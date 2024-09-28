package com.nnk.springboot.validator;

import com.nnk.springboot.annotation.ValidPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValid = true; // Pour vérifier la validité globale

        // Désactiver le message de violation par défaut
        context.disableDefaultConstraintViolation();

        // Vérification si le mot de passe est null
        if (password == null) {
            context.buildConstraintViolationWithTemplate("Password cannot be null")
                    .addConstraintViolation();
            isValid = false;
        } else {
            // Vérification de la longueur minimale de 8 caractères
            if (password.length() < 8) {
                context.buildConstraintViolationWithTemplate("Password must be at least 8 characters long")
                        .addConstraintViolation();
                isValid = false;
            }

            // Vérification pour au moins une lettre majuscule
            if (!password.matches(".*[A-Z].*")) {
                context.buildConstraintViolationWithTemplate("Password must contain at least one uppercase letter")
                        .addConstraintViolation();
                isValid = false;
            }

            // Vérification pour au moins un chiffre
            if (!password.matches(".*\\d.*")) {
                context.buildConstraintViolationWithTemplate("Password must contain at least one digit")
                        .addConstraintViolation();
                isValid = false;
            }

            // Vérification pour au moins un symbole spécial
            if (!password.matches(".*[@$!%*?&].*")) {
                context.buildConstraintViolationWithTemplate(
                        "Password must contain at least one special character (@$!%*?&)")
                        .addConstraintViolation();
                isValid = false;
            }
        }

        // Retourne true si le mot de passe est valide, false sinon
        return isValid;
    }
}
