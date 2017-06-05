package srv.db.r;

import org.springframework.data.repository.CrudRepository;
import srv.db.en.Firm;

public interface FirmRepo extends CrudRepository<Firm, Long> {}
