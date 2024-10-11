package com.nnk.springboot.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.repositories.UserRepository;

import jakarta.transaction.Transactional;

/**
 * CustomUserDetailsService est une implémentation de l'interface
 * {@link UserDetailsService}
 * qui fournit une méthode pour charger les détails d'un utilisateur par son nom
 * d'utilisateur.
 * 
 * Cette classe interagit avec un {@link UserRepository} pour récupérer les
 * informations
 * d'utilisateur depuis la base de données et les convertir en un objet
 * {@link UserDetails},
 * qui est utilisé par Spring Security pour l'authentification et
 * l'autorisation.
 * 
 * La méthode {@link #loadUserByUsername(String)} est annotée avec
 * {@link Transactional}
 * pour s'assurer que les opérations de récupération d'utilisateur soient
 * exécutées dans
 * le cadre d'une transaction.
 * 
 * Les autorités (rôles) de l'utilisateur sont générées via la méthode privée
 * {@link #getGrantedAuthorities(String)}, qui retourne une liste de
 * {@link GrantedAuthority},
 * incluant le rôle préfixé de "ROLE_".
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge un utilisateur par son nom d'utilisateur et renvoie un objet
     * {@link UserDetails}.
     * Si aucun utilisateur n'est trouvé avec le nom d'utilisateur fourni, une
     * exception
     * {@link UsernameNotFoundException} est lancée.
     *
     * @param username le nom d'utilisateur de l'utilisateur à rechercher
     * @return un objet {@link UserDetails} contenant les détails de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec ce
     *                                   nom d'utilisateur
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(
                user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        getGrantedAuthorities(user.getRole())))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Génère une liste de {@link GrantedAuthority} pour l'utilisateur basé sur son
     * rôle.
     * Le rôle est préfixé par "ROLE_" tel qu'attendu par Spring Security.
     * 
     * @param role le rôle de l'utilisateur
     * @return une liste de {@link GrantedAuthority} contenant les rôles de
     *         l'utilisateur
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
