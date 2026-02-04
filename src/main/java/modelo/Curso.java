package modelo;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String codigo;
    private String nombre;
    private String facultad;
    private String profesor;
    private int creditos;
    private String horario;
    private int capacidad;
    private int inscritos;
    private List<String> prerequisitos;

    public Curso(String codigo, String nombre, String facultad, String profesor, 
                 int creditos, String horario, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.facultad = facultad;
        this.profesor = profesor;
        this.creditos = creditos;
        this.horario = horario;
        this.capacidad = capacidad;
        this.inscritos = 0;
        this.prerequisitos = new ArrayList<>();
    }

    public int getCuposDisponibles() {
        return capacidad - inscritos;
    }

    public String getEstadoCupos() {
        if (getCuposDisponibles() <= 0) {
            return "Sin cupos";
        } else if (getCuposDisponibles() <= 5) {
            return "Pocos cupos";
        } else {
            return "Disponible";
        }
    }

    public void incrementarInscritos() {
        if (inscritos < capacidad) {
            inscritos++;
        }
    }

    public void decrementarInscritos() {
        if (inscritos > 0) {
            inscritos--;
        }
    }

    public void agregarPrerequisito(String codigoCurso) {
        if (!prerequisitos.contains(codigoCurso)) {
            prerequisitos.add(codigoCurso);
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public String getProfesor() {
        return profesor;
    }

    public int getCreditos() {
        return creditos;
    }

    public String getHorario() {
        return horario;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getInscritos() {
        return inscritos;
    }

    public List<String> getPrerequisitos() {
        return prerequisitos;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
