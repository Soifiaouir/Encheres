package fr.eni.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisateurController {

    @GetMapping("/credit")
    public  String credit() {
        return "credit";
    }
}
