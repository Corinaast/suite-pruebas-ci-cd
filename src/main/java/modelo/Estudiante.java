package modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String codigo;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String facultad;
    private String carrera;
    private String nivel;
    private List<Curso> cursosInscritos;
    private List<Calificacion> cursosAprobados;

    public Estudiante(String codigo, String nombre, String email) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.email = email;
        this.telefono = "";
        this.direccion = "";
        this.facultad = "Ingeniería";
        this.carrera = "Ingeniería de Sistemas";
        this.nivel = "3er Semestre";
        this.cursosInscritos = new ArrayList<>();
        this.cursosAprobados = new ArrayList<>();
    }

    public boolean cumplePrerequisitos(Curso curso) {
        for (String prerequisito : curso.getPrerequisitos()) {
            boolean tienePrerequisito = false;
            for (Calificacion cal : cursosAprobados) {
                if (cal.getCodigoCurso().equals(prerequisito)) {
                    tienePrerequisito = true;
                    break;
                }
            }
            if (!tienePrerequisito) {
                return false;
            }
        }
        return true;
    }

    public int getCreditosTotales() {
        int creditos = 0;
        for (Curso curso : cursosInscritos) {
            creditos += curso.getCreditos();
        }
        return creditos;
    }

    public boolean puedeInscribir(Curso curso) {
        if (curso.getCuposDisponibles() <= 0) {
            return false;
        }
        for (Curso c : cursosInscritos) {
            if (c.getCodigo().equals(curso.getCodigo())) {
                return false;
            }
        }
        if (getCreditosTotales() + curso.getCreditos() > 21) {
            return false;
        }
        if (!cumplePrerequisitos(curso)) {
            return false;
        }
        return true;
    }

    public void inscribirse(Curso curso) {
        if (puedeInscribir(curso)) {
            cursosInscritos.add(curso);
            curso.incrementarInscritos();
        }
    }

    public void desinscribirse(Curso curso) {
        if (cursosInscritos.remove(curso)) {
            curso.decrementarInscritos();
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    public List<Calificacion> getCursosAprobados() {
        return cursosAprobados;
    }

    public void agregarCursoAprobado(Calificacion calificacion) {
        cursosAprobados.add(calificacion);
    }

    public double calcularPromedioPonderado() {
        double sumaPonderada = 0.0;
        int sumaCreditos = 0;

        for (Calificacion cal : cursosAprobados) {
            sumaPonderada += cal.getNotaPonderada();
            sumaCreditos += cal.getCreditos();
        }

        if (sumaCreditos == 0) return 0.0;
        return sumaPonderada / sumaCreditos;
    }

}
