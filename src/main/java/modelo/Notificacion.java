package modelo;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Notificacion {
    private String tipo;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaEvento;
    private LocalDateTime fechaEnvio;
    private boolean leida;
    private String curso;

    public enum TipoNotificacion {
        EXAMEN, ENTREGA, RETIRO
    }

    public enum Prioridad {
        ALTA, MEDIA, BAJA
    }

    public Notificacion(String tipo, String titulo, String mensaje, 
                       LocalDateTime fechaEvento, String curso) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaEvento = fechaEvento;
        this.fechaEnvio = LocalDateTime.now();
        this.leida = false;
        this.curso = curso;
    }

    public Prioridad getPrioridad() {
        long horasRestantes = Duration.between(LocalDateTime.now(), fechaEvento).toHours();
        
        if (horasRestantes < 24) {
            return Prioridad.ALTA;
        } else if (horasRestantes < 48) {
            return Prioridad.MEDIA;
        } else {
            return Prioridad.BAJA;
        }
    }

    public String getPrioridadFormato() {
        Prioridad prioridad = getPrioridad();
        switch (prioridad) {
            case ALTA:
                return "🔴 ALTA";
            case MEDIA:
                return "🟠 MEDIA";
            case BAJA:
                return "🟢 BAJA";
            default:
                return "BAJA";
        }
    }

    public String getEmojiPorTipo() {
        switch (tipo.toUpperCase()) {
            case "EXAMEN":
                return "📝";
            case "ENTREGA":
                return "📤";
            case "RETIRO":
                return "⏰";
            default:
                return "📢";
        }
    }

    public String getFechaEventoFormato() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaEvento.format(formatter);
    }

    public String getFechaEnvioFormato() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fechaEnvio.format(formatter);
    }

    public String getTiempo() {
        long minutos = Duration.between(LocalDateTime.now(), fechaEvento).toMinutes();
        
        if (minutos < 0) {
            return "Vencido";
        } else if (minutos < 60) {
            return minutos + " min";
        } else if (minutos < 1440) {
            long horas = minutos / 60;
            return horas + " h";
        } else {
            long dias = minutos / 1440;
            return dias + " días";
        }
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEvento() {
        return fechaEvento;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public boolean isLeida() {
        return leida;
    }

    public void setLeida(boolean leida) {
        this.leida = leida;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return titulo + " - " + getPrioridadFormato();
    }
}
