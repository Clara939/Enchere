package fr.eni.enchere.service;

import fr.eni.enchere.bo.Retrait;

public interface RetraitService {
    Retrait readRetraitById(long id);

    void createRetrait(Retrait retrait);

    void updateRetrait(Retrait retrait);

    void deleteRetrait(long id);

}
