package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "opt")
public class Opt {
	@Id 
	@GeneratedValue
	public long id;
	public Long id_firm;
	public Long id_usrs;
	
	public String k;
	public String vs;
	public String vi;
}
