package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retrait;

import java.util.List;

public interface RetraitService {
    void createRetrait(Retrait retrait);
    void deleteRetrait(long noArticle);
    Retrait findRetrait(long noArticle);

}
