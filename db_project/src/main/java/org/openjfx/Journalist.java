package org.openjfx;

public class Journalist {
    private String email = null;
    private Float money = null;

    public Journalist() {
    }

    public Journalist(String email, Float money) {
        this.email = email;
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

}
