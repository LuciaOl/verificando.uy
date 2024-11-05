package verificando.uy.dtos;

public class DtLoginResponse {
    private String message;
    private DtUsuario usuario; 

    public DtLoginResponse(String message, DtUsuario usuario) {
        this.message = message;
        this.usuario = usuario;
    }

    // Getters y Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DtUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(DtUsuario usuario) {
        this.usuario = usuario;
    }
}
