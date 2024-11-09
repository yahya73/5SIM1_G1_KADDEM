package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceTest {

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Test
    @DisplayName("Should retrieve all universites using mock")
    void testRetrieveAllUniversites() {
        Universite univ1 = new Universite(1, "Univ1");
        Universite univ2 = new Universite(2, "Univ2");
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertNotNull(universites);
        assertEquals(2, universites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should add a new universite using mock")
    void testAddUniversite() {
        Universite univ = new Universite(null, "NewUniv");
        when(universiteRepository.save(any(Universite.class))).thenReturn(univ);

        Universite savedUniv = universiteService.addUniversite(univ);

        assertNotNull(savedUniv);
        assertEquals("NewUniv", savedUniv.getNomUniv());
        verify(universiteRepository, times(1)).save(univ);
    }

    @Test
    @DisplayName("Should update an existing universite using mock")
    void testUpdateUniversite() {
        Universite univ = new Universite(1, "UpdatedUniv");
        when(universiteRepository.save(any(Universite.class))).thenReturn(univ);

        Universite updatedUniv = universiteService.updateUniversite(univ);

        assertNotNull(updatedUniv);
        assertEquals("UpdatedUniv", updatedUniv.getNomUniv());
        verify(universiteRepository, times(1)).save(univ);
    }

    @Test
    @DisplayName("Should retrieve universite by ID using mock")
    void testRetrieveUniversite() {
        Universite univ = new Universite(1, "Univ1");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(univ));

        Universite retrievedUniv = universiteService.retrieveUniversite(1);

        assertNotNull(retrievedUniv);
        assertEquals("Univ1", retrievedUniv.getNomUniv());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should delete universite by ID using mock")
    void testDeleteUniversite() {
        Universite univ = new Universite(1, "UnivToDelete");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(univ));

        universiteService.deleteUniversite(1);

        verify(universiteRepository, times(1)).delete(univ);
    }

    @Test
    @DisplayName("Should assign a departement to a universite using mock")
    void testAssignUniversiteToDepartement() {
        Universite univ = new Universite(1, "Univ1");
        Departement dep = new Departement();
        dep.setIdDepart(1);
        dep.setNomDepart("Dep1");

        when(universiteRepository.findById(1)).thenReturn(Optional.of(univ));
        when(departementRepository.findById(1)).thenReturn(Optional.of(dep));

        universiteService.assignUniversiteToDepartement(1, 1);

        assertTrue(univ.getDepartements().contains(dep));
        verify(universiteRepository, times(1)).save(univ);
    }

    @Test
    @DisplayName("Should retrieve departements by universite ID using mock")
    void testRetrieveDepartementsByUniversite() {
        Departement dep1 = new Departement();
        dep1.setIdDepart(1);
        dep1.setNomDepart("Dep1");
        Departement dep2 = new Departement();
        dep2.setIdDepart(2);
        dep2.setNomDepart("Dep2");

        Universite univ = new Universite(1, "UnivWithDeps");
        univ.setDepartements(new HashSet<>(Arrays.asList(dep1, dep2)));
        when(universiteRepository.findById(1)).thenReturn(Optional.of(univ));

        Set<Departement> departements = universiteService.retrieveDepartementsByUniversite(1);

        assertNotNull(departements);
        assertEquals(2, departements.size());
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Should throw exception when adding a universite with null name")
    void testAddUniversiteWithoutName() {
        Universite univ = new Universite(null, null); // No name

        when(universiteRepository.save(any(Universite.class))).thenThrow(new IllegalArgumentException("Universite name cannot be null"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            universiteService.addUniversite(univ);
        });

        assertEquals("Universite name cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when updating a universite with empty name")
    void testUpdateUniversiteWithEmptyName() {
        Universite univ = new Universite(1, "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            universiteService.updateUniversite(univ);
        });

        assertEquals("Universite name cannot be empty", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when retrieving non-existent universite by ID")
    void testRetrieveNonExistentUniversite() {
        when(universiteRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            universiteService.retrieveUniversite(999);
        });

        assertEquals("Universite not found with id: 999", exception.getMessage());
    }
}
