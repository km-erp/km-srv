package kmsrv;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
	public boolean validate(String auth){
	  if (auth == null){
	    return false;
	  }
		return true;
	}

}
