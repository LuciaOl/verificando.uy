package model;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los remitentes están encargados de seleccionar y presentar hechos para verificación.
// Estos hechos pueden provenir de sugerencias de ciudadanos o de mecanismos automáticos.
// Los remitentes gestionan una lista de hechos que han presentado.
public class Submitter extends Usuario {
    private String organizationName;
    private List<String> submittedFacts;

    // Constructor
    public Submitter(String userID, String fullName, String email, String role, String organizationName) {
        super(userID, fullName, email, role);
        this.organizationName = organizationName;
        this.submittedFacts = new ArrayList<>();
    }

    // Getters and Setters
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

