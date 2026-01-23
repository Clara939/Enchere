package fr.eni.enchere.service;

import fr.eni.enchere.bo.Enchere;
import fr.eni.enchere.repository.EnchereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnchereServiceImpl implements EnchereService{
    EnchereRepository enchereRepository;

    public EnchereServiceImpl(EnchereRepository enchereRepository) {
        this.enchereRepository = enchereRepository;
    }

    @Override
    public void createEnchere(Enchere enchere) { this.enchereRepository.createEnchere(enchere); }

    @Override
    public List<Enchere> readAll() { return this.enchereRepository.readAll(); }

    @Override
    public Enchere readById(long id) { return this.enchereRepository.readById(id); }

    @Override
    public void updateEnchere(Enchere enchere) { this.enchereRepository.updateEnchere(enchere); }

    @Override
    public void deleteEnchere(long id) { this.enchereRepository.deleteEnchere(id); }
}
