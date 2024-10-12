package com.nnk.springboot.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nnk.springboot.validator.PasswordValidator;

/**
 * Annotation pour valider le mot de passe.
 */

@Constraint(validatedBy = PasswordValidator.class) // Classe de validation
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER }) // Pour les types
                                                                          // d'annotations
                                                                          // cibles (ici
                                                                          // pour les
                                                                          // champs/attributs/methodes/parametres)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password"; // Message par défaut

    Class<?>[] groups() default {}; // Pour le regroupement de validations (déclaration obligatoire au bon
                                    // fonctionnement du framework)

    Class<? extends Payload>[] payload() default {}; // Pour les métadonnées (déclaration obligatoire au bon
                                                     // fonctionnement)
}
