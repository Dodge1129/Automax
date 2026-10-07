package cl.automax.concesionaria;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class ConfigActivity extends AppCompatActivity {

    private TextInputEditText etNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        setTitle("Configuración");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // botón Atrás
        }

        etNombre = findViewById(R.id.etNombreConfig);
        SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
        etNombre.setText(prefs.getString("nombre", ""));

        findViewById(R.id.btnGuardarConfig).setOnClickListener(v -> guardar(prefs));
    }

    private void guardar(SharedPreferences prefs) {
        String nombre = etNombre.getText() == null ? "" : etNombre.getText().toString().trim();
        if (!Validaciones.largoMinimo(nombre, 3)) {
            etNombre.setError("El nombre debe tener al menos 3 letras");
            return;
        }
        prefs.edit().putString("nombre", nombre).apply();
        Toast.makeText(this, "Guardado ✅", Toast.LENGTH_SHORT).show();
        finish(); // volvemos a MainActivity
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
