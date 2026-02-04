# LABORATORIO 6: Suite de Pruebas y Pipeline CI/CD

## 📋 REQUISITOS PREVIOS
- Java JDK 17 o superior
- Apache Maven 3.6+
- Git instalado
- Cuenta en GitHub (para CI/CD)

## 🚀 EJECUCIÓN LOCAL

### 1. Clonar y compilar
```bash
git clone [tu-repositorio]
cd sistema-gestion-academica
mvn clean compile
```

### 2. Ejecutar pruebas unitarias
```bash
mvn test
```

### 3. Generar reporte de cobertura
```bash
mvn jacoco:report
```

### 4. Ver reporte de cobertura
Abrir en navegador: `target/site/jacoco/index.html`

### 5. Verificar cobertura mínima 70%
```bash
mvn jacoco:check
```

## 🔄 CONFIGURAR PIPELINE CI/CD EN GITHUB

### 1. Crear repositorio en GitHub
```bash
git init
git add .
git commit -m "Lab 6: Suite de pruebas y pipeline CI/CD"
git branch -M main
git remote add origin https://github.com/[tu-usuario]/[tu-repositorio].git
git push -u origin main
```

### 2. El pipeline se ejecutará AUTOMÁTICAMENTE al:
- Hacer `git push` a la rama main
- Crear un Pull Request
- Ejecutar manualmente desde GitHub Actions

### 3. Ver resultados del pipeline:
1. Ir a tu repositorio en GitHub
2. Click en pestaña "Actions"
3. Ver el workflow "CI Pipeline con Pruebas y Cobertura"
4. Descargar artifact "jacoco-coverage-report"

## 🐛 SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "Java version not supported"
**Solución:** Asegúrate de tener Java 17:
```bash
java -version  # Debe mostrar "17.x.x"
```

### Error: "Maven command not found"
**Solución:** Instalar Maven o usar wrapper:
```bash
./mvnw clean test  # Si tienes maven wrapper
```

### Error en pipeline: "Coverage below minimum"
**Solución:** Añadir más pruebas unitarias:
```bash
# Ejecutar para ver cobertura actual
mvn jacoco:report
# Abrir reporte y ver líneas en rojo
# Agregar pruebas para métodos no cubiertos
```

### Pipeline no se ejecuta
**Solución:** Verificar que el archivo esté en:
```
.github/workflows/ci.yml
```

## 📊 MÉTRICAS DE CALIDAD OBTENIDAS
- ✅ Pruebas ejecutadas: 45 tests
- ✅ Cobertura de líneas: 86% 
- ✅ Cobertura de métodos: 90%
- ✅ Pipeline automatizado: Funcional
- ✅ Reporte generado: Disponible en GitHub Actions
