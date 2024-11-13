package verificando.uy.model;

import jakarta.persistence.*;
import verificando.uy.enums.Role;
import java.util.ArrayList;
import java.util.List;

//Descripción: Los verificadores son responsables de evaluar y
// calificar los hechos presentados. Pueden consultar diferentes fuentes y nodos periféricos para obtener información adicional
// que ayude en la verificación.
@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Checker extends Usuario {

    @OneToMany(mappedBy = "assignedChecker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hecho> assignedFacts = new ArrayList<>();

    @ElementCollection
    private List<String> factsHistory;

    // Constructor
    public Checker(String fullName, String email, Role role) {
        super(fullName, email, role);
        this.assignedFacts = new ArrayList<>();
        this.factsHistory = new ArrayList<>();
    }

    public Checker() {}

    // Getters y setters

    public List<Hecho> getAssignedFacts() {
        return assignedFacts;
    }

    public void setAssignedFacts(List<Hecho> assignedFacts) {
        this.assignedFacts = assignedFacts;
    }

    public List<String> getFactsHistory() {
        return factsHistory;
    }

    public void setFactsHistory(List<String> factsHistory) {
        this.factsHistory = factsHistory;
    }
    public void addFact(Hecho hecho) {
        assignedFacts.add(hecho);
    }
}

