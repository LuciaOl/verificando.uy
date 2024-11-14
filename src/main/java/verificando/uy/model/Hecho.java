package verificando.uy.model;

import verificando.uy.enums.Status;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static verificando.uy.enums.Status.PENDIENTE;

// Atributos: id, description, category, status, ratings, justifications
// Descripción: Un hecho es la información que se somete a verificación. Cada hecho tiene una descripción,
// una categoría (como salud, política o economía), un estado (verificado, pendiente o rechazado),
// y recibe calificaciones y justificaciones de los verificadores.
@Entity
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hecho")
    private Long id;
    private LocalDateTime fechaCreacion;
    private String description;
    private String category;
    private Status status; // Verificado, Pendiente, Rechazado
    @ElementCollection
    @CollectionTable(name = "rating", joinColumns = @JoinColumn(name = "id_hecho"))
    private List<Double> ratings;

    @ElementCollection
    @CollectionTable(name = "justification", joinColumns = @JoinColumn(name = "id_hecho"))
    private List<String> justifications;

    @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Verificacion> verificacions;


    // Constructor
    public Hecho(String description, String category) {
    	this.fechaCreacion = LocalDateTime.now();
        this.description = description;
        this.category = category;
        this.status = PENDIENTE;
        this.ratings = new ArrayList<>();
        this.justifications = new ArrayList<>();
        this.verificacions = new ArrayList<>();
    }

    public Hecho() {

    }

    // Getters y Setters
    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }

    public List<String> getJustifications() {
        return justifications;
    }

    public void setJustifications(List<String> justifications) {
        this.justifications = justifications;
    }

    public void addRating(Double rating) {
        this.ratings.add(rating);
    }

    public void addJustification(String justification) {
        this.justifications.add(justification);
    }

    public Double getAverageRating() {
        if (this.ratings.isEmpty()) {
            return 0.0;
        }
        Double sum = 0.0;
        for (Double rating : this.ratings) {
            sum += rating;
        }
        return sum / this.ratings.size();
    }

    public void clearRatings() {
        this.ratings.clear();
    }

    public void clearJustifications() {
        this.justifications.clear();
    }


    public List<Verificacion> getVerificacions() {
        return verificacions;
    }

    public void setVerificacions(List<Verificacion> verificacions) {
        this.verificacions = verificacions;
    }

    public void addVerification(Verificacion verificacion){
        this.verificacions.add(verificacion);
    }

    @Override
    public String toString() {
        return "ID: " + this.id + "\nDescripción: " + this.description + "\nCategoría: " + this.category + "\nEstado: " + this.status + "\nCalificaciones: " + this.ratings + "\nJustificaciones: " + this.justifications;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Hecho)) {
            return false;
        }
        Hecho hecho = (Hecho) obj;
        return hecho.id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
