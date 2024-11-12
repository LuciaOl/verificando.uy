package verificando.uy.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Verificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador de la verificación
    @ManyToOne
    @JoinColumn(name = "id_hecho")
    @JsonBackReference // Define el lado "inverso" de la relación
    private Hecho hecho;
    private boolean esVerdadero; // Resultado de la verificación
    private String justificacion; // Justificación del verificador
    private LocalDateTime fechaVerificacion; // Fecha y hora de la verificación

    // Constructor
    public Verificacion(Hecho hecho, boolean esVerdadero, String justificacion) {
        this.hecho = hecho;
        this.esVerdadero = esVerdadero;
        this.justificacion = justificacion;
        this.fechaVerificacion = LocalDateTime.now(); // Fecha de verificación actual
    }

    public Verificacion() {

    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hecho getHecho() {
        return hecho;
    }

    public void setHecho(Hecho hecho) {
        this.hecho = hecho;
    }

    public boolean isEsVerdadero() {
        return esVerdadero;
    }

    public void setEsVerdadero(boolean esVerdadero) {
        this.esVerdadero = esVerdadero;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public LocalDateTime getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(LocalDateTime fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }
}
