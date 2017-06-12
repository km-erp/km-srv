package srv.db.en;

import javax.persistence.*;

@Entity
@Table(name = "usrs_grps")
public class UsrsGrps {
  @Id
  @GeneratedValue
  public long id;
  @Column(name = "id_usrs")
  public Long idUsrs;
  @Column(name = "id_grps")
  public Long idGrps;

}
