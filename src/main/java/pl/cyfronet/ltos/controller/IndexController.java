package pl.cyfronet.ltos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/bazaar")
    public String bazaar() {
        return "bazaar";
    }

    @RequestMapping("/indigo")
    public String indygo() {
        return "indigo";
    }
}
