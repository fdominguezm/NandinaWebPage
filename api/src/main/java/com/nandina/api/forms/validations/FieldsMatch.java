package com.nandina.api.forms.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { FieldsMatchValidator.class})
@Target({TYPE,ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface   FieldsMatch {

    String message() default "Fields don't match";

    Class<?>[] groups() default {};

    String field();

    String secondField();

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @interface List {
        FieldsMatch[] value();
    }

}
