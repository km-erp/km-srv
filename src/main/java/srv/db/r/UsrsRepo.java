package srv.db.r;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import srv.db.en.Usrs;

public interface UsrsRepo extends CrudRepository<Usrs, Long> {
  List<Usrs> findByUsrName(String usrName);

}
