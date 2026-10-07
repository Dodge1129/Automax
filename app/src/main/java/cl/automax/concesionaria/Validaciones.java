package cl.automax.concesionaria;

import android.util.Patterns;

/**
 * Funciones para validar lo que escribe el usuario antes de lanzar un intent.
 */
public class Validaciones {

    // Acepta 9 dígitos chilenos, con o sin +56 (ej: 912345678 o +56912345678)
    public static boolean telefonoValido(String texto) {
        String limpio = texto.replace(" ", "").replace("-", "");
        return limpio.matches("^(\\+?56)?[2-9]\\d{8}$");
    }

    public static boolean correoValido(String texto) {
        return Patterns.EMAIL_ADDRESS.matcher(texto).matches();
    }

    // Tiene que partir con https:// y ser una dirección válida
    public static boolean webValida(String texto) {
        return texto.startsWith("https://") && Patterns.WEB_URL.matcher(texto).matches();
    }

    public static boolean largoMinimo(String texto, int minimo) {
        return texto.trim().length() >= minimo;
    }
}
