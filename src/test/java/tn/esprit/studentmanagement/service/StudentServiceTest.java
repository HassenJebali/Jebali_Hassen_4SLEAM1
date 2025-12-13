package tn.esprit.studentmanagement.service;


import tn.esprit.studentmanagement.entities.Student;
import tn. esprit.studentmanagement.repositories.StudentRepository;
import org. junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.services.StudentService;

import java. time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit. jupiter.api.Assertions.*;
import static org.mockito. Mockito.*;

/*WebHOOK Test*/



@ExtendWith(MockitoExtension.class) // Intègre Mockito avec JUnit 5
class StudentServiceTest {


    @Mock
    private StudentRepository studentRepository;  // Simulation de la base de données


    @InjectMocks
    private StudentService studentService;  // Service à tester avec les mocks injectés

    // Objet etusiant pour effectuer les test
    private static Student createStudent(Long id, String Fname, String Lname,
                                         String email, String phone,
                                         LocalDate birthday, String address) {
        Student student = new Student();
        student. setIdStudent(id);
        student.setFirstName(Fname);
        student.setLastName(Lname);
        student.setEmail(email);
        student.setPhone(phone);
        student. setDateOfBirth(birthday);
        student.setAddress(address);
        return student;
    }

    // ========== TESTS POUR getAllStudents() ==========

    // Test avec différentes tailles de liste (1, 2, 5, 10 étudiants)
    @ParameterizedTest //Permet d'exécuter le même test avec différentes valeurs d'entrée
    @ValueSource(ints = {1, 2, 5, 10})
    void testGetAllStudents_WithDifferentSizes(int size) {
        // Arrange : Créer une liste avec le nombre d'étudiants spécifié
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            students.add(createStudent(
                    (long) i,
                    "Étudiant",
                    "Nom" + i,
                    "email" + i + "@example.com",
                    "21123456" + i,
                    LocalDate.of(2000, 1, i),
                    "Adresse " + i
            ));
        }
        when(studentRepository.findAll()).thenReturn(students);

        // Act : Appeler la méthode
        List<Student> result = studentService.getAllStudents();

        // Assert : Vérifier le résultat
        assertEquals(size, result.size(), "La taille de la liste doit être " + size);
        verify(studentRepository, times(1)). findAll();
    }

    // Test avec une liste vide
    @Test //Pour des cas spécifiques non paramétrables.
    void testGetAllStudents_EmptyList() {
        // Arrange : Préparation
        when(studentRepository. findAll()).thenReturn(new ArrayList<>());

        // Act : Exécution
        List<Student> result = studentService.getAllStudents();

        // Assert: Vérification
        assertTrue(result.isEmpty(), "La liste doit être vide");
        assertEquals(0, result.size());
    }

    /*Vérifier la récupération de tous les étudiants
    Cas testés :
    Listes de différentes tailles (1, 2, 5, 10 étudiants)
    Liste vide*/

    // ========== TESTS POUR getStudentById() ==========

    // Test avec différents IDs (étudiant trouvé)
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 100L, 999L})
    void testGetStudentById_Found(long id) {
        // Arrange : Créer un étudiant pour chaque ID
        Student student = createStudent(
                id,
                "Jean",
                "Dupont",
                "jean" + id + "@example.com",
                "2112345678",
                LocalDate. of(2000, 1, 15),
                "Rue de Paris"
        );
        when(studentRepository.findById(id)). thenReturn(Optional.of(student));

        // Act
        Student result = studentService.getStudentById(id);

        // Assert
        assertNotNull(result, "L'étudiant ne doit pas être null");
        assertEquals(id, result.getIdStudent());
        assertEquals("Jean", result.getFirstName());
        assertEquals("Dupont", result.getLastName());
        verify(studentRepository, times(1)). findById(id);
    }

    // Test avec différents IDs (étudiant non trouvé)
    @ParameterizedTest
    @ValueSource(longs = {999L, 888L, 777L})
    void testGetStudentById_NotFound(long id) {
        // Arrange
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Student result = studentService.getStudentById(id);

        // Assert
        assertNull(result, "L'étudiant doit être null si non trouvé");
        verify(studentRepository, times(1)).findById(id);
    }


    // ========== TESTS POUR saveStudent() ==========

    // Test avec différents noms d'étudiants
    @ParameterizedTest
    @ValueSource(strings = {"Dupont", "Martin", "Laurent", "Bernard"})
    void testSaveStudent_WithDifferentNames(String lastName) {
        // Arrange : Créer un étudiant avec le nom du paramètre
        Student student = createStudent(
                1L,
                "Jean",
                lastName,
                "jean." + lastName. toLowerCase() + "@example.com",
                "2112345678",
                LocalDate.of(2000, 1, 15),
                "Rue de Paris"
        );
        when(studentRepository.save(student)).thenReturn(student);

        // Act
        Student result = studentService.saveStudent(student);

        // Assert
        assertNotNull(result, "L'étudiant sauvegardé ne doit pas être null");
        assertEquals("Jean", result.getFirstName());
        assertEquals(lastName, result. getLastName());
        verify(studentRepository, times(1)).save(student);
    }

    // ========== TESTS POUR deleteStudent() ==========

    // Test avec différents IDs
    @ParameterizedTest
    @ValueSource(longs = {1L, 5L, 100L})
    void testDeleteStudent_WithDifferentIds(long id) {
        // Arrange
        doNothing().when(studentRepository). deleteById(id);

        // Act
        studentService.deleteStudent(id);

        // Assert : Vérifier que deleteById a été appelé une fois
        verify(studentRepository, times(1)).deleteById(id);
    }

    // ========== TESTS SUPPLÉMENTAIRES ==========

    // Test de sauvegarde avec des emails différents
    @ParameterizedTest
    @ValueSource(strings = {"jean@example.com", "marie@example. com", "pierre@example.com"})
    void testSaveStudent_WithDifferentEmails(String email) {
        // Arrange
        Student student = createStudent(
                1L,
                "Test",
                "User",
                email,
                "2112345678",
                LocalDate.of(2000, 1, 15),
                "Rue de Paris"
        );
        when(studentRepository.save(student)).thenReturn(student);

        // Act
        Student result = studentService. saveStudent(student);

        // Assert
        assertNotNull(result);
        assertEquals(email, result. getEmail());
        verify(studentRepository, times(1)).save(student);
    }

    // Test de sauvegarde avec des numéros de téléphone différents
    @ParameterizedTest
    @ValueSource(strings = {"2112345678", "2187654321", "2198765432"})
    void testSaveStudent_WithDifferentPhones(String phone) {
        // Arrange
        Student student = createStudent(
                1L,
                "Jean",
                "Dupont",
                "jean@example.com",
                phone,
                LocalDate.of(2000, 1, 15),
                "Rue de Paris"
        );
        when(studentRepository.save(student)).thenReturn(student);

        // Act
        Student result = studentService. saveStudent(student);

        // Assert
        assertNotNull(result);
        assertEquals(phone, result.getPhone());
        verify(studentRepository, times(1)).save(student);
    }
}