package srv.db.en;

import javax.persistence.*;

@Entity()
@Table(name = "usrs")
public class Usrs {
  @Id 
  @GeneratedValue
  public long id;
  @Column(name = "usr_name")
  public String usrName;
  public boolean is_grp;
  

}
