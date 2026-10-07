package cl.automax.concesionaria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class FormActivity extends AppCompatActivity {

    public static final String EXTRA_AUTO = "extra_auto";

    private TextInputEditText etNombre, etCorreo, etTelefono, etMensaje;
    private TextView tvResultado;
    private String auto;

    // Aquí recibimos la respuesta de ConfirmActivity (reemplaza a startActivityForResult)
    private final ActivityResultLauncher<Intent> confirmLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    tvResultado.setText("✅ Cotización enviada correctamente");
                    etNombre.setText("");
                    etCorreo.setText("");
                    etTelefono.setText("");
                    etMensaje.setText("");
                } else {
                    tvResultado.setText("❌ Solicitud cancelada");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        setTitle("Solicitar cotización");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNombre = findViewById(R.id.etFormNombre);
        etCorreo = findViewById(R.id.etFormCorreo);
        etTelefono = findViewById(R.id.etFormTelefono);
        etMensaje = findViewById(R.id.etFormMensaje);
        tvResultado = findViewById(R.id.tvResultadoForm);

        auto = getIntent().getStringExtra(EXTRA_AUTO);
        ((TextView) findViewById(R.id.tvAutoForm)).setText("🚗 Cotizando: " + auto);

        findViewById(R.id.btnEnviarForm).setOnClickListener(v -> revisarYEnviar());
    }

    private void revisarYEnviar() {
        String nombre = texto(etNombre);
        String correo = texto(etCorreo);
        String telefono = texto(etTelefono);
        String mensaje = texto(etMensaje);

        // Validaciones
        if (!Validaciones.largoMinimo(nombre, 3)) {
            etNombre.setError("Escribe tu nombre (mínimo 3 letras)");
            return;
        }
        if (!Validaciones.correoValido(correo)) {
            etCorreo.setError("Correo inválido");
            return;
        }
        if (!Validaciones.telefonoValido(telefono)) {
            etTelefono.setError("Teléfono inválido (ej: 912345678)");
            return;
        }
        if (!Validaciones.largoMinimo(mensaje, 10)) {
            etMensaje.setError("El mensaje debe tener al menos 10 caracteres");
            return;
        }

        // 3) FormActivity -> ConfirmActivity (con resultado)
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(ConfirmActivity.EXTRA_AUTO, auto);
        intent.putExtra(ConfirmActivity.EXTRA_NOMBRE, nombre);
        intent.putExtra(ConfirmActivity.EXTRA_CORREO, correo);
        intent.putExtra(ConfirmActivity.EXTRA_TELEFONO, telefono);
        intent.putExtra(ConfirmActivity.EXTRA_MENSAJE, mensaje);
        confirmLauncher.launch(intent);
    }

    private String texto(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
