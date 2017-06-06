package srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

class RgtParam extends StdParam{
  public String rgtName;
}

class RgtResult extends StdResult{
  public Boolean has;
}

@RestController
public class RgtController extends Std {
  
  @Autowired
  RgtService rgtService;
  
  @RequestMapping(method = RequestMethod.POST, value = "rgtChk")
  public RgtResult rgtChk(@RequestBody RgtParam p) throws Exception{
    RgtResult r = new RgtResult();
    String usrName = as.usrName(p, r);
    if (r.authorized){
      r.has = rgtService.hasRight(usrName, p.rgtName);
    }
    return r;
  }
}
