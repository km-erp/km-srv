package db;

import java.util.ArrayList;

import javax.persistence.*;


public abstract class Sql {
	
	protected EntityManager em;
	
	protected void sqle(String sql){
	  em.createNativeQuery(sql).executeUpdate();
	}
  protected void sqle(String sql, Object ...v){
    Query q = em.createNativeQuery(sql);
    int iP = 1;
    
    for (int i = 0; i < v.length; i++){
      if (v[i] instanceof ArrayList){
        ArrayList<?> al = (ArrayList<?>) v[i];
        for (int j = 0; j < al.size(); j ++){
          q.setParameter(iP, al.get(j));  
          iP++;
        }        
      }
      else{
        q.setParameter(iP, v[i]);
        iP++;
      }
    }
    q.executeUpdate();
  }

	public abstract int versionDb();
	
	public enum TableColType{
		Integer, String, Money,
    IntegerNN, StringNN, MoneyNN,
		pk, fk, fkNN
	}
	protected abstract String tctS(TableColType tct);
	public class TableCol{
		private String cn;
		private TableColType tct;
		
		public TableCol(String cn, TableColType tct){
			this.cn = colName(cn);
			this.tct = tct;
		}
	}
	public TableCol tc(String cn, TableColType tct){
	  return new TableCol(cn, tct);
	}
	
	protected String tableName(String tn){
	  return tn.toLowerCase();
	}
	protected String constraintName(String sn){
	  return sn.toLowerCase();
	}

// tables
	
	public abstract boolean tableExists(String tn);
	public void tableCreate(String tn, TableCol[] cols){
		String sql;
		
		if (!tableExists(tn)){
  		sql = "create table " + tableName(tn) + "(" + colName("id") + " " + tctS(TableColType.pk);
  		for (TableCol col: cols){
  			sql = sql + "," + col.cn + " " + tctS(col.tct);
  		}
  		sql = sql + ")";
  		sqle(sql);
		}
		
		pkCreate(tn);
	};
	

// columns 
	
  protected String colName(String cn){
    return cn.toLowerCase();
  }
	public void colCreate(String tn, TableCol tc){
	  String sql = String.format(
     "alter table %s add %s %s", 
     tableName(tn),
     tc.cn,
     tctS(tc.tct));	  
	  sqle(sql);
	}
  public void colModify(String tn, TableCol tc){
    String sql = String.format(
     "alter table %s modify %s %s", 
     tableName(tn),
     tc.cn,
     tctS(tc.tct));   
    sqle(sql);
  }	
// foreign keys
	
	public void fkCreate(String tn, String ptn, String fk, String col, String pcol){
	  if (fk == null){
	    fk = "fk_" + tn + "_" + ptn;
	  }
	  
	  if (col == null){
	    col = "id_" + ptn;
	  }
	  
	  if (pcol == null){
	    pcol = "id";
	  }
	  
	  String sql = String.format(
	    "alter table %s add constraint %s foreign key ( %s ) references %s ( %s )", 
	    tableName(tn),
	    constraintName(fk),
	    colName(col),
	    tableName(ptn),
	    colName(pcol));
	  sqle(sql);
	}
  public void fkCreate(String tn, String ptn, String fk, String col){
    fkCreate(tn, ptn, fk, col, null);
  }
  public void fkCreate(String tn, String ptn, String fk){
    fkCreate(tn, ptn, fk, null, null);
  }
  public void fkCreate(String tn, String ptn){
    fkCreate(tn, ptn, null, null, null);
  }
  
 // primary key 
 
	public void pkCreate(String tn){
	  String sql = String.format(
	    "alter table %s add constraint %s primary key ( %s )",
	    tableName(tn),
	    constraintName("pk_" + tn),
	    colName("id"));
	  sqle(sql);
	}
	
	Sql(EntityManager em){
		this.em = em;
	}
	
// insert record
	public void recIns(String tn, Object ...v){
	  String cols = "";
	  String pars = "";
	  ArrayList<Object> vals = new ArrayList<Object>();
	  for (int i = 0; i < v.length; i++){
	    if (i % 2 == 0){ //even
	      cols = cols + "," + colName(v[i].toString());
	      pars = pars + ",?";	      
	    }	 
	    else{
	      vals.add(v[i]);
	    }
	  }
	  cols = cols.substring(1);
	  pars = pars.substring(1);
    String sql = String.format("insert into %s (%s) values (%s)", tableName(tn), cols, pars);
    sqle(sql, vals);
	};
	
// update record
	public void recUpd(String tn, Object[] v, Object[] k){
	  String sql;
	  String cols = "";
    ArrayList<Object> vals = new ArrayList<Object>();
	  for (int i = 0; i < v.length; i++){
      if (i % 2 == 0){ //even
        cols = cols + colName(v[i].toString()) + " = ?";        
      }
      else{
        vals.add(v[i]);        
      }
	  }
	  
	  String keys = "";
	  for (int i = 0; i < v.length; i++){
      if (i % 2 == 0){ //even
        keys = keys + (keys.length() == 0 ? "where ": " and") + colName(k[i].toString()) + " = ? ";        
      }
      else{
        vals.add(k[i]);        
      }	    
	  }
	  
    sql = String.format("update %s set %s %s", tableName(tn), cols, keys);
    sqle(sql, vals);
	}
  public void recUpd(String tn, Object[] v){
    recUpd(tn, v, new Object[]{});
  }
    
}
