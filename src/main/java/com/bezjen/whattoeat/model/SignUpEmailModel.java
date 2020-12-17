package com.bezjen.whattoeat.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SignUpEmailModel {
    @NotBlank(message = "{messages.error.signup.email.empty}")
    @Email(message = "{messages.error.signup.email.format}")
    private String inputValue;

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
}
