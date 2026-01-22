package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retraits;
import java.util.List;

public interface RetraitRepository {

    // Retraits
    public List<Retraits> readRetraits();

    Retraits getRetraitById(long id);
    void createRetrait(Retraits retrait);
    void updateRetrait(Retraits retrait);
    void deleteRetraitById(long id);


}
