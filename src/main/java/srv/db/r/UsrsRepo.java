package srv.db.r;

import org.springframework.data.repository.CrudRepository;
import srv.db.en.Usrs;

public interface UsrsRepo extends CrudRepository<Usrs, Long> {
  Usrs findFirstByUsrName(String usrName);

}
