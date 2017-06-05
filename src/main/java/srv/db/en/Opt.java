package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "opt")
public class Opt {
	@Id 
	@GeneratedValue
	public long id;
	public long id_firm;
	public long id_usrs;
	
	public String k;
	public String vs;
	public String vi;
}
