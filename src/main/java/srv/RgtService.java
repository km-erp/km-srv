package srv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srv.db.en.*;
import srv.db.r.*;

@Service
public class RgtService extends Std {

  @Autowired
  UpgService upgService;
  @Autowired
  private RgtsRepo rgtsRepo;
  @Autowired
  private UsrsRepo usrsRepo;
  @Autowired
  private UsrsGrpsRepo usrsGrpsRepo;
  @Autowired
  private UsrsRgtsRepo usrsRgtsRepo;
  
  public Boolean hasRight(String usrName, String rgtName) throws Exception {
    if (upgService.versionDb() < 6){
      return true;
    }
    
    List<Rgts> r = rgtsRepo.findByRgtName(rgtName);
    if (r.size() != 1){
      return false;
    }
    List<Usrs> u = usrsRepo.findByUsrName(usrName);
    if (r.size() != 1){
      return false;
    }
    
    List<UsrsGrps>ug = usrsGrpsRepo.findByIdUsrs(u.get(0).id);

    return ug.stream()
      .filter(ug1 -> usrsRgtsRepo.findByIdUsrsAndIdRgts(ug1.idGrps, r.get(0).id).size() > 0)
      .count() > 0;
  }

}
