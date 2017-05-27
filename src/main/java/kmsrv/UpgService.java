package kmsrv;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import db.*;
import db.Sql.*;


@Service
public class UpgService extends Std {
	
	@Autowired
  private Environment env;
	@PersistenceContext
  private EntityManager em;

	public int versionDb() throws Exception{
  	int iResult = sql.versionDb();
  	
  	if (iResult != -1){
  		
  	}
  	
		return iResult;
	}
	public int versionRq(){
		return 0;
	}
	
	private Sql sql = null;
	
	@Transactional
  public boolean upgDo(int iUpg) throws Exception{
    if (upg.length - 1 < iUpg || iUpg < 0){
      return false;
    }
    
    if (sql == null && env.getProperty(Consts.AppPropDBDialect).compareToIgnoreCase(Consts.AppPropDbDialectPG) == 0){
      sql = new SqlPg(em);
    }
    if (sql == null){
      throw new Exception("Unsupported database");
    }
    
    upg[iUpg].upg();
    
    sql.recUpd("opt", new Object[]{"vi", iUpg}, new Object[]{"k", Consts.optDbVer});

    return true;
  }

	interface Upg{void upg() throws Exception;}
	
	private Upg[] upg = new Upg[]{

	  new Upg(){public void upg() throws Exception{
        sql.tableCreate(
          "opt", 
          sql.tc("k", TableColType.StringNN),
          sql.tc("vs", TableColType.String),
          sql.tc("vi", TableColType.Integer));
        sql.recIns("opt", "id", 1, "k", Consts.optDbVer, "vi", -1);
      }
		},     

    new Upg(){public void upg() throws Exception{
        sql.seqCreate("pk");
      }
    },     

    new Upg(){public void upg() throws Exception{
        sql.tableCreate("firm", sql.tc("firm_name", TableColType.String));
        sql.recIns("firm", "id", 0, "firm_name", "global");
        
        sql.colCreate("opt", sql.tc("id_firm", TableColType.fk));
        sql.recUpd("opt", new Object[]{"id_firm", 0});
        sql.colModify("opt", sql.tc("id_firm", TableColType.fkNN));
        sql.fkCreate("opt", "firm");
      }
    },     

    new Upg(){public void upg() throws Exception{
        sql.tableCreate(
          "usrs", 
          sql.tc("usr_name", TableColType.StringNN),
          sql.tc("is_grp", TableColType.BoolNN));
        
        sql.recIns("usrs", "id", 0, "usr_name", "global", "is_grp", true);
        
        sql.colCreate("opt", sql.tc("id_usrs", TableColType.fk));
        sql.recUpd("opt", new Object[]{"id_usrs", 0});
        sql.colModify("opt", sql.tc("id_usrs", TableColType.fkNN));
        sql.fkCreate("opt", "usrs");
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql.tableCreate(
          "rgts", 
          sql.tc("rgt_name", TableColType.StringNN), 
          sql.tc("id_rgt", TableColType.fk));
        sql.recIns("rgts", "id", 1, "rgt_name", "adm");
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql.tableCreate(
          "usrs_grps", 
          sql.tc("id_usrs", TableColType.fkNN),
          sql.tc("id_grps", TableColType.fkNN));
        sql.fkCreate("usrs_grps", "usrs");
        sql.fkCreate("usrs_grps", "usrs", null, "id_grps");
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql.tableCreate(
          "usrs_rgts", 
          sql.tc("id_usrs", TableColType.fkNN),
          sql.tc("id_rgts", TableColType.fkNN));
        sql.fkCreate("usrs_rgts", "usrs");
        sql.fkCreate("usrs_rgts", "rgts");

        Long id = sql.pk();
        sql.recIns("usrs", "id", id, "usr_name", env.getProperty(Consts.AppPropMasterUser).toString(), "is_grp", false);
        sql.recIns("usrs_grps", "id", sql.pk(), "id_usrs", id, "id_usrs");
        sql.recIns("usrs_rgts", "id", sql.pk(), "id_usrs", id, "id_rgts", 1);
      }
    },     
    
    new Upg(){public void upg() throws Exception{
      }
    },     
    
    new Upg(){public void upg() throws Exception{
      }
    },     
    
    new Upg(){public void upg() throws Exception{
      }
    }	
    
    
	};
	
 }
