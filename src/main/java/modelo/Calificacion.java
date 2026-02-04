package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Calificacion {
    private String codigoCurso;
    private String nombreCurso;
    private int creditos;
    private double nota;
    private String periodo;
    private String estado;
    private LocalDateTime fecha;

    public enum Estado {
        APROBADO, REPROBADO
    }

    public Calificacion(String codigoCurso, String nombreCurso, int creditos, 
                       double nota, String periodo) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
        this.creditos = creditos;
        this.nota = nota;
        this.periodo = periodo;
        this.estado = nota >= 7.0 ? "APROBADO" : "REPROBADO";
        this.fecha = LocalDateTime.now();
    }

    public double getNotaPonderada() {
        return nota * creditos;
    }

    public String obtenerEstado() {
        return estado;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public int getCreditos() {
        return creditos;
    }

    public double getNota() {
        return nota;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaFormato() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formatter);
    }

    @Override
    public String toString() {
        return codigoCurso + " - " + nombreCurso + " (" + nota + ")";
    }
}
