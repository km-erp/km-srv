package srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import srv.db.en.Firm;

class FirmParam extends StdParam{
  
}

class FirmResult extends StdResult{
  public Firm firm = null;  
}

@RestController
public class FirmController extends Std {

  @Autowired
  FirmService firmService;
  
  @RequestMapping(method = RequestMethod.POST, value = "firmByUsr")
  public FirmResult firmByUsr(FirmParam p){
    FirmResult r = new FirmResult();
    String usrName = as.usrName(p, r);
    if (r.authorized){
      r.firm = firmService.firmByUsr(usrName);      
    }
    return r;
  }
}
