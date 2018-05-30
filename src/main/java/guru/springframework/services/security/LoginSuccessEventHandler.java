package guru.springframework.services.security;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;

public class LoginSuccessEventHandler implements ApplicationListener<LoginSuccessEvent> {
    @Override
    public void onApplicationEvent(LoginSuccessEvent event){
        Authentication authentication = (Authentication)event.getSource();
        System.out.println("Login event success for: " + authentication.getPrincipal());
    }
}
