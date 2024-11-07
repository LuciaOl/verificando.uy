package verificando.uy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    private String fullName;
    @Column(unique = true)
    private String email;

    // Para ignorar en las responses 
    @JsonIgnore
    private String password;
    
    private String role;
    @Column(name = "gubuy_cedula")
    private String cedula;
    @Column(name = "gubuy_id_token", length = 1024)    
    private String id_token;
    @Column(name = "gubuy_refresh_token", length = 1024)   
    private String refresh_token;


    // Constructor
    public Usuario() {}

    public Usuario(String fullName, String email, String role) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public Usuario(String fullName, String email, String role, String cedula, String id_token, String refresh_token) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.cedula = cedula;
        this.id_token = id_token;
        this.refresh_token = refresh_token;
    }

    // Getters y Setters
    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCedula() {
        return cedula;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
