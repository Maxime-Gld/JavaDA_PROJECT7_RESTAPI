package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nnk.springboot.service.user.CustomUserDetailsService;

/**
 * La classe SpringSecurityConfig configure les paramètres de sécurité de
 * l'application
 * en utilisant Spring Security.
 * 
 * Elle spécifie les règles de gestion des requêtes HTTP, les mécanismes de
 * login, de logout,
 * ainsi que la gestion de l'authentification avec un encodeur de mot de passe
 * {@link BCryptPasswordEncoder}.
 * 
 * Cette classe utilise un {@link CustomUserDetailsService} pour charger les
 * utilisateurs
 * et leurs rôles depuis une source de données.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Configure la chaîne de filtres de sécurité pour gérer l'autorisation des
	 * requêtes HTTP.
	 * 
	 * Cette méthode désactive la protection CSRF, permet l'accès public à certaines
	 * URL
	 * (telles que les pages de connexion, les pages utilisateur, et les ressources
	 * CSS), et
	 * force l'authentification pour toutes les autres requêtes.
	 * 
	 * Le formulaire de connexion est configuré pour rediriger vers l'URL
	 * "/bidList/list"
	 * après une authentification réussie.
	 * 
	 * @param http l'objet {@link HttpSecurity} permettant de configurer la sécurité
	 *             des requêtes HTTP
	 * @return un objet {@link SecurityFilterChain} représentant la chaîne de
	 *         filtres de sécurité
	 * @throws Exception en cas de configuration incorrecte de la sécurité
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable()) // Désactivation de la protection CSRF
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/user/**", "/css/**") // Autorisation publique
																		// sur certaines URL
						.permitAll()
						.anyRequest().authenticated()) // Authentification requise pour toute
														// autre requête
				.formLogin(form -> form
						.defaultSuccessUrl("/bidList/list", true) // Redirection après succès du
																	// login
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/app-logout") // URL de logout
						.logoutSuccessUrl("/") // Redirection lorsque l'utilisateur se déconnecte
						.permitAll()) // Autorisation publique pour le logout
				.build();
	}

	/**
	 * Crée et configure un encodeur de mot de passe utilisant l'algorithme BCrypt.
	 * 
	 * @return un objet {@link BCryptPasswordEncoder} pour encoder les mots de passe
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configure le gestionnaire d'authentification de l'application.
	 * 
	 * Il utilise le {@link CustomUserDetailsService} pour charger les détails des
	 * utilisateurs
	 * et le {@link BCryptPasswordEncoder} pour encoder les mots de passe.
	 * 
	 * @param http                  l'objet {@link HttpSecurity} utilisé pour
	 *                              obtenir les composants partagés
	 * @param bCryptPasswordEncoder l'encodeur de mot de passe utilisé pour vérifier
	 *                              les mots de passe
	 * @return un objet {@link AuthenticationManager} pour gérer l'authentification
	 *         des utilisateurs
	 * @throws Exception en cas d'erreur dans la configuration du gestionnaire
	 *                   d'authentification
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
			BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}

}
