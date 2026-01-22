package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retrait;
import java.util.List;

public interface RetraitRepository {

    public List<Retrait> readAll();

    Retrait readRetraitById(long id);
    void createRetrait(Retrait retrait);
    void updateRetrait(Retrait retrait);
    void deleteRetrait(long id);

}
