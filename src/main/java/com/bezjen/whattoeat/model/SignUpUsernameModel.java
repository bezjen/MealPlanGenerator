package com.bezjen.whattoeat.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SignUpUsernameModel {
    @NotBlank(message = "{messages.error.signup.username.empty}")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-.]{3,30}$", message = "{messages.error.signup.username.format}")
    private String inputValue;

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }
}
