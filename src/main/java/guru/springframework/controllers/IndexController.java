package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jt on 11/6/15.
 */
@Controller
public class IndexController {

    @RequestMapping({"/", ""})
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/access_denied", method = RequestMethod.GET)
    public String notAuth() {
        return "access_denied";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginForm() {
        return "login";
    }
}
