package srv.db.en;

import javax.persistence.*;


@Entity
@Table(name = "firm")
public class Firm {
  @Id 
  @GeneratedValue
  public long id;

  @Column(name = "firm_name")
  public String firmName;
  
}
