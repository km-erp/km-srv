package srv.db.r;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import srv.db.en.Rgts;

public interface RgtsRepo extends CrudRepository<Rgts, Long>{
  List<Rgts> findByRgtName(String rgtName);
}
