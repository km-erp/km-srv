package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "rgts")
public class Rgts {
  @Id 
  public long id;
  public long id_rgts;
  @Column(name = "rgt_name")
  public String rgtName;
}
