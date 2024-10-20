package model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los ciudadanos utilizan la plataforma para buscar hechos verificados, sugerir nuevos hechos para verificar y
// suscribirse a notificaciones sobre nuevos hechos verificados. Cada ciudadano tiene un historial de búsquedas, sugerencias
// realizadas y hechos a los que está suscrito.
@Entity
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Citizen extends Usuario {

    public String getTockenDispositivo() {
        return tockenDispositivo;
    }

    public void setTockenDispositivo(String tockenDispositivo) {
        this.tockenDispositivo = tockenDispositivo;
    }

    private String tockenDispositivo;

    @ManyToMany
    private List<Hecho> searchHistory;
    @OneToMany
    private List<Hecho> suggestions;
    @ManyToMany
    private List<Hecho> subscriptions;

    // Constructor
    public Citizen(String userID, String fullName, String email, String role) {
        super(userID, fullName, email, role);
        this.searchHistory = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
    }

    public Citizen() {

    }

    // Getters and Setters
    public List<Hecho> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(List<Hecho> searchHistory) {
        this.searchHistory = searchHistory;
    }

    public List<Hecho> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Hecho> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Hecho> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Hecho> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
