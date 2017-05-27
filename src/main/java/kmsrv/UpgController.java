package kmsrv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

class Param{
  public Param() {
    super();
  }
  public Param(String authCode, Integer upgCall) {
    super();
    this.authCode = authCode;
    this.upgCall = upgCall;
  }
  public String authCode;
  public Integer upgCall;
  public String getAuthCode() {
    return authCode;
  }
  public Integer getUpgCall() {
    return upgCall;
  }
  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }
  public void setUpgCall(Integer upgCall) {
    this.upgCall = upgCall;
  }   
}

@RestController
public class UpgController extends Std{
	
  private class Result{
		public Boolean Auth;
		public Boolean Requires;
		public Boolean Upgraded;
		public Integer VersionDb;
		public Integer VersionRq;
	}
	
	@Autowired
	UpgService upgService;

  @RequestMapping(method = RequestMethod.POST, value = "/upgParam")
  public Param upgParam() throws Exception{
    Param p = new Param();
    p.upgCall = 1;
    return p;
  }

  @RequestMapping(method = RequestMethod.POST, value = "/upg")
	public Result upg(@RequestBody Param param) throws Exception{
    Result r = new Result();
    r.Auth = as.validate(param.authCode);
    
    if (r.Auth){
      r.Requires = param.upgCall != null && upgService.versionDb() == param.upgCall - 1;
      
      if (r.Requires){
        r.Upgraded = upgService.upgDo(param.upgCall);    
        r.VersionDb = upgService.versionDb();
        r.VersionRq = upgService.versionRq();
      }
    }
		return r;
	}
}
