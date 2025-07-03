package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {

    Retrait readByArticle(long noArticle);
    void createRetrait(Retrait retrait);
    void updateRetrait(Retrait retrait);
    void deleteRetrait(long noArticle);

}
