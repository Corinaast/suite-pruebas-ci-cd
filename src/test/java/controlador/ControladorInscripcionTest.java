package controlador;

import modelo.Curso;
import modelo.Estudiante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Suite de Pruebas: Controlador Inscripción")
public class ControladorInscripcionTest {

    private ControladorInscripcion controlador;
    private Estudiante estudiante;
    private Curso cursoDisponible;

    @BeforeEach
    void setUp() {
        estudiante = new Estudiante("STU001", "María López", "maria@university.edu");
        controlador = new ControladorInscripcion(estudiante);
        cursoDisponible = controlador.getCursosDisponibles().get(0);
    }

    @Test
    @DisplayName("Debe inicializar 6 cursos disponibles")
    void testCursosDisponiblesIniciales() {
        assertThat(controlador.getCursosDisponibles()).hasSize(6);
    }

    @Test
    @DisplayName("Debe permitir búsqueda de cursos por término")
    void testBuscarCursosPorTermino() {
        List<Curso> resultados = controlador.buscarCursos("Programación", "");
        assertThat(resultados).isNotEmpty();
        assertThat(resultados).anyMatch(c -> c.getNombre().contains("Programación"));
    }

    @Test
    @DisplayName("Debe permitir búsqueda de cursos por código")
    void testBuscarCursosPorCodigo() {
        List<Curso> resultados = controlador.buscarCursos("ING101", "");
        assertThat(resultados).isNotEmpty();
        assertThat(resultados).anyMatch(c -> c.getCodigo().equals("ING101"));
    }

    @Test
    @DisplayName("Debe permitir búsqueda de cursos por facultad")
    void testBuscarCursosPorFacultad() {
        List<Curso> resultados = controlador.buscarCursos("", "Ingeniería");
        assertThat(resultados).hasSize(6); // Todos los cursos son de Ingeniería
    }

    @Test
    @DisplayName("Debe retornar lista vacía para búsqueda sin resultados")
    void testBusquedaSinResultados() {
        List<Curso> resultados = controlador.buscarCursos("NoExiste", "Medicina");
        assertThat(resultados).isEmpty();
    }

    @Test
    @DisplayName("Debe permitir inscripción exitosa en un curso")
    void testInscripcionExitosa() {
        String resultado = controlador.intentarInscribir(cursoDisponible);
        assertThat(resultado).contains("Inscripción exitosa");
        assertThat(controlador.getCursosInscritos()).contains(cursoDisponible);
    }

    @Test
    @DisplayName("No debe permitir inscripción en curso sin cupos")
    void testNoInscripcionSinCupos() {
        Curso cursoLleno = controlador.getCursosDisponibles().stream()
                                       .filter(c -> c.getCodigo().equals("ING104"))
                                       .findFirst()
                                       .orElse(null);
        
        // Llenar el curso
        for (int i = 0; i < cursoLleno.getCapacidad(); i++) {
            cursoLleno.incrementarInscritos();
        }
        
        String resultado = controlador.intentarInscribir(cursoLleno);
        assertThat(resultado).contains("no tiene cupos disponibles");
    }

    @Test
    @DisplayName("No debe permitir doble inscripción")
    void testNoDobleInscripcion() {
        controlador.intentarInscribir(cursoDisponible);
        String resultado = controlador.intentarInscribir(cursoDisponible);
        assertThat(resultado).contains("Ya está inscrito");
    }

    @Test
    @DisplayName("Debe permitir múltiples inscripciones válidas dentro del límite")
    void testMultiplesInscripcionesValidas() {
        Curso curso1 = controlador.getCursosDisponibles().get(0);
        Curso curso2 = controlador.getCursosDisponibles().get(1);
        
        controlador.intentarInscribir(curso1);
        controlador.intentarInscribir(curso2);
        
        assertThat(controlador.getCursosInscritos()).hasSize(2);
    }

    @Test
    @DisplayName("Debe validar prerequisitos correctamente")
    void testValidacionPrerequisitos() {
        // Obtener el curso ING103 que tiene ING101 como prerequisito
        Curso cursoConPrerequisito = controlador.getCursosDisponibles().stream()
                                                 .filter(c -> c.getCodigo().equals("ING103"))
                                                 .findFirst()
                                                 .orElse(null);
        
        // Intentar inscribirse sin cumplir el prerequisito
        String resultado = controlador.intentarInscribir(cursoConPrerequisito);
        assertThat(resultado).contains("prerrequisitos");
    }

    @Test
    @DisplayName("Debe confirmar inscripción correctamente")
    void testConfirmarInscripcion() {
        controlador.intentarInscribir(cursoDisponible);
        String resultado = controlador.confirmarInscripcion();
        assertThat(resultado).contains("confirmada exitosamente");
    }

    @Test
    @DisplayName("No debe confirmar inscripción sin cursos seleccionados")
    void testConfirmarInscripcionSinCursos() {
        String resultado = controlador.confirmarInscripcion();
        assertThat(resultado).contains("No hay cursos seleccionados");
    }

    @Test
    @DisplayName("Debe obtener lista de cursos inscritos")
    void testObtenerCursosInscritos() {
        controlador.intentarInscribir(cursoDisponible);
        List<Curso> inscritos = controlador.getCursosInscritos();
        assertThat(inscritos).hasSize(1);
        assertThat(inscritos).contains(cursoDisponible);
    }

    @Test
    @DisplayName("Debe permitir múltiples inscripciones válidas")
    void testMultiplesInscripciones() {
        Curso curso1 = controlador.getCursosDisponibles().get(0);
        Curso curso2 = controlador.getCursosDisponibles().get(1);
        
        controlador.intentarInscribir(curso1);
        controlador.intentarInscribir(curso2);
        
        assertThat(controlador.getCursosInscritos()).hasSize(2);
    }

    @Test
    @DisplayName("Debe buscar cursos sin importar mayúsculas/minúsculas")
    void testBusquedaSinSensibilidad() {
        List<Curso> resultado1 = controlador.buscarCursos("programación", "");
        List<Curso> resultado2 = controlador.buscarCursos("PROGRAMACIÓN", "");
        
        assertThat(resultado1).hasSameSizeAs(resultado2);
    }

    @Test
    @DisplayName("Debe retornar mensaje de éxito con nombre del curso")
    void testMensajeInscripcionContieneCurso() {
        String resultado = controlador.intentarInscribir(cursoDisponible);
        assertThat(resultado).contains(cursoDisponible.getNombre());
    }

    @Test  
    @DisplayName("Debe validar límite de créditos (21 máximo)")
    void testLimiteCreditos() {
        Curso cursoGrande1 = new Curso("ING201", "Curso Grande 1", "Ingeniería", "Prof. A", 15, "L-M-V", 40);
        Curso cursoGrande2 = new Curso("ING202", "Curso Grande 2", "Ingeniería", "Prof. B", 10, "M-J", 40);
        
        controlador.intentarInscribir(cursoGrande1); // 15 créditos
        String resultado = controlador.intentarInscribir(cursoGrande2); // 15 + 10 = 25 > 21
        
        assertThat(resultado).contains("Excedería el límite");
    }
}
