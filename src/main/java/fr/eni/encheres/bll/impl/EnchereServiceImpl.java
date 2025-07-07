package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService {


    private final EnchereDAO enchereDAO;

    @Autowired
    public EnchereServiceImpl(EnchereDAO enchereDAO) {
        this.enchereDAO = enchereDAO;
    }


    @Override
    public Enchere findById(long noEnchere) {
        return enchereDAO.readByNoEnchere(noEnchere);
    }

    @Override
    public List<Enchere> findByArticle(long noArticle) {
        return enchereDAO.readByArticle(noArticle);
    }

    @Override
    public List<Enchere> findByUser(long noUtilisateur) {
        return enchereDAO.readByUser(noUtilisateur);
    }

    @Override
    public List<Enchere> findListEnchere() {
        return enchereDAO.readListEnchere();
    }
    @Override
    public void createEnchere(Enchere enchere) {
            enchereDAO.createEnchere(enchere);
    }

    @Override
    public void updateEnchere(Enchere enchere) {
            enchereDAO.updateEnchere(enchere);
    }

    @Override
    public void deleteEnchere(long noEnchere) {
            enchereDAO.deleteEnchere(noEnchere);
    }
}
