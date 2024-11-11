package tn.esprit.spring.kaddem.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class EquipeImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    private Equipe equipe;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks et de l'objet à tester
        MockitoAnnotations.openMocks(this);

        // Création d'une équipe pour les tests
        equipe = new Equipe(1, "Equipe A", Niveau.JUNIOR);
    }

    @Test
    void testRetrieveAllEquipes() {
        // Arranger le comportement du mock
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(equipe));

        // Appel de la méthode
        List<Equipe> equipes = equipeService.retrieveAllEquipes();

        // Vérifications
        assertEquals(1, equipes.size()); // Assurez-vous que la taille de la liste est correcte
        assertEquals("Equipe A", equipes.get(0).getNomEquipe()); // Vérifiez que le nom de l'équipe est bien "Equipe A"
    }


    @Test
    void testRetrieveEquipe() {
        // Arranger le comportement du mock
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // Appel de la méthode
        Equipe result = equipeService.retrieveEquipe(1);

        // Vérifications
        assertNotNull(result);
        assertEquals("Equipe A", result.getNomEquipe());
    }

    @Test
    void testAddEquipe() {
        // Arranger le comportement du mock
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);

        // Appel de la méthode
        Equipe result = equipeService.addEquipe(equipe);

        // Vérifications
        assertNotNull(result);
        assertEquals("Equipe A", result.getNomEquipe());
    }

    @Test
    void testDeleteEquipe() {
        // Arranger le comportement du mock
        when(equipeRepository.findById(1)).thenReturn(Optional.of(equipe));

        // Appel de la méthode
        equipeService.deleteEquipe(1);

        // Vérifications
        verify(equipeRepository, times(1)).delete(equipe);
    }

    @Test
    void testUpdateEquipe() {
        // Arranger le comportement du mock
        when(equipeRepository.save(any(Equipe.class))).thenReturn(equipe);

        // Appel de la méthode
        Equipe updatedEquipe = new Equipe(1, "Equipe B", Niveau.SENIOR);
        Equipe result = equipeService.updateEquipe(updatedEquipe);

        // Vérifications
        assertNotNull(result);
        assertEquals("Equipe B", result.getNomEquipe());
    }

    @Test
    void testEvoluerEquipes() {
        // Arranger le comportement des mocks
        when(equipeRepository.findAll()).thenReturn(Arrays.asList(equipe));

        // Appel de la méthode
        equipeService.evoluerEquipes();

        // Vérifications
        // On vérifie que la méthode save a bien été appelée après modification de l'équipe
        verify(equipeRepository, times(1)).save(any(Equipe.class));
    }
}
