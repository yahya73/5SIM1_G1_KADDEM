package tn.esprit.spring.kaddem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DepartementServiceImplTest {

    @InjectMocks
    private DepartementServiceImpl departementService;

    @Mock
    private DepartementRepository departementRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllDepartements() {
        List<Departement> departements = new ArrayList<>();
        // Add some sample departements to the list
        departements.add(new Departement(1, "Dept 1"));
        departements.add(new Departement(2, "Dept 2"));

        // Mock the behavior of the repository
        Mockito.when(departementRepository.findAll()).thenReturn(departements);

        List<Departement> retrievedDepartements = departementService.retrieveAllDepartements();

        assertNotNull(retrievedDepartements);
        assertEquals(2, retrievedDepartements.size());
    }

    @Test
    public void testAddDepartement() {
        Departement newDepartement = new Departement(3, "Dept 3");

        // Mock the behavior of the repository
        Mockito.when(departementRepository.save(newDepartement)).thenReturn(newDepartement);

        Departement addedDepartement = departementService.addDepartement(newDepartement);

        assertNotNull(addedDepartement);
        assertEquals(newDepartement.getIdDepart(), addedDepartement.getIdDepart());
        assertEquals(newDepartement.getNomDepart(), addedDepartement.getNomDepart());
    }

    @Test
    public void testUpdateDepartement() {
        Departement existingDepartement = new Departement(4, "Dept 4");

        // Mock the behavior of the repository
        Mockito.when(departementRepository.save(existingDepartement)).thenReturn(existingDepartement);

        Departement updatedDepartement = departementService.updateDepartement(existingDepartement);

        assertNotNull(updatedDepartement);
        assertEquals(existingDepartement.getIdDepart(), updatedDepartement.getIdDepart());
        assertEquals(existingDepartement.getNomDepart(), updatedDepartement.getNomDepart());
    }

    @Test
    public void testRetrieveDepartement() {
        int departementId = 5;
        Departement departement = new Departement(departementId, "Dept 5");

        // Mock the behavior of the repository
        Mockito.when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        Departement retrievedDepartement = departementService.retrieveDepartement(departementId);

        assertNotNull(retrievedDepartement);
        assertEquals(departementId, retrievedDepartement.getIdDepart());
        assertEquals(departement.getNomDepart(), retrievedDepartement.getNomDepart());
    }

    @Test
    public void testDeleteDepartement() {
        int departementId = 6;
        Departement departement = new Departement(departementId, "Dept 6");

        // Mock the behavior of the repository
        Mockito.when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        departementService.deleteDepartement(departementId);

        // Verify that delete method was called with the specified departement
        Mockito.verify(departementRepository, Mockito.times(1)).delete(departement);
    }
}
