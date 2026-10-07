package cl.automax.concesionaria;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "extra_nombre";
    public static final String EXTRA_PRECIO = "extra_precio";
    public static final String EXTRA_ANIO = "extra_anio";
    public static final String EXTRA_KM = "extra_km";
    public static final String EXTRA_DESCRIPCION = "extra_descripcion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        setTitle("Detalle del auto");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // botón Atrás
        }

        // Recibimos los extras que mandó MainActivity
        String nombre = getIntent().getStringExtra(EXTRA_NOMBRE);
        String precio = getIntent().getStringExtra(EXTRA_PRECIO);
        int anio = getIntent().getIntExtra(EXTRA_ANIO, 0);
        String km = getIntent().getStringExtra(EXTRA_KM);
        String descripcion = getIntent().getStringExtra(EXTRA_DESCRIPCION);

        ((TextView) findViewById(R.id.tvDetNombre)).setText("🚗 " + nombre);
        ((TextView) findViewById(R.id.tvDetPrecio)).setText("💲 " + precio);
        ((TextView) findViewById(R.id.tvDetAnio)).setText("📆 Año: " + anio);
        ((TextView) findViewById(R.id.tvDetKm)).setText("🛣️ Kilometraje: " + km);
        ((TextView) findViewById(R.id.tvDetDescripcion)).setText("📝 " + descripcion);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
