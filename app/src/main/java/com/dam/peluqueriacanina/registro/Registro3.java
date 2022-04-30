package com.dam.peluqueriacanina.registro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.model.User;
import com.dam.peluqueriacanina.utils.MiApplication;
import com.google.android.material.snackbar.Snackbar;

public class Registro3 extends AppCompatActivity implements View.OnClickListener {

    Button btnAtrasRegTres, btnSiguienteRegTres;
    EditText etTelefonoReg;
    Intent i;
    SmsManager sms;
    int numeroConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        btnAtrasRegTres = findViewById(R.id.btnAtrasRegTres);
        btnSiguienteRegTres = findViewById(R.id.btnSiguienteRegTres);
        etTelefonoReg = findViewById(R.id.etTelefonoRegTres);

        btnAtrasRegTres.setOnClickListener(this);
        btnSiguienteRegTres.setOnClickListener(this);
        numeroConfirmar = (int) ((Math.random() * 9 + 1) * 100000);
    }

    @Override
    public void onClick(View v) {
        String telefono = etTelefonoReg.getText().toString().trim();
        if (v.equals(btnSiguienteRegTres)) {
            if (telefono.isEmpty()) {
                Snackbar.make(v, R.string.tst_fill, Snackbar.LENGTH_LONG).show();

            } else if (v.equals(btnSiguienteRegTres)) {

                ((MiApplication) getApplicationContext()).setTelefono(telefono);

                if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) +
                        ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS))
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS}, 1001);
                } else {
                    sms = SmsManager.getDefault();
                    sms.sendTextMessage("+34"+((MiApplication)getApplicationContext()).getTelefono(), null, String.valueOf(numeroConfirmar), null, null);


                    i = new Intent(this, Registro4.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
                }


            } else if (v.equals(btnAtrasRegTres)) {
                i = new Intent(this, Registro2.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro2.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+34"+((MiApplication)getApplicationContext()).getTelefono(), null, String.valueOf(numeroConfirmar), null, null);

            i = new Intent(this, Registro4.class);
            startActivity(i);
            overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);

        } else {
            Toast.makeText(getApplicationContext(), "Se debe dar permisos", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}