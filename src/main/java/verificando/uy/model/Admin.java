package verificando.uy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;

//Descripción: Los administradores tienen la capacidad de configurar la plataforma, gestionar usuarios y supervisar el proceso de verificación de hechos.
@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Admin extends Usuario {

    @OneToMany
    private List<Usuario> managedUsers;

    // Constructor
    public Admin(String fullName, String email, String role) {
        super(fullName, email, role);
        this.managedUsers = new ArrayList<>();
    }

    public Admin() {}

    // Getters y setters
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
