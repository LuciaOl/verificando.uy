package dtos;

import model.Hecho;

import java.time.LocalDateTime;

public class DtVerificacion {
    private Hecho hecho; // El hecho que se está verificando
    private boolean esVerdadero; // Resultado de la verificación
    private String justificacion; // Justificación del verificador
    private LocalDateTime fechaVerificacion;

    public Hecho getHecho() {
        return hecho;
    }

    public void setHecho(Hecho hecho) {
        this.hecho = hecho;
    }

    public boolean esVerdadero() {
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
