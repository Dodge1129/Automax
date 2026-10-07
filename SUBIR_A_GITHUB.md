# 🚀 Cómo subir el proyecto a GitHub

La pauta pide: rama `main`, rama de trabajo `feature/intents` y commits chicos con mensajes claros.

1. Crear un repositorio vacío en GitHub (público, o privado dándole acceso al profesor).
2. Abrir una terminal dentro de la carpeta del proyecto y ejecutar:

```bash
git init
git branch -M main
git add .gitignore build.gradle settings.gradle gradle.properties gradle app/build.gradle
git commit -m "Configuracion inicial del proyecto Android"
git remote add origin https://github.com/TU_USUARIO/ConcesionariaAutoMax.git
git push -u origin main

git checkout -b feature/intents

git add app/src/main/java/cl/automax/concesionaria/Validaciones.java app/src/main/java/cl/automax/concesionaria/Auto.java
git commit -m "Agrega clases Validaciones y Auto"

git add app/src/main/res app/src/main/AndroidManifest.xml
git commit -m "Agrega layouts, tema y manifest"

git add app/src/main/java/cl/automax/concesionaria/MainActivity.java
git commit -m "Agrega 5 intents implicitos en MainActivity"

git add app/src/main/java/cl/automax/concesionaria/DetalleActivity.java app/src/main/java/cl/automax/concesionaria/ConfigActivity.java
git commit -m "Agrega intents explicitos a Detalle y Config"

git add app/src/main/java/cl/automax/concesionaria/FormActivity.java app/src/main/java/cl/automax/concesionaria/ConfirmActivity.java
git commit -m "Agrega Form y Confirm con resultado (registerForActivityResult)"

git add README.md PRESENTACION.md capturas
git commit -m "Agrega README y capturas"

git push -u origin feature/intents
```

3. En GitHub, crear un **Pull Request** de `feature/intents` hacia `main` y hacer *merge*.
4. Entregar el **enlace del repositorio** (el README.md ya está dentro del repo).

📸 Antes del último commit, guardar las capturas en la carpeta `capturas/` con los mismos nombres que usa el README.
