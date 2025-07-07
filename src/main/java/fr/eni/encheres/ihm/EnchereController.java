package fr.eni.encheres.ihm;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Enchere;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class EnchereController {
    EnchereService eService;

    public EnchereController(EnchereService eService) {
        this.eService = eService;
    }

    @GetMapping("/list_enchere")
    public String listEncheres(Model model) {
        List<Enchere> encheres = eService.findListEnchere();
        model.addAttribute("list_enchere", encheres);
        return "list_enchere";
    }

    @GetMapping("/listEnchereU")
    public String displayListEnchereUser(@RequestParam(name = "idUser" ) Long noUtilisateur, Model model) {
        List<Enchere> encheresU = eService.findByUser(noUtilisateur);
        model.addAttribute("encheresU", encheresU);

        return "detail_enchere";
    }


}
