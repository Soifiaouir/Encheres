package fr.eni.encheres.ihm.converter;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurConverter implements Converter<String, Utilisateur> {

    private UtilisateurService service;

    public UtilisateurConverter(UtilisateurService service) {
        this.service = service;
    }

    @Override
    public Utilisateur convert(String noUtilisateur) {
        long id = Long.parseLong(noUtilisateur);

        Utilisateur utilisateur = service.findUtilisateurById(id);

        return utilisateur;
    }
}
