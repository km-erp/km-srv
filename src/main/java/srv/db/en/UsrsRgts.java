package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "usrs_rgts")
public class UsrsRgts {
  @Id
  @GeneratedValue
  public long id;
  @Column(name = "id_usrs")
  public long idUsrs;
  @Column(name = "id_rgts")
  public long idRgts;
}
