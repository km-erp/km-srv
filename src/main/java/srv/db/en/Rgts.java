package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "rgts")
public class Rgts {
  @Id 
  public Long id;
  @Column(name = "id_rgt")
  public Long idRgt;
  @Column(name = "rgt_name")
  public String rgtName;
}
