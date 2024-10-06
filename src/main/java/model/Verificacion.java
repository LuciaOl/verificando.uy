package model;

import java.time.LocalDateTime;

public class Verificacion {
    private String id; // Identificador de la verificación
    private Hecho hecho; // El hecho que se está verificando
    private boolean esVerdadero; // Resultado de la verificación
    private String justificacion; // Justificación del verificador
    private LocalDateTime fechaVerificacion; // Fecha y hora de la verificación

    // Constructor
    public Verificacion(String id, Hecho hecho, boolean esVerdadero, String justificacion) {
        this.id = id;
        this.hecho = hecho;
        this.esVerdadero = esVerdadero;
        this.justificacion = justificacion;
        this.fechaVerificacion = LocalDateTime.now(); // Fecha de verificación actual
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
