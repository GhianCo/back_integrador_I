package modules.user.models;

import shared.models.Person;

public class User extends Person {

    private int user_id;
    private String nick;
    private String pass;
    private String role;
    private String active;

    public User() {
    }

    public User(int user_id, int person_id, String nick, String pass, String role, String active) {
        this.user_id = user_id;
        this.nick = nick;
        this.pass = pass;
        this.role = role;
        this.active = active;
        this.setPerson_id(person_id);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
