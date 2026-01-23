package fr.eni.enchere.service;

import fr.eni.enchere.bo.Retrait;
import fr.eni.enchere.repository.RetraitRepository;
import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements RetraitService{
    RetraitRepository retraitRepository;

    public RetraitServiceImpl(RetraitRepository retraitRepository) {
        this.retraitRepository = retraitRepository;
    }

    @Override
    public Retrait readRetraitById(long id) { return this.retraitRepository.readRetraitById(id); }

    @Override
    public void createRetrait(Retrait retrait) { this.retraitRepository.createRetrait(retrait); }

    @Override
    public void updateRetrait(Retrait retrait) { this.retraitRepository.updateRetrait(retrait); }

    @Override
    public void deleteRetrait(long id) { this.retraitRepository.deleteRetrait(id); }
}
