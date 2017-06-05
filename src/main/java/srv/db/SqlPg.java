package srv.db;

import java.util.List;

import javax.persistence.EntityManager;

public class SqlPg extends Sql {
	
	public SqlPg(EntityManager em) {
		super(em);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String tctS(TableColType tct) {
		switch (tct){
			case Integer: return "integer null";
			case String: return "character varying(250) null";
			case Money: return "decimal(30, 4) null";
      case Bool: return "integer null";
			
      case IntegerNN: return "integer not null";
      case StringNN: return "character varying(250) not null";
      case MoneyNN: return "decimal(30, 4) not null";
      case BoolNN: return "integer not null";

      case pk: return "bigint not null";
      case fk: return "bigint null";
      case fkNN: return "bigint not null";
			
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
