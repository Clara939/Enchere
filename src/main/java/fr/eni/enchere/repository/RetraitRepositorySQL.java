package fr.eni.enchere.repository;

import fr.eni.enchere.bo.Retrait;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class RetraitRepositorySQL implements RetraitRepository {

    @Override
    public List<Retrait> readAll() {
        return List.of();
    }

    @Override
    public Retrait readRetraitById(long id) {
        return null;
    }

    @Override
    public void createRetrait(Retrait retrait) {

    }

    @Override
    public void updateRetrait(Retrait retrait) {

    }

    @Override
    public void deleteRetrait(long id) {

    }
}
