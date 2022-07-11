package cse.nigile.softdevi.security;
 
import java.io.IOException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import cse.nigile.softdevi.entities.AppUser;
import cse.nigile.softdevi.entities.Role;
import cse.nigile.softdevi.service.UserService;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	@Autowired
	private UserService userServ;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) 
    		throws ServletException, IOException {
 
    	Authentication currentlyLoggedInUserInfo = SecurityContextHolder.getContext().getAuthentication();
        String redirectURL = request.getContextPath();
        
        AppUser user = userServ.getUser(currentlyLoggedInUserInfo.getName()); 
        for(Role role : user.getRoles()) {
        	if (role.getRoleName().equals("ADMIN")) {
            	redirectURL = "/admin";
            	break;
        	} else {
        		redirectURL = "/user";
        	}
        }
        response.sendRedirect(redirectURL);
         
    }
 
}
