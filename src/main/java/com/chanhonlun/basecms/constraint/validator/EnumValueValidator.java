package com.chanhonlun.basecms.constraint.validator;

import com.chanhonlun.basecms.constraint.annotation.Enum;

import javax.validation.ConstraintValidator;
        import javax.validation.ConstraintValidatorContext;
        import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        return value == null && !annotation.required()
                || value != null && Arrays.stream(annotation.enumClass().getEnumConstants())
                .map(java.lang.Enum::toString)
                .anyMatch(enumValue -> enumValue.equals(value) || annotation.ignoreCase() && enumValue.equalsIgnoreCase(value));
    }
}