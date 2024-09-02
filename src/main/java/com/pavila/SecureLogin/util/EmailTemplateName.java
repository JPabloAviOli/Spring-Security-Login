package com.pavila.SecureLogin.util;

import lombok.Getter;

@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("reset-password");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
