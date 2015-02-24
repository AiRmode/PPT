package com.ppt.mvc;

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
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!" + new Date());
        new SessionID("alexey", "shevch");
        return "hello";
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD})
    public String login(@RequestParam String login, @RequestParam String password, ModelMap model) {
        model.addAttribute("message", "Hello world!" + new Date());
        SessionID sessionID = new SessionID("alexey", "shevch");
        return "hello";
    }
}