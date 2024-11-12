package verificando.uy.enums;

public enum Role {

    CITIZEN("Citizen"),
    SUBMITTER("Submitter"),
    CHECKER("Checker"),
    ADMIN("Admin");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
