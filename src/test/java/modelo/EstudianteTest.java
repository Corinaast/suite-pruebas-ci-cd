package modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Suite de Pruebas: Modelo Estudiante")
public class EstudianteTest {

    private Estudiante estudiante;
    private Curso curso1;
    private Curso curso2;
    private Curso curso3;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("STU001", "Juan García", "juan@university.edu");
        
        curso1 = new Curso("ING101", "Programación I", "Ingeniería", 
                          "Dr. López", 3, "L-M-V 08:00-10:00", 40);
        curso2 = new Curso("ING102", "Cálculo Diferencial", "Ingeniería", 
                          "Dra. García", 4, "L-M-V 10:00-12:00", 35);
        curso3 = new Curso("ING103", "Programación II", "Ingeniería", 
                          "Ing. Pérez", 3, "M-J 14:00-16:00", 30);
        curso3.agregarPrerequisito("ING101");
    }

    @Test
    @DisplayName("Debe crear un estudiante correctamente")
    void testCreacionEstudiante() {
        assertThat(estudiante.getCodigo()).isEqualTo("STU001");
        assertThat(estudiante.getNombre()).isEqualTo("Juan García");
        assertThat(estudiante.getEmail()).isEqualTo("juan@university.edu");
        assertThat(estudiante.getCursosInscritos()).isEmpty();
        assertThat(estudiante.getCursosAprobados()).isEmpty();
    }

    @Test
    @DisplayName("Debe permitir inscribirse en un curso disponible")
    void testInscribirseEnCurso() {
        estudiante.inscribirse(curso1);
        assertThat(estudiante.getCursosInscritos()).contains(curso1);
        assertThat(curso1.getInscritos()).isEqualTo(1);
    }

    @Test
    @DisplayName("No debe permitir doble inscripción")
    void testNoDobleInscripcion() {
        estudiante.inscribirse(curso1);
        assertThat(estudiante.puedeInscribir(curso1)).isFalse();
    }

    @Test
    @DisplayName("No debe permitir inscripción sin cupos")
    void testNoInscripcionSinCupos() {
        Curso cursoLleno = new Curso("ING104", "Curso Lleno", "Ingeniería", 
                                     "Prof. X", 3, "L-M-V", 1);
        cursoLleno.incrementarInscritos();
        
        assertThat(estudiante.puedeInscribir(cursoLleno)).isFalse();
    }

    @Test
    @DisplayName("No debe exceder el límite de 21 créditos")
    void testLimiteCreditosExcedido() {
        Curso curso4 = new Curso("ING105", "Estructura de Datos", "Ingeniería", 
                                "Ing. Martínez", 10, "L-M-V", 30);
        Curso curso5 = new Curso("ING106", "Álgebra Lineal", "Ingeniería", 
                                "Prof. Ramírez", 12, "M-J", 32);
        
        estudiante.inscribirse(curso4); // 10 créditos
        assertThat(estudiante.puedeInscribir(curso5)).isFalse(); // 10 + 12 = 22 > 21
    }

    @Test
    @DisplayName("Debe calcular créditos totales correctamente")
    void testCreditosTotales() {
        estudiante.inscribirse(curso1); // 3 créditos
        estudiante.inscribirse(curso2); // 4 créditos
        assertThat(estudiante.getCreditosTotales()).isEqualTo(7);
    }

    @Test
    @DisplayName("Debe validar prerequisitos correctamente")
    void testValidarPrerequisitos() {
        assertThat(estudiante.cumplePrerequisitos(curso3)).isFalse();
        
        Calificacion calAprobada = new Calificacion("ING101", "Programación I", 3, 8.0, "2024-1");
        estudiante.agregarCursoAprobado(calAprobada);
        assertThat(estudiante.cumplePrerequisitos(curso3)).isTrue();
    }

    @Test
    @DisplayName("Debe desinscribirse correctamente")
    void testDesinscribirse() {
        estudiante.inscribirse(curso1);
        assertThat(estudiante.getCursosInscritos()).contains(curso1);
        
        estudiante.desinscribirse(curso1);
        assertThat(estudiante.getCursosInscritos()).doesNotContain(curso1);
        assertThat(curso1.getInscritos()).isEqualTo(0);
    }

    @Test
    @DisplayName("Debe agregar curso aprobado")
    void testAgregarCursoAprobado() {
        Calificacion calificacion = new Calificacion("ING101", "Programación I", 3, 8.0, "2024-1");
        estudiante.agregarCursoAprobado(calificacion);
        assertThat(estudiante.getCursosAprobados()).contains(calificacion);
    }

    @Test
    @DisplayName("Debe actualizar datos del estudiante")
    void testActualizarDatos() {
        estudiante.setTelefono("3001234567");
        estudiante.setDireccion("Calle 10 #20-30");
        
        assertThat(estudiante.getTelefono()).isEqualTo("3001234567");
        assertThat(estudiante.getDireccion()).isEqualTo("Calle 10 #20-30");
    }

    @Test
    @DisplayName("Debe retornar lista de cursos inscritos correctamente")
    void testObtenerCursosInscritos() {
        estudiante.inscribirse(curso1);
        estudiante.inscribirse(curso2);
        assertThat(estudiante.getCursosInscritos()).hasSize(2)
                                                    .contains(curso1, curso2);
    }

    @Test
    @DisplayName("Debe permitir múltiples inscripciones dentro del límite")
    void testMultiplesInscripcionesValidas() {
        Curso cursoA = new Curso("ING201", "Curso A", "Ingeniería", "Prof. A", 5, "L-M-V", 40);
        Curso cursoB = new Curso("ING202", "Curso B", "Ingeniería", "Prof. B", 8, "M-J", 40);
        Curso cursoC = new Curso("ING203", "Curso C", "Ingeniería", "Prof. C", 8, "L-M-V", 40);
        
        estudiante.inscribirse(cursoA); // 5 créditos
        estudiante.inscribirse(cursoB); // 5 + 8 = 13 créditos
        assertThat(estudiante.puedeInscribir(cursoC)).isTrue(); // 13 + 8 = 21 (límite permitido)
    }

    @Test
    @DisplayName("Debe agregar y listar cursos aprobados correctamente")
    void testAgregarYListarCursosAprobados() {
        Calificacion cal1 = new Calificacion("ING101", "Programación I", 3, 8.5, "2024-1");
        Calificacion cal2 = new Calificacion("ING102", "Cálculo", 4, 6.5, "2024-1");
        
        estudiante.agregarCursoAprobado(cal1);
        estudiante.agregarCursoAprobado(cal2);
        
        assertThat(estudiante.getCursosAprobados()).hasSize(2);
        assertThat(estudiante.getCursosAprobados())
            .extracting(Calificacion::getCodigoCurso)
            .containsExactly("ING101", "ING102");
    }

    @Test
    @DisplayName("Debe calcular promedio ponderado correctamente")
    void testCalcularPromedioPonderado() {
        Calificacion cal1 = new Calificacion("ING101", "Curso 1", 3, 8.0, "2024-1"); // 24 puntos
        Calificacion cal2 = new Calificacion("ING102", "Curso 2", 4, 7.0, "2024-1"); // 28 puntos
        estudiante.agregarCursoAprobado(cal1);
        estudiante.agregarCursoAprobado(cal2);
        
        double promedio = estudiante.calcularPromedioPonderado();
        // (24 + 28) / (3 + 4) = 52 / 7 = 7.42857...
        assertThat(promedio).isCloseTo(52.0/7.0, within(0.0001));
    }

    @Test
    @DisplayName("Debe actualizar todos los datos del estudiante")
    void testActualizarTodosLosDatos() {
        estudiante.setTelefono("0987654321");
        estudiante.setDireccion("Av. Principal 123");
        estudiante.setFacultad("Ciencias");
        estudiante.setCarrera("Ing. Software");
        estudiante.setNivel("5to Semestre");
        
        assertThat(estudiante.getTelefono()).isEqualTo("0987654321");
        assertThat(estudiante.getDireccion()).isEqualTo("Av. Principal 123");
        assertThat(estudiante.getFacultad()).isEqualTo("Ciencias");
        assertThat(estudiante.getCarrera()).isEqualTo("Ing. Software");
        assertThat(estudiante.getNivel()).isEqualTo("5to Semestre");
    }
}
