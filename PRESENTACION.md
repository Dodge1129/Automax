# 🎤 Guion de la presentación (5 minutos)

Una diapositiva por punto, en este orden (son las que pide la pauta).

## 1️⃣ Portada (10 seg)
- Nombre de la app: **AutoMax Concesionaria - Prototipo 2**
- Nombres del grupo, ramo y profesor.

## 2️⃣ Objetivo (30 seg)
- Qué agregamos: 5 intents implícitos + 3 explícitos + validaciones.
- Por qué: para que la app de la concesionaria se conecte con otras apps del teléfono (mapas, correo, calendario, teléfono) y se pueda navegar entre pantallas sin errores.

## 3️⃣ Mapa de navegación (40 seg)
```
                 ┌──────────────► DetalleActivity   (putExtra con los datos del auto)
MainActivity ────┼──────────────► ConfigActivity    (botón Atrás)
                 └──────────────► FormActivity ───► ConfirmActivity
                                       ▲                 │
                                       └─── resultado ◄──┘
                                    (RESULT_OK / RESULT_CANCELED)
```
- Dibujar esto como diagrama simple con cajas y flechas.

## 4️⃣ Implícitos (1 min)
- Lista: Maps, Web, Marcador, Correo, Calendario.
- Mostrar **1 o 2 flujos con captura**: por ejemplo la cotización por correo (asunto y mensaje con el auto elegido) y el test drive en el calendario.
- Decir que `ACTION_DIAL` no pide permiso.

## 5️⃣ Explícitos (1 min)
- Lista: Main→Detalle, Main→Config, Form→Confirm.
- Diagrama simple (el mismo del mapa, destacando las flechas).

## 6️⃣ Código clave (1 min) - 2 o 3 fragmentos
1. **Intent implícito + try/catch** (`MainActivity.enviarCorreo()` y `lanzar()`).
2. **Intent explícito con extras** (`MainActivity.irADetalle()` y cómo los recibe `DetalleActivity`).
3. **Manejo de resultado** (`FormActivity`: `registerForActivityResult()` y `ConfirmActivity`: `setResult()`).

## 7️⃣ Lecciones y mejoras (40 seg)
- Retos: que no se cerrara la app si no había una app disponible; el reemplazo de `startActivityForResult`.
- Validaciones: teléfono, correo, https, largos mínimos.
- UX: errores en rojo en el mismo campo, botón Atrás en la barra.
- Mejoras: fotos reales de los autos (galería/cámara), compartir un auto por SMS y animaciones entre pantallas.

> 🎥 Se puede llevar una grabación del proyecto funcionando por si falla el celular o el emulador.
