package application;

public class User {
    private String name;
    private String email;
    private String password;
    private String role;
    private String phone;
    private String address;

    public User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = "";
        this.address = "";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean checkLogin(String emailInput, String passwordInput) {
        return email.equals(emailInput) && password.equals(passwordInput);
    }

    @Override
    public String toString() {
        return name + " | " + email + " | " + role;
    }
}
