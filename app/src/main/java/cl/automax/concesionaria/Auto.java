package cl.automax.concesionaria;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Representa un auto del catálogo. Los datos son de ejemplo.
 */
public class Auto {

    public final String marca;
    public final String modelo;
    public final int anio;
    public final long precio;
    public final int km;
    public final String descripcion;

    public Auto(String marca, String modelo, int anio, long precio, int km, String descripcion) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.km = km;
        this.descripcion = descripcion;
    }

    public String nombreCompleto() {
        return marca + " " + modelo + " " + anio;
    }

    public String precioFormateado() {
        return "$" + NumberFormat.getInstance(new Locale("es", "CL")).format(precio);
    }

    public String kmFormateado() {
        return NumberFormat.getInstance(new Locale("es", "CL")).format(km) + " km";
    }

    // Catálogo de ejemplo
    public static final Auto[] CATALOGO = {
            new Auto("Toyota", "Yaris", 2023, 12990000, 18000,
                    "Auto city car, bencinero, muy económico en consumo. Ideal para la ciudad."),
            new Auto("Suzuki", "Swift", 2022, 10490000, 25000,
                    "Compacto y ágil, transmisión manual, mantenciones al día."),
            new Auto("Hyundai", "Tucson", 2024, 24990000, 5000,
                    "SUV familiar con pantalla táctil, cámara de retroceso y control de crucero."),
            new Auto("Kia", "Sportage", 2023, 22500000, 12000,
                    "SUV automática, aire acondicionado bizona y 6 airbags."),
            new Auto("Chevrolet", "Sail", 2021, 7990000, 40000,
                    "Sedán económico, buen espacio de maleta, un solo dueño.")
    };
}
