package my.nxvembrx.cherrybox.cherrykeep.service;

import my.nxvembrx.cherrybox.cherrykeep.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {


    public User getCurrentlyAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public void autoLogin(HttpServletRequest request, String username, String password) throws ServletException {
        request.login(username, password);
    }
}
