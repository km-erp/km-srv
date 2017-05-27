package db;

import javax.persistence.*;

@Entity
@Table(name = "opt")
public class Opt {
	@Id 
	@GeneratedValue
	public int id;
	public String k;
	public String vs;
	public String vi;
}
