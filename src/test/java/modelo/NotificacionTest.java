package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Suite de Pruebas: Modelo Notificacion")
public class NotificacionTest {

    private Notificacion notificacion;
    private LocalDateTime fechaProxima;
    private LocalDateTime fechaDistante;

    @BeforeEach
    void setUp() {
        fechaProxima = LocalDateTime.now().plusHours(12);
        fechaDistante = LocalDateTime.now().plusDays(5);
        notificacion = new Notificacion("EXAMEN", "Examen de Programación", 
                                        "Recuerda estudiar capítulos 1-5", fechaProxima, "ING101");
    }

    @Test
    @DisplayName("Debe crear una notificación correctamente")
    void testCreacionNotificacion() {
        assertThat(notificacion.getTipo()).isEqualTo("EXAMEN");
        assertThat(notificacion.getTitulo()).isEqualTo("Examen de Programación");
        assertThat(notificacion.getMensaje()).isEqualTo("Recuerda estudiar capítulos 1-5");
        assertThat(notificacion.getCurso()).isEqualTo("ING101");
        assertThat(notificacion.isLeida()).isFalse();
    }

    @Test
    @DisplayName("Debe establecer prioridad ALTA cuando faltan menos de 24 horas")
    void testPrioridadAlta() {
        Notificacion notAlta = new Notificacion("EXAMEN", "Examen Urgente", 
                                                "Rápido!", LocalDateTime.now().plusHours(12), "ING101");
        assertThat(notAlta.getPrioridad()).isEqualTo(Notificacion.Prioridad.ALTA);
    }

    @Test
    @DisplayName("Debe establecer prioridad MEDIA cuando faltan 24-48 horas")
    void testPrioridadMedia() {
        LocalDateTime fechaMedia = LocalDateTime.now().plusHours(36);
        Notificacion notMedia = new Notificacion("ENTREGA", "Entrega Próxima", 
                                                 "Entrega en 36 horas", fechaMedia, "ING102");
        assertThat(notMedia.getPrioridad()).isEqualTo(Notificacion.Prioridad.MEDIA);
    }

    @Test
    @DisplayName("Debe establecer prioridad BAJA cuando faltan más de 48 horas")
    void testPrioridadBaja() {
        Notificacion notBaja = new Notificacion("RETIRO", "Plazo de Retiro", 
                                                "Aún hay tiempo", fechaDistante, "ING103");
        assertThat(notBaja.getPrioridad()).isEqualTo(Notificacion.Prioridad.BAJA);
    }

    @Test
    @DisplayName("Debe retornar formato de prioridad correctamente")
    void testPrioridadFormato() {
        String formato = notificacion.getPrioridadFormato();
        assertThat(formato).contains("ALTA");
    }

    @Test
    @DisplayName("Debe retornar emoji correcto por tipo de notificación")
    void testEmojiPorTipo() {
        Notificacion notExamen = new Notificacion("EXAMEN", "Examen", "msg", 
                                                   LocalDateTime.now().plusDays(1), "ING101");
        Notificacion notEntrega = new Notificacion("ENTREGA", "Entrega", "msg", 
                                                    LocalDateTime.now().plusDays(1), "ING102");
        Notificacion notRetiro = new Notificacion("RETIRO", "Retiro", "msg", 
                                                   LocalDateTime.now().plusDays(1), "ING103");
        
        assertThat(notExamen.getEmojiPorTipo()).isEqualTo("📝");
        assertThat(notEntrega.getEmojiPorTipo()).isEqualTo("📤");
        assertThat(notRetiro.getEmojiPorTipo()).isEqualTo("⏰");
    }

    @Test
    @DisplayName("Debe marcar notificación como leída")
    void testMarcarComoLeida() {
        assertThat(notificacion.isLeida()).isFalse();
        notificacion.setLeida(true);
        assertThat(notificacion.isLeida()).isTrue();
    }

    @Test
    @DisplayName("Debe retornar fecha evento en formato correcto")
    void testFechaEventoFormato() {
        String fecha = notificacion.getFechaEventoFormato();
        assertThat(fecha).matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}");
    }

    @Test
    @DisplayName("Debe retornar fecha envío en formato correcto")
    void testFechaEnvioFormato() {
        String fecha = notificacion.getFechaEnvioFormato();
        assertThat(fecha).matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}");
    }

    @Test
    @DisplayName("Debe calcular tiempo restante en minutos cuando faltan menos de una hora")
    void testTiempoRestanteMinutos() {
        Notificacion notMinutos = new Notificacion("EXAMEN", "Examen", "msg", 
                                                    LocalDateTime.now().plusMinutes(30), "ING101");
        String tiempo = notMinutos.getTiempo();
        assertThat(tiempo).contains("min");
    }

    @Test
    @DisplayName("Debe calcular tiempo restante en horas")
    void testTiempoRestanteHoras() {
        Notificacion notHoras = new Notificacion("EXAMEN", "Examen", "msg", 
                                                  LocalDateTime.now().plusHours(12), "ING101");
        String tiempo = notHoras.getTiempo();
        assertThat(tiempo).contains("h");
    }

    @Test
    @DisplayName("Debe calcular tiempo restante en días")
    void testTiempoRestanteDias() {
        Notificacion notDias = new Notificacion("EXAMEN", "Examen", "msg", 
                                                 LocalDateTime.now().plusDays(5), "ING101");
        String tiempo = notDias.getTiempo();
        assertThat(tiempo).contains("días");
    }

    @Test
    @DisplayName("Debe indicar 'Vencido' cuando la fecha ya pasó")
    void testNotificacionVencida() {
        Notificacion notVencida = new Notificacion("EXAMEN", "Examen Pasado", "msg", 
                                                    LocalDateTime.now().minusHours(1), "ING101");
        assertThat(notVencida.getTiempo()).isEqualTo("Vencido");
    }

    @Test
    @DisplayName("Debe retornar String correctamente")
    void testToString() {
        String resultado = notificacion.toString();
        assertThat(resultado).contains("Examen de Programación")
                              .contains("ALTA");
    }
}
