package verificando.uy.verificando.model;

import java.util.ArrayList;
import java.util.List;

//Descripción: Los ciudadanos utilizan la plataforma para buscar hechos verificados, sugerir nuevos hechos para verificar y
// suscribirse a notificaciones sobre nuevos hechos verificados. Cada ciudadano tiene un historial de búsquedas, sugerencias
// realizadas y hechos a los que está suscrito.

public class Citizen extends Usuario {
    private List<String> searchHistory;
    private List<String> suggestions;
    private List<String> subscriptions;

    // Constructor
    public Citizen(String userID, String fullName, String email, String role) {
        super(userID, fullName, email, role);
        this.searchHistory = new ArrayList<>();
        this.suggestions = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
    }

    // Getters and Setters
    public List<String> getSearchHistory() {
        return searchHistory;
    }

    public void setSearchHistory(List<String> searchHistory) {
        this.searchHistory = searchHistory;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
