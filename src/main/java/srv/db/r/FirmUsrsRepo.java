package srv.db.r;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import srv.db.en.FirmUsrs;

public interface FirmUsrsRepo extends CrudRepository<FirmUsrs, Long> {
  List<FirmUsrs> findByIdUsrs(long idUsrs);

}
