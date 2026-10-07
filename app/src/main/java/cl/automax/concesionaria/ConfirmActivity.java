package cl.automax.concesionaria;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmActivity extends AppCompatActivity {

    public static final String EXTRA_AUTO = "extra_auto";
    public static final String EXTRA_NOMBRE = "extra_nombre";
    public static final String EXTRA_CORREO = "extra_correo";
    public static final String EXTRA_TELEFONO = "extra_telefono";
    public static final String EXTRA_MENSAJE = "extra_mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        setTitle("Confirmar solicitud");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ((TextView) findViewById(R.id.tvConfAuto))
                .setText(" Auto: " + getIntent().getStringExtra(EXTRA_AUTO));
        ((TextView) findViewById(R.id.tvConfNombre))
                .setText(" Nombre: " + getIntent().getStringExtra(EXTRA_NOMBRE));
        ((TextView) findViewById(R.id.tvConfCorreo))
                .setText("  Correo: " + getIntent().getStringExtra(EXTRA_CORREO));
        ((TextView) findViewById(R.id.tvConfTelefono))
                .setText(" Teléfono: " + getIntent().getStringExtra(EXTRA_TELEFONO));
        ((TextView) findViewById(R.id.tvConfMensaje))
                .setText(" Mensaje: " + getIntent().getStringExtra(EXTRA_MENSAJE));

        // Devolvemos el resultado a FormActivity
        findViewById(R.id.btnConfirmar).setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
        findViewById(R.id.btnCancelar).setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_CANCELED);
        finish();
        return true;
    }
}
