package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UniversiteServiceTest {

    @Autowired
    private UniversiteServiceImpl universiteService;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Test
    @DisplayName("Should retrieve all universities")
    void testRetrieveAllUniversites() {
        Universite univ1 = new Universite("Universite 1");
        Universite univ2 = new Universite("Universite 2");
        universiteRepository.save(univ1);
        universiteRepository.save(univ2);

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertNotNull(universites);
        assertEquals(2, universites.size());
    }

    @Test
    @DisplayName("Should add a new university")
    void testAddUniversite() {
        Universite univ = new Universite("Universite Test");

        Universite savedUniv = universiteService.addUniversite(univ);

        assertNotNull(savedUniv);
        assertEquals("Universite Test", savedUniv.getNomUniv());
        assertTrue(universiteRepository.existsById(savedUniv.getIdUniv()));
    }

    @Test
    @DisplayName("Should retrieve a university by ID")
    void testRetrieveUniversite() {
        Universite univ = new Universite("Universite Retrieve");
        Universite savedUniv = universiteRepository.save(univ);

        Universite retrievedUniv = universiteService.retrieveUniversite(savedUniv.getIdUniv());

        assertNotNull(retrievedUniv);
        assertEquals("Universite Retrieve", retrievedUniv.getNomUniv());
    }

    @Test
    @DisplayName("Should delete a university by ID")
    void testDeleteUniversite() {
        Universite univ = new Universite("Universite Delete");
        Universite savedUniv = universiteRepository.save(univ);

        universiteService.deleteUniversite(savedUniv.getIdUniv());

        assertFalse(universiteRepository.existsById(savedUniv.getIdUniv()));
    }

    @Test
    @DisplayName("Should update an existing university")
    void testUpdateUniversite() {
        Universite univ = new Universite("Old Universite");
        univ = universiteRepository.save(univ); // Save to get an ID
        univ.setNomUniv("Updated Universite");

        Universite updatedUniv = universiteService.updateUniversite(univ);
        assertEquals("Updated Universite", updatedUniv.getNomUniv());
    }

    @Test
    @DisplayName("Should assign a department to a university")
    void testAssignDepartementToUniversite() {
        Universite univ = new Universite("Universite Assign");
        universiteRepository.save(univ);

        Departement dept = new Departement();
        dept.setNomDepart("Departement Test");
        departementRepository.save(dept);

        universiteService.assignUniversiteToDepartement(univ.getIdUniv(), dept.getIdDepart());

        Universite updatedUniv = universiteService.retrieveUniversite(univ.getIdUniv());
        assertNotNull(updatedUniv.getDepartements());
        assertTrue(updatedUniv.getDepartements().contains(dept));
    }

    @Test
    @DisplayName("Should retrieve departments of a university")
    void testRetrieveDepartementsByUniversite() {
        Universite univ = new Universite("Universite With Departements");
        univ = universiteRepository.save(univ);

        Departement dept1 = new Departement();
        dept1.setNomDepart("Departement 1");
        departementRepository.save(dept1);

        Departement dept2 = new Departement();
        dept2.setNomDepart("Departement 2");
        departementRepository.save(dept2);

        universiteService.assignUniversiteToDepartement(univ.getIdUniv(), dept1.getIdDepart());
        universiteService.assignUniversiteToDepartement(univ.getIdUniv(), dept2.getIdDepart());

        Set<Departement> departements = universiteService.retrieveDepartementsByUniversite(univ.getIdUniv());

        assertNotNull(departements);
        assertEquals(2, departements.size());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent university")
    void testUpdateNonExistentUniversite() {
        Universite univ = new Universite();
        univ.setIdUniv(999);
        univ.setNomUniv("NonExistent");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            universiteService.updateUniversite(univ);
        });

        assertEquals("Universite not found with id: 999", exception.getMessage());
    }


    @Test
    @DisplayName("Should retrieve all universities")
    void testRetrieveAllUniversitesAfterAdding() {
        Universite univ1 = new Universite("Universite 1");
        Universite univ2 = new Universite("Universite 2");

        universiteRepository.save(univ1);
        universiteRepository.save(univ2);

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertEquals(2, universites.size(), "Should retrieve two universities");
    }
}
