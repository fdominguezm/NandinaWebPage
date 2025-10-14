package com.nandina.api.forms.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class BiggerThanValidator implements ConstraintValidator<BiggerThan,Object> {
    private String field;
    private String secondField;

    @Override
    public void initialize(BiggerThan constraint) {
        this.field = constraint.field();
        this.secondField = constraint.secondField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Long fieldValue = (Long) new BeanWrapperImpl(value).getPropertyValue(field);
        Long secondFieldValue = (Long) new BeanWrapperImpl(value).getPropertyValue(secondField);

        if(Objects.isNull(fieldValue) || Objects.isNull(secondFieldValue)){
            return true;
        }
        return secondFieldValue > fieldValue;
    }


}
