package com.nnk.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;

/**
 * Implémentation du service utilisateur qui gère les opérations CRUD
 * liées à l'entité {@link User}.
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    /**
     * Constructeur avec injection du repository utilisateur.
     * 
     * @param userRepository Le repository qui permet d'accéder aux données
     *                       utilisateur.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crée un nouvel utilisateur et l'enregistre dans la base de données.
     * 
     * @param user L'utilisateur à créer.
     * @return L'utilisateur créé et enregistré.
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant à l'identifiant, ou null s'il n'existe
     *         pas.
     */
    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Récupère la liste de tous les utilisateurs.
     * 
     * @return La liste des utilisateurs enregistrés.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Met à jour un utilisateur existant.
     * 
     * @param id   L'identifiant de l'utilisateur à mettre à jour.
     * @param user Les nouvelles informations de l'utilisateur.
     * @return L'utilisateur mis à jour, ou null si l'utilisateur n'existe pas.
     */
    @Override
    public void updateUser(Integer id, User userUpated) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isPresent()) {
            User userFound = userToUpdate.get();
            userFound.setFullname(userUpated.getFullname());
            userFound.setRole(userUpated.getRole());
            userFound.setPassword(userUpated.getPassword());
            userRepository.save(userFound);
        }
    }

    /**
     * Supprime un utilisateur par son identifiant.
     * 
     * @param id L'identifiant de l'utilisateur à supprimer.
     */
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
