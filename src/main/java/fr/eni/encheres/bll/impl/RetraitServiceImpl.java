package fr.eni.encheres.bll.impl;

import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.RetraitDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements RetraitService {
    private final RetraitDAO retraitDAO;

    @Autowired
    public RetraitServiceImpl(RetraitDAO retraitDAO) {
        this.retraitDAO = retraitDAO;
    }

    @Override
    public void createRetrait(Retrait retrait) {
        retraitDAO.createRetrait(retrait);
    }

    @Override
    public void deleteRetrait(long noArticle) {
        retraitDAO.deleteRetrait(noArticle);
    }

    @Override
    public Retrait findRetrait(long noArticle) {
        return retraitDAO.readByArticle(noArticle);
    }
}
