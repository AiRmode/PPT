package com.ppt.mvc;

import com.ppt.Configurator;
import com.ppt.session.SessionHelper;
import com.ppt.session.Validator;
import com.ppt.user.User;
import com.ppt.user.UserHelper;
import com.ppt.user.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


/**
 * ppt = public private transport
 */
@Controller
@RequestMapping("/")
public class HelloController {

    static {
        Configurator configurator = new Configurator();
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world! " + new Date());
        return "hello";
    }

    @RequestMapping(value = "/createUser", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String createUser(@RequestParam("login") String login, @RequestParam("password") String password, ModelMap model) {
        User user = new User(login, password);
        boolean isUserExists = UserValidator.isUserExists(user);
        if (isUserExists) {
            model.addAttribute("message", "User is already existed!</br>" + "</br>" + new Date());
        } else {
            UserHelper.createUser(login, password);
            model.addAttribute("message", "User was successfully created!</br>" + "</br>" + new Date());
        }

        return "hello";
    }

    @RequestMapping(value = "/getSession", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String getSessionID(@RequestParam("login") String login, @RequestParam("password") String password, ModelMap model) {
        User user = new User(login, password);
        boolean isUserExists = UserValidator.isUserExists(user);
        if (isUserExists) {
            SessionHelper.createAndStoreSessionToDB(login, password);
            model.addAttribute("message", "New session was created! " + new Date());
        } else {
            model.addAttribute("message", "User was not found! " + new Date());
        }
        return "hello";
    }

    @RequestMapping(value = "/doAction1", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String doAction1(@RequestParam("sessionID") String sessionID, ModelMap model) {
        boolean isSessionValid = Validator.isSessionIDValid(sessionID);
        if (isSessionValid)
            model.addAttribute("message", "Action1!</br>" + isSessionValid + "</br>" + new Date());
        else
            model.addAttribute("message", "(doAction1): Session INVALID!</br>" + isSessionValid + "</br>" + new Date());

        return "hello";
    }

    @RequestMapping(value = "/closeSession", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String closeSession(@RequestParam("sessionID") String sessionID, ModelMap model) {
        boolean isSessionValid = Validator.isSessionIDValid(sessionID);
        if (isSessionValid) {
            SessionHelper.removeSessionByRequest(sessionID);
        }
        model.addAttribute("message", "Hello world! Session was successfully invalidated!</br>" + isSessionValid + "</br>" + new Date());

        return "hello";
    }
}