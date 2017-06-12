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
    
    Rgts r = rgtsRepo.findFirstByRgtName(rgtName);
    if (r == null){
      return false;
    }
    Usrs u = usrsRepo.findFirstByUsrName(usrName);
    if (u == null){
      return false;
    }
    
    List<UsrsGrps>ug = usrsGrpsRepo.findByIdUsrs(u.id);

    return ug.stream()
      .filter(ug1 -> usrsRgtsRepo.findByIdUsrsAndIdRgts(ug1.idGrps, r.id).size() > 0)
      .count() > 0;
  }

}
