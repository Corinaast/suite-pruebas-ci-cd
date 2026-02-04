package controlador;

import modelo.Curso;
import modelo.Estudiante;
import java.util.ArrayList;
import java.util.List;

public class ControladorInscripcion {
    private List<Curso> cursosDisponibles;
    private Estudiante estudiante;

    public ControladorInscripcion(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.cursosDisponibles = new ArrayList<>();
        inicializarCursos();
    }

    private void inicializarCursos() {
        Curso curso1 = new Curso("ING101", "Programación I", "Ingeniería", 
                                  "Dr. Carlos López", 3, "L-M-V 08:00-10:00", 40);
        cursosDisponibles.add(curso1);

        Curso curso2 = new Curso("ING102", "Cálculo Diferencial", "Ingeniería", 
                                  "Dra. María García", 4, "L-M-V 10:00-12:00", 35);
        cursosDisponibles.add(curso2);

        Curso curso3 = new Curso("ING103", "Programación II", "Ingeniería", 
                                  "Ing. Juan Pérez", 3, "M-J 14:00-16:00", 30);
        curso3.agregarPrerequisito("ING101");
        cursosDisponibles.add(curso3);

        Curso curso4 = new Curso("ING104", "Bases de Datos", "Ingeniería", 
                                  "Dr. Roberto Silva", 4, "M-J 10:00-12:00", 25);
        curso4.agregarPrerequisito("ING101");
        cursosDisponibles.add(curso4);

        Curso curso5 = new Curso("ING105", "Estructura de Datos", "Ingeniería", 
                                  "Ing. Ana Martínez", 4, "L-M-V 14:00-16:00", 28);
        curso5.agregarPrerequisito("ING101");
        cursosDisponibles.add(curso5);

        Curso curso6 = new Curso("ING106", "Álgebra Lineal", "Ingeniería", 
                                  "Prof. Diego Ramírez", 4, "M-J 08:00-10:00", 32);
        cursosDisponibles.add(curso6);
    }

    public List<Curso> buscarCursos(String termino, String facultad) {
        List<Curso> resultados = new ArrayList<>();
        
        for (Curso curso : cursosDisponibles) {
            boolean cumpleFacultad = facultad.isEmpty() || curso.getFacultad().equals(facultad);
            boolean cumpleBusqueda = termino.isEmpty() || 
                                    curso.getCodigo().toLowerCase().contains(termino.toLowerCase()) ||
                                    curso.getNombre().toLowerCase().contains(termino.toLowerCase());
            
            if (cumpleFacultad && cumpleBusqueda) {
                resultados.add(curso);
            }
        }
        
        return resultados;
    }

    public String intentarInscribir(Curso curso) {
        if (curso.getCuposDisponibles() <= 0) {
            return "El curso no tiene cupos disponibles.";
        }
        
        for (Curso c : estudiante.getCursosInscritos()) {
            if (c.getCodigo().equals(curso.getCodigo())) {
                return "Ya está inscrito en este curso.";
            }
        }
        
        if (estudiante.getCreditosTotales() + curso.getCreditos() > 21) {
            return "Excedería el límite de 21 créditos.";
        }
        
        if (!estudiante.cumplePrerequisitos(curso)) {
            return "No cumple con los prerrequisitos necesarios.";
        }
        
        estudiante.inscribirse(curso);
        return "Inscripción exitosa en " + curso.getNombre();
    }

    public String confirmarInscripcion() {
        if (estudiante.getCursosInscritos().isEmpty()) {
            return "No hay cursos seleccionados para confirmar.";
        }
        
        System.out.println("\n========== CONFIRMACIÓN DE INSCRIPCIÓN ==========");
        System.out.println("Estudiante: " + estudiante.getNombre());
        System.out.println("Código: " + estudiante.getCodigo());
        System.out.println("Email: " + estudiante.getEmail());
        System.out.println("\nCursos inscritos:");
        
        int creditosTotal = 0;
        for (int i = 0; i < estudiante.getCursosInscritos().size(); i++) {
            Curso curso = estudiante.getCursosInscritos().get(i);
            System.out.println((i + 1) + ". " + curso.getCodigo() + " - " + curso.getNombre() 
                             + " (" + curso.getCreditos() + " créditos)");
            creditosTotal += curso.getCreditos();
        }
        
        System.out.println("\nTotal de créditos: " + creditosTotal);
        System.out.println("Se ha enviado un correo de confirmación a: " + estudiante.getEmail());
        System.out.println("===============================================\n");
        
        return "Inscripción confirmada exitosamente.";
    }

    public List<Curso> getCursosDisponibles() {
        return cursosDisponibles;
    }

    public List<Curso> getCursosInscritos() {
        return estudiante.getCursosInscritos();
    }
}
