package srv;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


class AuthResult{
  public String access_token;
  public Integer expires_in;
  public String token_type;
  public String refresh_token;
  public String id_token;
  public String user_id;
  public String project_id;
}

@Service
public class AuthService {
  
  private ResponseEntity<AuthResult> token(StdParam p, StdResult r){
    r.authorized = false;
    if (p.authToken == null){
      return null;
    }
    
    RestTemplate rt = new RestTemplate();
    MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
    form.add("grant_type", "refresh_token");
    form.add("refresh_token", p.authToken);
    ResponseEntity<AuthResult> re = rt.postForEntity(Consts.sKMErpFB, form, AuthResult.class);
    
    if (re.getStatusCode().compareTo(HttpStatus.OK) != 0){
      return null;
    }
    
    r.authorized = true;
    
    return re;
  }
  
  public String usrName(StdParam p, StdResult r){
    ResponseEntity<AuthResult> re = token(p, r);
    
    if (re != null){
      return re.getBody().user_id;
    }
    
    return null;
  }
  
	public void validate(StdParam p, StdResult r){
	  token(p, r);
	}

}
