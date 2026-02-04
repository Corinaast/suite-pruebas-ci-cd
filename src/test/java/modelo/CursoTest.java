package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Suite de Pruebas: Modelo Curso")
public class CursoTest {

    private Curso curso;

    @BeforeEach
    void setUp() {
        curso = new Curso("ING101", "Programación I", "Ingeniería", 
                         "Dr. Carlos López", 3, "L-M-V 08:00-10:00", 40);
    }

    @Test
    @DisplayName("Debe crear un curso con los datos iniciales correctos")
    void testCreacionCurso() {
        assertThat(curso.getCodigo()).isEqualTo("ING101");
        assertThat(curso.getNombre()).isEqualTo("Programación I");
        assertThat(curso.getCreditos()).isEqualTo(3);
        assertThat(curso.getCapacidad()).isEqualTo(40);
        assertThat(curso.getInscritos()).isEqualTo(0);
        assertThat(curso.getCuposDisponibles()).isEqualTo(40);
    }

    @Test
    @DisplayName("Debe incrementar inscritos correctamente")
    void testIncrementarInscritos() {
        curso.incrementarInscritos();
        assertThat(curso.getInscritos()).isEqualTo(1);
        assertThat(curso.getCuposDisponibles()).isEqualTo(39);
    }

    @Test
    @DisplayName("No debe incrementar inscritos si se alcanza capacidad")
    void testIncrementarInscritosEnCapacidad() {
        Curso cursoLleno = new Curso("ING102", "Curso Pequeño", "Ingeniería", 
                                     "Prof. X", 3, "L-M-V", 2);
        cursoLleno.incrementarInscritos();
        cursoLleno.incrementarInscritos();
        
        assertThat(cursoLleno.getInscritos()).isEqualTo(2);
        cursoLleno.incrementarInscritos();
        assertThat(cursoLleno.getInscritos()).isEqualTo(2); // No debe incrementar
    }

    @Test
    @DisplayName("Debe decrementar inscritos correctamente")
    void testDecrementarInscritos() {
        curso.incrementarInscritos();
        curso.decrementarInscritos();
        assertThat(curso.getInscritos()).isEqualTo(0);
    }

    @Test
    @DisplayName("No debe decrementar inscritos si es cero")
    void testDecrementarInscritosEnCero() {
        curso.decrementarInscritos();
        assertThat(curso.getInscritos()).isEqualTo(0);
    }

    @Test
    @DisplayName("Debe retornar estado 'Disponible' cuando hay cupos")
    void testEstadoCuposDisponible() {
        assertThat(curso.getEstadoCupos()).isEqualTo("Disponible");
    }

    @Test
    @DisplayName("Debe retornar estado 'Pocos cupos' cuando quedan 5 o menos")
    void testEstadoCuposPocosDisponibles() {
        Curso cursoReducido = new Curso("ING103", "Curso Reducido", "Ingeniería", 
                                        "Prof. Y", 3, "L-M-V", 5);
        for (int i = 0; i < 4; i++) {
            cursoReducido.incrementarInscritos();
        }
        assertThat(cursoReducido.getEstadoCupos()).isEqualTo("Pocos cupos");
    }

    @Test
    @DisplayName("Debe retornar estado 'Sin cupos' cuando no hay disponibles")
    void testEstadoCuposSinDisponibles() {
        Curso cursoLleno = new Curso("ING104", "Curso Lleno", "Ingeniería", 
                                     "Prof. Z", 3, "L-M-V", 2);
        cursoLleno.incrementarInscritos();
        cursoLleno.incrementarInscritos();
        assertThat(cursoLleno.getEstadoCupos()).isEqualTo("Sin cupos");
    }

    @Test
    @DisplayName("Debe incrementar 1 vez correctamente")
    void testMultiplesIncrementos1() {
        for (int i = 0; i < 1; i++) {
            curso.incrementarInscritos();
        }
        assertThat(curso.getInscritos()).isEqualTo(1);
    }

    @Test
    @DisplayName("Debe incrementar 5 veces correctamente")
    void testMultiplesIncrementos5() {
        for (int i = 0; i < 5; i++) {
            curso.incrementarInscritos();
        }
        assertThat(curso.getInscritos()).isEqualTo(5);
    }

    @Test
    @DisplayName("Debe incrementar 10 veces correctamente")
    void testMultiplesIncrementos10() {
        for (int i = 0; i < 10; i++) {
            curso.incrementarInscritos();
        }
        assertThat(curso.getInscritos()).isEqualTo(10);
    }

    @Test
    @DisplayName("Debe incrementar 20 veces correctamente")
    void testMultiplesIncrementos20() {
        for (int i = 0; i < 20; i++) {
            curso.incrementarInscritos();
        }
        assertThat(curso.getInscritos()).isEqualTo(20);
    }

    @Test
    @DisplayName("Debe agregar prerequisito correctamente")
    void testAgregarPrerequisito() {
        curso.agregarPrerequisito("ING100");
        assertThat(curso.getPrerequisitos()).contains("ING100");
    }

    @Test
    @DisplayName("No debe agregar prerequisito duplicado")
    void testNoAgregarPrerequisitoDuplicado() {
        curso.agregarPrerequisito("ING100");
        curso.agregarPrerequisito("ING100");
        assertThat(curso.getPrerequisitos()).hasSize(1);
    }

    @Test
    @DisplayName("Debe retornar la representación en String correctamente")
    void testToString() {
        assertThat(curso.toString()).isEqualTo("ING101 - Programación I");
    }

    @Test
    @DisplayName("Debe calcular cupos disponibles correctamente")
    void testCuposDisponibles() {
        for (int i = 0; i < 15; i++) {
            curso.incrementarInscritos();
        }
        assertThat(curso.getCuposDisponibles()).isEqualTo(25);
    }
}
