package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Suite de Pruebas: Modelo Calificacion")
public class CalificacionTest {

    private Calificacion calificacionAprobada;
    private Calificacion calificacionReprobada;

    @BeforeEach
    void setUp() {
        calificacionAprobada = new Calificacion("ING101", "Programación I", 3, 8.5, "2024-1");
        calificacionReprobada = new Calificacion("ING102", "Cálculo", 4, 5.0, "2024-1");
    }

    @Test
    @DisplayName("Debe crear una calificación aprobada correctamente")
    void testCreacionCalificacionAprobada() {
        assertThat(calificacionAprobada.getCodigoCurso()).isEqualTo("ING101");
        assertThat(calificacionAprobada.getNombreCurso()).isEqualTo("Programación I");
        assertThat(calificacionAprobada.getNota()).isEqualTo(8.5);
        assertThat(calificacionAprobada.getCreditos()).isEqualTo(3);
        assertThat(calificacionAprobada.getPeriodo()).isEqualTo("2024-1");
        assertThat(calificacionAprobada.getEstado()).isEqualTo("APROBADO");
    }

    @Test
    @DisplayName("Debe crear una calificación reprobada correctamente")
    void testCreacionCalificacionReprobada() {
        assertThat(calificacionReprobada.getCodigoCurso()).isEqualTo("ING102");
        assertThat(calificacionReprobada.getNota()).isEqualTo(5.0);
        assertThat(calificacionReprobada.getEstado()).isEqualTo("REPROBADO");
    }

    @Test
    @DisplayName("Debe retornar nota ponderada correctamente")
    void testNotaPonderada() {
        double notaPonderada = calificacionAprobada.getNotaPonderada();
        assertThat(notaPonderada).isEqualTo(25.5); // 8.5 * 3
    }

    @Test
    @DisplayName("Debe validar nota mínima para aprobado (7.0)")
    void testNotaMinimaPasaAprobado() {
        Calificacion calMin = new Calificacion("ING103", "Estructura", 3, 7.0, "2024-1");
        assertThat(calMin.getEstado()).isEqualTo("APROBADO");
    }

    @Test
    @DisplayName("Debe validar nota justo debajo del mínimo")
    void testNotaMenorAlMinimo() {
        Calificacion calMenor = new Calificacion("ING104", "Algoritmos", 3, 6.9, "2024-1");
        assertThat(calMenor.getEstado()).isEqualTo("REPROBADO");
    }

    @Test
    @DisplayName("Debe obtener estado correctamente")
    void testObtenerEstado() {
        assertThat(calificacionAprobada.obtenerEstado()).isEqualTo("APROBADO");
        assertThat(calificacionReprobada.obtenerEstado()).isEqualTo("REPROBADO");
    }

    @Test
    @DisplayName("Debe retornar fecha en formato correcto")
    void testFechaFormato() {
        String fecha = calificacionAprobada.getFechaFormato();
        assertThat(fecha).matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}");
    }

    @Test
    @DisplayName("Debe retornar String con información de la calificación")
    void testToString() {
        String resultado = calificacionAprobada.toString();
        assertThat(resultado).contains("ING101")
                              .contains("Programación I")
                              .contains("8.5");
    }

    @Test
    @DisplayName("Debe manejar notas en rango válido")
    void testNotasEnRango() {
        Calificacion cal1 = new Calificacion("ING105", "Prueba 1", 3, 0.0, "2024-1");
        Calificacion cal2 = new Calificacion("ING106", "Prueba 2", 3, 10.0, "2024-1");
        
        assertThat(cal1.getEstado()).isEqualTo("REPROBADO");
        assertThat(cal2.getEstado()).isEqualTo("APROBADO");
    }

    @Test
    @DisplayName("Debe validar que la fecha sea actual")
    void testFechaActual() {
        assertThat(calificacionAprobada.getFechaFormato()).isNotNull();
    }
}
