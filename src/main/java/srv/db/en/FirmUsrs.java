package srv.db.en;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "firm_usrs")
public class FirmUsrs {
  @Id 
  @GeneratedValue
  public long id;
  @Column(name = "id_firm")
  public long idFirm;
  @Column(name = "id_usrs")
  public long idUsrs;
}
