package srv.db.r;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import srv.db.en.UsrsGrps;

public interface UsrsGrpsRepo extends CrudRepository<UsrsGrps, Long> {
  List<UsrsGrps> findByIdUsrs(long idUsrs);
}
