package guru.springframework.services.security;

import guru.springframework.domain.User;
import guru.springframework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void resetFailedLogins() {
        List<User> users = (List<User>) userService.listAll();
        System.out.println("Checking for locked accounts");
        users.stream().filter(user -> user.getEnabled() == false && user.getFailedAttempts() > 0).forEach(user -> {
            user.setEnabled(true);
            user.setFailedAttempts(0);
            userService.saveOrUpdate(user);
        });
    }
}
