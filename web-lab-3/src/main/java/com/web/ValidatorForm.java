package com.web;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;


@FacesValidator("ValidatorForm")
public class ValidatorForm implements Validator<Double> {

    private static final double MIN = -3.0;
    private static final double MAX = 3.0;

    @Override
    public void validate(FacesContext context, UIComponent component, Double value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Ошибка",
                    "Поле Y обязательно для заполнения"
            ));
        }
        if (value < MIN || value > MAX) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Ошибка",
                    "Значение Y должно быть в диапазоне от " + MIN + " до " + MAX + "."
            ));
        }
    }

}
