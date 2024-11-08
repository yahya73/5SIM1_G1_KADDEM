package tn.esprit.spring.kaddem.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EtudiantImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    void setUp() {

        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Trabelsi");
        etudiant.setPrenomE("Lotfi");
        etudiant.setOp(Option.SIM);
        lenient().when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);
        lenient().when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
    }

    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("Trabelsi");
        etudiant.setPrenomE("Lotfi");
        etudiant.setOp(Option.SIM);

        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        Assertions.assertThat(savedEtudiant).isNotNull();
        Assertions.assertThat(savedEtudiant.getNomE()).isEqualTo("Trabelsi");
    }

    @Test
    void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Trabelsi");
        etudiant.setPrenomE("Lotfi");
        etudiant.setOp(Option.SIM);

        when(etudiantRepository.findById(anyInt())).thenReturn(Optional.of(etudiant));

        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(etudiant.getIdEtudiant());

        Assertions.assertThat(retrievedEtudiant).isNotNull();
        Assertions.assertThat(retrievedEtudiant.getNomE()).isEqualTo("Trabelsi");
    }

    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant = new Etudiant();
        etudiant.setIdEtudiant(1);
        etudiant.setNomE("Trabelsi");
        etudiant.setPrenomE("Lotfi");
        etudiant.setOp(Option.SIM);
        etudiants.add(etudiant);

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> retrievedEtudiants = etudiantService.retrieveAllEtudiants();

        Assertions.assertThat(retrievedEtudiants).isNotNull();
        Assertions.assertThat(retrievedEtudiants.size()).isEqualTo(1);
    }

    @Test
    void testDeleteEtudiant() {
        Etudiant mockEtudiant = new Etudiant();
        mockEtudiant.setIdEtudiant(1);
        mockEtudiant.setNomE("Trabelsi");

        when(etudiantRepository.findById(1)).thenReturn(Optional.of(mockEtudiant));
        doNothing().when(etudiantRepository).delete(mockEtudiant);

        etudiantService.removeEtudiant(1);
        verify(etudiantRepository).delete(mockEtudiant);
    }
}
