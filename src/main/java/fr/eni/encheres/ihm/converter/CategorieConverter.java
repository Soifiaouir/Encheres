package fr.eni.encheres.ihm.converter;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategorieConverter implements Converter<String, Categorie> {

    private final CategorieService categorieService;

    public CategorieConverter(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @Override
    public Categorie convert(String noCategorie) {
        long id = Long.parseLong(noCategorie);

        return categorieService.findById(id);
    }
}
