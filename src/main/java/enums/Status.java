package enums;

public enum Status {

    VERIFICADO("Verificado"),
    PENDIENTE("Pendiente"),
    RECHAZADO("Rechazado");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
