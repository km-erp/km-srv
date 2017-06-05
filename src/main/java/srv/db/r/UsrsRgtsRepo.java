package srv.db.r;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import srv.db.en.UsrsRgts;

public interface UsrsRgtsRepo extends CrudRepository<UsrsRgts, Long> {
  List<UsrsRgts> findByIdUsrsAndIdRgts(long idUser, long idRgts);

}
