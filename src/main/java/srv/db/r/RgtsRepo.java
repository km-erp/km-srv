package srv.db.r;

import org.springframework.data.repository.CrudRepository;

import srv.db.en.Rgts;

public interface RgtsRepo extends CrudRepository<Rgts, Long>{
  Rgts findFirstByRgtName(String rgtName);
}
