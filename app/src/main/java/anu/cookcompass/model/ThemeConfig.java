package anu.cookcompass.model;

import java.io.Serializable;

public class ThemeConfig implements Serializable {
    private String account;
    private String address;
    private String theme;

    public ThemeConfig(String account, String address, String theme) {
        this.account = account;
        this.address = address;
        this.theme = theme;
    }

    public void setTheme(String theme) {
        this.theme=theme;
    }

    public String getTheme() {
        return this.theme;
    }
}
