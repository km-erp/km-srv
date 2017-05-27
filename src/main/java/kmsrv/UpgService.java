package kmsrv;

import java.util.List;

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
  	int iResult = sql().versionDb();
  	
  	if (iResult != -1){
  		
  	}
  	
		return iResult;
	}
	public int versionRq(){
		return 0;
	}
	
	private Sql _sql = null;
	private Sql sql() throws Exception{
		if (_sql != null){
			return _sql;
		}		
		
		if (env.getProperty(Consts.AppPropDBDialect).compareToIgnoreCase(Consts.AppPropDbDialectPG) == 0){
			_sql = new SqlPg(em);
			return _sql;
		}		
		
		throw new Exception("Unsupported database");
	}
	
	@Transactional
  public boolean upgDo(int iUpg) throws Exception{
    if (upg.length - 1 < iUpg || iUpg < 0){
      return false;
    }
    
    upg[iUpg].upg();
    
    sql().recUpd("opt", new Object[]{"vi", iUpg}, new Object[]{"k", Consts.optDbVer});

    return true;
  }

	interface Upg{void upg() throws Exception;}
	
	private Upg[] upg = new Upg[]{

	  new Upg(){public void upg() throws Exception{
        sql().tableCreate(
          "opt", 
          new TableCol[] {
            sql().tc("k", TableColType.StringNN),
            sql().tc("vs", TableColType.String),
            sql().tc("vi", TableColType.Integer)});
        sql().recIns(
            "opt", 
            "id", 1, 
            "k", Consts.optDbVer, 
            "vi", -1);
      }
		},     

    new Upg(){public void upg() throws Exception{
        sql().tableCreate(
          "subject", 
          new TableCol[] {
            sql().tc("subject_name", TableColType.String)});
      }
    },     

    new Upg(){public void upg() throws Exception{
        sql().recIns(
            "subject",
            "id", 0,
            "subject_name", "global");
      }
    },     

    new Upg(){public void upg() throws Exception{
		    sql().colCreate("opt", sql().tc("id_subject", TableColType.fk));
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql().recUpd("opt", new Object[]{"id_subject", 0});
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql().colModify("opt", sql().tc("id_subject", TableColType.fk));
      }
    },     
    
    new Upg(){public void upg() throws Exception{
        sql().fkCreate("opt", "subject");}
    }			
	};
	
 }
