package srv.db;

import java.util.List;

import javax.persistence.EntityManager;

public class SqlPg extends Sql {
	
	public SqlPg(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String tctS(TableColType tct, boolean nll) {
		switch (tct){
			case Integer: return "integer" + (nll? " null": "");
			case String: return "character varying(250)" + (nll? " null": "");
			case Money: return "decimal(30, 4)" + (nll? " null": "");
      case Bool: return "integer" + (nll? " null": "");
			
      case IntegerNN: return "integer" + (nll? " not null": "");
      case StringNN: return "character varying(250)" + (nll? " not null": "");
      case MoneyNN: return "decimal(30, 4)" + (nll? " not null": "");
      case BoolNN: return "integer" + (nll? " not null": "");

      case pk: return "bigint" + (nll? " not null": "");
      case fk: return "bigint" + (nll? " null": "");
      case fkNN: return "bigint" + (nll? " not null": "");
			
			default: return null;		
		}		
	}

	@Override
	public boolean tableExists(String tn){
    @SuppressWarnings("rawtypes")
    List l = em.createNativeQuery("select oid from pg_class where relname = :rn and relkind = :r")
      .setParameter("rn", tn)
      .setParameter("r",  "r")
      .getResultList();
    
    if (l.size() == 0){
      return false;
    }
    return true;	  
	}

}
