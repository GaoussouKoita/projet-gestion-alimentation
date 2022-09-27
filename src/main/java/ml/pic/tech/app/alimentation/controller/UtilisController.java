package ml.pic.tech.app.alimentation.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/403")
    public String home1() {
        return "403";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/test0")
    public String test() {
        return "test";
    }
}
