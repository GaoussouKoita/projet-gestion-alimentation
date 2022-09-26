package ml.pic.tech.app.alimentation.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtilisController {

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @GetMapping("/403")
    public String home1() {
        return "403";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
