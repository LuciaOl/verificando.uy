package model;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los administradores tienen la capacidad de configurar la plataforma, gestionar usuarios y supervisar el proceso de verificación de hechos.

public class Admin extends Usuario {
    private List<Usuario> managedUsers;

    // Constructor
    public Admin(String userID, String fullName, String email, String role) {
        super(userID, fullName, email, role);
        this.managedUsers = new ArrayList<>();
    }

    // Getters and Setters
    public List<Usuario> getManagedUsers() {
        return managedUsers;
    }

    public void setManagedUsers(List<Usuario> managedUsers) {
        this.managedUsers = managedUsers;
    }

    public void manageUser(Usuario user) {
        this.managedUsers.add(user);
    }
}
