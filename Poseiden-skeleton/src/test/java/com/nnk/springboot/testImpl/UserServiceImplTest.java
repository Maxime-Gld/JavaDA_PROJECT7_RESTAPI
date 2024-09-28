package com.nnk.springboot.testImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserServiceImpl;

/**
 * Classe de test pour {@link UserServiceImpl}.
 */
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    /**
     * Méthode exécutée avant chaque test pour initialiser les objets nécessaires.
     */
    @BeforeEach
    public void setup() {
        // Initialise les mocks
        MockitoAnnotations.openMocks(this);

        // Crée un objet User pour les tests
        user = new User();
        user.setId(1);
        user.setFullname("John Doe");
        user.setRole("ADMIN");
        user.setPassword("password123");
    }

    /**
     * Teste la création d'un utilisateur en utilisant un ArgumentCaptor pour
     * capturer et
     * vérifier l'objet User sauvegardé.
     */
    @Test
    public void testCreateUser() {
        // Utilisation d'ArgumentCaptor pour capturer l'argument passé à
        // userRepository.save
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        // Appelle la méthode à tester
        User createdUser = userService.createUser(user);

        // Vérifie que la méthode save du repository a été appelée une fois
        verify(userRepository, times(1)).save(userCaptor.capture());

        // Vérifie que les informations de l'utilisateur sont correctes
        assertEquals(userCaptor.getValue().getFullname(), createdUser.getFullname());
        assertEquals(userCaptor.getValue().getRole(), createdUser.getRole());
        assertEquals(userCaptor.getValue().getPassword(), createdUser.getPassword());
    }

    /**
     * Teste la récupération d'un utilisateur par son ID lorsqu'il existe.
     */
    @Test
    public void testGetUserById() {
        // Configure le mock pour retourner un utilisateur existant
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Appelle la méthode à tester
        User foundUser = userService.getUserById(1);

        // Vérifie que l'utilisateur trouvé n'est pas nul
        assertNotNull(foundUser);

        // Vérifie que le nom complet correspond à celui attendu
        assertEquals(user.getFullname(), foundUser.getFullname());

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(userRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération d'un utilisateur par son ID lorsqu'il n'existe pas.
     */
    @Test
    public void testGetUserByIdNotFound() {
        // Configure le mock pour retourner un Optional vide
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        User foundUser = userService.getUserById(1);

        // Vérifie que l'utilisateur trouvé est nul (puisqu'il n'existe pas)
        assertNull(foundUser);

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(userRepository, times(1)).findById(1);
    }

    /**
     * Teste la récupération de tous les utilisateurs.
     */
    @Test
    public void testGetAllUsers() {
        // Crée une liste d'utilisateurs pour le test
        List<User> users = Arrays.asList(user);

        // Configure le mock pour retourner la liste d'utilisateurs
        when(userRepository.findAll()).thenReturn(users);

        // Appelle la méthode à tester
        List<User> allUsers = userService.getAllUsers();

        // Vérifie que la taille de la liste est correcte
        assertEquals(1, allUsers.size());

        // Vérifie que le premier utilisateur de la liste correspond à celui attendu
        assertEquals(user.getFullname(), allUsers.get(0).getFullname());

        // Vérifie que la méthode findAll du repository a été appelée une fois
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Teste la mise à jour d'un utilisateur existant.
     */
    @Test
    public void testUpdateUser() {
        // Crée un nouvel utilisateur avec les nouvelles informations
        User updatedUser = new User();
        updatedUser.setFullname("Jane Doe");
        updatedUser.setRole("USER");
        updatedUser.setPassword("newPassword");

        // Configure le mock pour retourner l'utilisateur existant lors de la recherche
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Configure le mock pour retourner l'utilisateur mis à jour lors de la
        // sauvegarde
        when(userRepository.save(user)).thenReturn(user);

        // Appelle la méthode à tester
        userService.updateUser(1, updatedUser);

        // Vérifie que les informations ont été mises à jour correctement
        assertEquals(updatedUser.getFullname(), user.getFullname());
        assertEquals(updatedUser.getRole(), user.getRole());
        assertEquals(updatedUser.getPassword(), user.getPassword());

        // Vérifie que les méthodes du repository ont été appelées le nombre de fois
        // attendu
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Teste la mise à jour d'un utilisateur qui n'existe pas.
     */
    @Test
    public void testUpdateUserNotFound() {
        // Crée un nouvel utilisateur avec les nouvelles informations
        User updatedUser = new User();
        updatedUser.setFullname("Jane Doe");
        updatedUser.setRole("USER");
        updatedUser.setPassword("newPassword");

        // Configure le mock pour retourner un Optional vide lors de la recherche
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Appelle la méthode à tester
        userService.updateUser(1, updatedUser);

        // Vérifie que la méthode findById du repository a été appelée une fois
        verify(userRepository, times(1)).findById(1);

        // Vérifie que la méthode save n'a jamais été appelée
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Teste la suppression d'un utilisateur.
     */
    @Test
    public void testDeleteUser() {
        // Configure le mock pour ne rien faire lors de la suppression
        doNothing().when(userRepository).deleteById(1);

        // Appelle la méthode à tester
        userService.deleteUser(1);

        // Vérifie que la méthode deleteById du repository a été appelée une fois
        verify(userRepository, times(1)).deleteById(1);
    }
}
