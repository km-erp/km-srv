package srv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

class UpgParam extends StdParam{
  public Integer upgCall;
}

class UpgResult extends StdResult{
  public Boolean requires;
  public Boolean upgraded;
  public Integer versionDb;
  public Integer versionRq;
}

@RestController
public class UpgController extends Std{

	@Autowired
	UpgService upgService;

  @RequestMapping(method = RequestMethod.POST, value = "/upgParam")
  public UpgParam upgParam() throws Exception{
    UpgParam p = new UpgParam();
    p.upgCall = 1;
    return p;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/upg")
	public UpgResult upg(@RequestBody UpgParam p) throws Exception{
    UpgResult r = new UpgResult();
    as.validate(p, r);
    
    if (r.authorized){
      r.requires = p.upgCall != null && upgService.versionDb() == p.upgCall - 1;
      if (r.requires){
        r.upgraded = upgService.upgDo(p.upgCall);    
      }
      r.versionDb = upgService.versionDb();
      r.versionRq = upgService.versionRq();
    }
		return r;
	}
  
  @RequestMapping(method = RequestMethod.POST, value = "/upgOk")
  public UpgResult upgOk(@RequestBody UpgParam p) throws Exception{
    UpgResult r = new UpgResult();
    as.validate(p, r);
    
    if (r.authorized){
      r.requires = p.upgCall != null && upgService.versionDb() == p.upgCall - 1;
      r.versionDb = upgService.versionDb();
      r.versionRq = upgService.versionRq();
      r.upgraded = r.versionDb == r.versionRq;     
    }
    return r;
  }
  
}
