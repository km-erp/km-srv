package srv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srv.db.en.Firm;
import srv.db.en.FirmUsrs;
import srv.db.en.Usrs;
import srv.db.en.UsrsGrps;
import srv.db.r.FirmRepo;
import srv.db.r.FirmUsrsRepo;
import srv.db.r.UsrsGrpsRepo;
import srv.db.r.UsrsRepo;

@Service
public class FirmService extends Std {
  @Autowired
  private FirmRepo firmRepo;
  @Autowired
  private FirmUsrsRepo firmUsrsRepo;
  @Autowired
  private UsrsRepo usrsRepo;
  @Autowired
  private UsrsGrpsRepo usrsGrpsRepo;
  
  public Firm firmByUsr(String usrName){
    Firm result;
    result = null;
    Usrs u = usrsRepo.findFirstByUsrName(usrName);
    if (u == null){
      return result;
    }
    
    List<UsrsGrps> ug = usrsGrpsRepo.findByIdUsrs(u.id);
    for (UsrsGrps ug1: ug){
      if (result == null){
        List<FirmUsrs> fr = firmUsrsRepo.findByIdUsrs(ug1.idGrps.longValue());
        if (fr.size() > 0){
          result = firmRepo.findOne(fr.get(0).idFirm);
        }
      }
    }
    return result;
  }

}
