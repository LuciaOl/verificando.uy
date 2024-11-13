package verificando.uy.dtos;

public class DonationConfigDTO {

    private String email; // Email para consultas
    private String bankAccount; // Número de cuenta bancaria
    private String message; // Mensaje personalizado para donaciones

    // Constructor
    public DonationConfigDTO(String email, String bankAccount, String message) {
        this.email = email;
        this.bankAccount = bankAccount;
        this.message = message;
    }

    public DonationConfigDTO() {
    }

    // Getters y Setters
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
