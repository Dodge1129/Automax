package cl.automax.concesionaria;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Datos de ejemplo de la sucursal (se pueden cambiar)
    private static final String SUCURSAL_NOMBRE = "AutoMax Concesionaria";
    private static final double SUCURSAL_LAT = -33.4489;
    private static final double SUCURSAL_LNG = -70.6693;

    private TextInputEditText etTelefono, etCorreo, etWeb;
    private TextView tvSaludo, tvResumenAuto;
    private Spinner spAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSaludo = findViewById(R.id.tvSaludo);
        tvResumenAuto = findViewById(R.id.tvResumenAuto);
        spAuto = findViewById(R.id.spAuto);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etWeb = findViewById(R.id.etWeb);

        configurarSpinner();

        // ----- Implícitos -----
        findViewById(R.id.btnMapa).setOnClickListener(v -> abrirMapa());
        findViewById(R.id.btnWeb).setOnClickListener(v -> abrirWeb());
        findViewById(R.id.btnLlamar).setOnClickListener(v -> abrirMarcador());
        findViewById(R.id.btnCorreo).setOnClickListener(v -> enviarCorreo());
        findViewById(R.id.btnCalendario).setOnClickListener(v -> agendarTestDrive());

        // ----- Explícitos -----
        findViewById(R.id.btnDetalle).setOnClickListener(v -> irADetalle());
        findViewById(R.id.btnConfig).setOnClickListener(v ->
                startActivity(new Intent(this, ConfigActivity.class)));
        findViewById(R.id.btnFormulario).setOnClickListener(v -> irAFormulario());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mostramos el nombre guardado en ConfigActivity
        SharedPreferences prefs = getSharedPreferences("config", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "");
        if (nombre.isEmpty()) {
            tvSaludo.setText("Bienvenido 🚗");
        } else {
            tvSaludo.setText("Bienvenido, " + nombre + " 🚗");
        }
    }

    private void configurarSpinner() {
        String[] nombres = new String[Auto.CATALOGO.length];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = Auto.CATALOGO[i].nombreCompleto();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, nombres);
        spAuto.setAdapter(adapter);

        spAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Auto a = Auto.CATALOGO[position];
                tvResumenAuto.setText("💲 " + a.precioFormateado() + "  ·  " + a.kmFormateado());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private Auto autoElegido() {
        return Auto.CATALOGO[spAuto.getSelectedItemPosition()];
    }

    // =====================  IMPLÍCITOS  =====================

    // 1) Abrir ubicación en Google Maps (geo:lat,lng?q=texto)
    private void abrirMapa() {
        Uri uri = Uri.parse("geo:" + SUCURSAL_LAT + "," + SUCURSAL_LNG + "?q=" + Uri.encode(SUCURSAL_NOMBRE));
        lanzar(new Intent(Intent.ACTION_VIEW, uri));
    }

    // 2) Ver una página web (ACTION_VIEW con https://)
    private void abrirWeb() {
        String url = textoDe(etWeb);
        if (!Validaciones.webValida(url)) {
            etWeb.setError("Escribe una dirección que parta con https://");
            return;
        }
        lanzar(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    // 3) Marcador telefónico (ACTION_DIAL con tel:) - no necesita permiso CALL_PHONE
    private void abrirMarcador() {
        String fono = textoDe(etTelefono);
        if (!Validaciones.telefonoValido(fono)) {
            etTelefono.setError("Teléfono inválido (ej: 212345678)");
            return;
        }
        lanzar(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + fono.replace(" ", ""))));
    }

    // 4) Enviar correo (ACTION_SENDTO con mailto:) con asunto y cuerpo prellenados
    private void enviarCorreo() {
        String correo = textoDe(etCorreo);
        if (!Validaciones.correoValido(correo)) {
            etCorreo.setError("Correo inválido");
            return;
        }
        Auto auto = autoElegido();
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + correo));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cotización: " + auto.nombreCompleto());
        intent.putExtra(Intent.EXTRA_TEXT, "Hola, me interesa el " + auto.nombreCompleto()
                + " (" + auto.precioFormateado() + "). ¿Está disponible? Quisiera una cotización.");
        lanzar(intent);
    }

    // 5) Agregar evento al calendario (ACTION_INSERT con Events.CONTENT_URI)
    private void agendarTestDrive() {
        Auto auto = autoElegido();

        // Test drive mañana a las 10:00, dura 1 hora
        Calendar inicio = Calendar.getInstance();
        inicio.add(Calendar.DAY_OF_YEAR, 1);
        inicio.set(Calendar.HOUR_OF_DAY, 10);
        inicio.set(Calendar.MINUTE, 0);
        Calendar fin = (Calendar) inicio.clone();
        fin.add(Calendar.HOUR_OF_DAY, 1);

        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "Test drive - " + auto.nombreCompleto());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, SUCURSAL_NOMBRE);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Prueba de manejo en " + SUCURSAL_NOMBRE);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, inicio.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fin.getTimeInMillis());
        lanzar(intent);
    }

    // =====================  EXPLÍCITOS  =====================

    // 1) MainActivity -> DetalleActivity (con datos extra)
    private void irADetalle() {
        Auto a = autoElegido();
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra(DetalleActivity.EXTRA_NOMBRE, a.nombreCompleto());
        intent.putExtra(DetalleActivity.EXTRA_PRECIO, a.precioFormateado());
        intent.putExtra(DetalleActivity.EXTRA_ANIO, a.anio);
        intent.putExtra(DetalleActivity.EXTRA_KM, a.kmFormateado());
        intent.putExtra(DetalleActivity.EXTRA_DESCRIPCION, a.descripcion);
        startActivity(intent);
    }

    // 3) MainActivity -> FormActivity (le pasamos el auto elegido)
    private void irAFormulario() {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra(FormActivity.EXTRA_AUTO, autoElegido().nombreCompleto());
        startActivity(intent);
    }

    // =====================  AUXILIARES  =====================

    private String textoDe(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }

    // Lanza el intent y avisa si el teléfono no tiene una app para hacerlo
    private void lanzar(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay una app disponible para esta acción", Toast.LENGTH_LONG).show();
        }
    }
}
