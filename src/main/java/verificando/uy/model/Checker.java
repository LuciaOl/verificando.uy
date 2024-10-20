package verificando.uy.model;

import jakarta.persistence.ElementCollection;
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

    @ElementCollection // Anotación para listas de tipos básicos (String)
    private List<String> assignedFacts;

    @ElementCollection // También es necesaria aquí
    private List<String> factsHistory;

    // Constructor
    public Checker(String fullName, String email, String role) {
        super(fullName, email, role);
        this.assignedFacts = new ArrayList<>();
        this.factsHistory = new ArrayList<>();
    }

    public Checker() {}

    // Getters y setters
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

