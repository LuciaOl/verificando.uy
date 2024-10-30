package verificando.uy.dtos;

import verificando.uy.enums.Status;

import java.util.List;

public class DtHecho {

    private String description;
    private String category;
    private Status status; // Verificado, Pendiente, Rechazado
    private List<Double> ratings;
    private List<String> justifications;


    // Constructor
    public DtHecho(String description, String category, Status status, List<Double> ratings, List<String> justifications) {
        this.description = description;
        this.category = category;
        this.status = status;
        this.ratings = ratings;
        this.justifications = justifications;
    }

    public DtHecho() {

    }

    // Getters y Setters
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
}
