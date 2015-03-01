package com.ppt.mvc;

import com.ppt.Configurator;
import com.ppt.session.SessionHelper;
import com.ppt.session.id.SessionID;
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
        new SessionID("alexey", "shevch");
        return "hello";
    }

    @RequestMapping(value = "/auth", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String login(@RequestParam("login") String login, @RequestParam("password") String password, ModelMap model) {
        model.addAttribute("message", "Hello world!" + new Date());
        SessionHelper.createAndStoreSessionToDB(login, password);
        return "hello";
    }
}