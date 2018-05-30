package guru.springframework.services.security;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import guru.springframework.services.jpaservices.UserServiceJpaDaoImpl;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoginAspect {
    private LoginFailureEventPublisher publisher;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPublisher(LoginFailureEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate() {

    }

    @Before("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logBefore(Authentication authentication){
        System.out.println("This is before the authenticate method: authentication: " + authentication.isAuthenticated());
    }

    @AfterReturning(value = "guru.springframework.services.security.LoginAspect.doAuthenticate()", returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication){
        System.out.println("This is after the authenticate method authentication: " + authentication.isAuthenticated());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUserName(userDetails.getUsername());
        if(user != null){
            user.setFailedAttempts(0);
            user.setEnabled(true);
        }
        userService.saveOrUpdate(user);
    }

    @AfterThrowing("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenticationException(Authentication authentication){
        String userDetails = (String)authentication.getPrincipal();
        System.out.println("This is after throwing the authentication exception" + authentication.getPrincipal());
        User user = userService.findByUserName(userDetails);

        if(user!= null){
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            if(user.getFailedAttempts() > 5){
                user.setEnabled(false);
                System.out.println("###LOCKED USER ACCOUNT###");
            }
            userService.saveOrUpdate(user);
        }

        System.out.println("Login failed for: " + userDetails);
        publisher.publish(new LoginFailureEvent(authentication));
    }

}
