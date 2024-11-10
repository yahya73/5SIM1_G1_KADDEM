package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;

@Service
public class UniversiteServiceImpl implements IUniversiteService {
    @Autowired
    UniversiteRepository universiteRepository;

    @Autowired
    DepartementRepository departementRepository;

    public UniversiteServiceImpl() {
        // Default constructor
    }

    public List<Universite> retrieveAllUniversites() {
        return (List<Universite>) universiteRepository.findAll();
    }

    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    public Universite updateUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    public Universite retrieveUniversite(Integer idUniversite) {
        return universiteRepository.findById(idUniversite).orElse(null); // Handle missing Universite properly
    }

    public void deleteUniversite(Integer idUniversite) {
        Universite u = retrieveUniversite(idUniversite);
        if (u != null) {
            universiteRepository.delete(u);
        }
    }

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        Departement d = departementRepository.findById(idDepartement).orElse(null);

        if (u != null && d != null) {
            u.getDepartements().add(d);
            universiteRepository.save(u);
        }
    }

    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u != null) {
            return u.getDepartements();
        }
        return null; // Return null or an empty set if the Universite is not found
    }
}
