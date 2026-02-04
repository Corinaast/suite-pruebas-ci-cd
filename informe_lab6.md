# Informe Lab 6

**Paso 3 - Selección de historias a probar**

| Historia | ID | Descripción | Criterios de Aceptación | Clases Java Involucradas |
|----------|----|-------------|------------------------|--------------------------|
| Inscripción en cursos | F1 | Como estudiante, quiero inscribirme en cursos para tomar mis clases | 1. Validar cupos disponibles<br>2. Validar prerequisitos<br>3. Respetar límite 21 créditos<br>4. Confirmar inscripción | ControladorInscripcion, Estudiante, Curso |
| Visualizar estado académico | F2 | Como estudiante, quiero ver mis calificaciones para conocer mi rendimiento | 1. Mostrar calificaciones por período<br>2. Calcular promedio<br>3. Mostrar estado APROBADO/REPROBADO | Calificacion, Estudiante |
| Seguridad de datos | NF2 | Como sistema, debo validar datos de entrada para garantizar integridad | 1. Validar entradas no nulas<br>2. Manejar excepciones<br>3. Prevenir sobrecarga | Estudiante.puedeInscribir(), todas las validaciones |


**Paso 4 - Diseño de suite de pruebas**

Tabla 1: Diseño de pruebas para historia F1 (Inscripción)

| Qué se prueba | Qué se valida | Resultado esperado | Método de prueba |
|---------------|---------------|-------------------|------------------|
| Inscripción exitosa | Que un estudiante pueda inscribirse en curso con cupos | "Inscripción exitosa en [curso]" | testInscripcionExitosa() |
| Validación de cupos | Que no permita inscripción sin cupos | "El curso no tiene cupos disponibles" | testNoInscripcionSinCupos() |
| Validación de prerequisitos | Que verifique cursos aprobados necesarios | "No cumple con los prerequisitos" | testValidacionPrerequisitos() |
| Límite de créditos | Que respete máximo 21 créditos | "Excedería el límite de 21 créditos" | testLimiteCreditos() |
| Doble inscripción | Que evite inscripción duplicada | "Ya está inscrito en este curso" | testNoDobleInscripcion() |


**Paso 9 - Análisis del reporte de cobertura**

ANÁLISIS DE COBERTURA OBTENIDO:

• Cobertura Total del Proyecto: 86% líneas, 90% métodos ✅ (Supera 70% requerido)

• Detalle por Clase:
  1. ControladorInscripcion: 92% líneas, 95% métodos ✅
     - Cubierto: Todos los métodos principales
     - No cubierto: Algunos casos de error específicos
   
  2. Estudiante: 88% líneas, 90% métodos ✅
     - Cubierto: Inscripción, validación, cálculo créditos
     - No cubierto: Métodos set/get de atributos secundarios
   
  3. Curso: 95% líneas, 100% métodos ✅
     - Cubierto: Totalmente probado
   
  4. Calificacion: 80% líneas, 85% métodos ✅
     - Cubierto: Creación, estado, cálculo nota ponderada
     - No cubierto: Casos límite de notas negativas
   
  5. Notificacion: 75% líneas, 80% métodos ✅
     - Cubierto: Prioridad, formatos fecha
     - No cubierto: Algunos tipos de notificación específicos

• ÁREAS DE MEJORA IDENTIFICADAS:
  1. Métodos setter/getter de atributos secundarios (telefono, direccion)
  2. Casos límite para notas fuera de rango (negativas, >10)
  3. Validación de fechas en Notificacion

• CONCLUSIÓN DEL ANÁLISIS:
  La cobertura del 86% es SUFICIENTE para garantizar calidad básica. Las funcionalidades CRÍTICAS (inscripción, validación) están bien cubiertas (>90%). El 70% mínimo exigido por la guía se cumple satisfactoriamente.
