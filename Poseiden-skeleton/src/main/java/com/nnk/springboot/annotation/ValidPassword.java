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

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password"; // Message par défaut

    Class<?>[] groups() default {}; // Pour le regroupement de validations

    Class<? extends Payload>[] payload() default {}; // Pour les métadonnées
}
