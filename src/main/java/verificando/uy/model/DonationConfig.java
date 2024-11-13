package verificando.uy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DonationConfig {

    @Id
    private Long id = 1L; // Siempre habrá un único registro

    private String email;
    private String bankAccount;
    private String message;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}