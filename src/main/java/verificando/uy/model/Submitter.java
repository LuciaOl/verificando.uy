package verificando.uy.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los remitentes están encargados de seleccionar y presentar hechos para verificación.
// Estos hechos pueden provenir de sugerencias de ciudadanos o de mecanismos automáticos.
// Los remitentes gestionan una lista de hechos que han presentado.
@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Submitter extends Usuario {

    private String organizationName;

    @ElementCollection // Anotación para listas de tipos básicos (String)
    private List<String> submittedFacts;

    // Constructor
    public Submitter(String fullName, String email, String role, String organizationName) {
        super(fullName, email, role);
        this.organizationName = organizationName;
        this.submittedFacts = new ArrayList<>();
    }

    public Submitter() {}

    // Getters y setters
    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<String> getSubmittedFacts() {
        return submittedFacts;
    }

    public void setSubmittedFacts(List<String> submittedFacts) {
        this.submittedFacts = submittedFacts;
    }
}

