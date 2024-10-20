package model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los verificadores son responsables de evaluar y
// calificar los hechos presentados. Pueden consultar diferentes fuentes y nodos periféricos para obtener información adicional
// que ayude en la verificación.
@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Checker extends Usuario {
    private List<String> assignedFacts;
    private List<String> factsHistory;

    // Constructor
    public Checker(String userID, String fullName, String email, String role) {
        super(userID, fullName, email, role);
        this.assignedFacts = new ArrayList<>();
        this.factsHistory = new ArrayList<>();
    }

    public Checker() {

    }

    // Getters and Setters
    public List<String> getAssignedFacts() {
        return assignedFacts;
    }

    public void setAssignedFacts(List<String> assignedFacts) {
        this.assignedFacts = assignedFacts;
    }

    public List<String> getFactsHistory() {
        return factsHistory;
    }

    public void setFactsHistory(List<String> factsHistory) {
        this.factsHistory = factsHistory;
    }
}
