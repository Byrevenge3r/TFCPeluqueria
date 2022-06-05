package com.dam.peluqueriacanina.registro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dam.peluqueriacanina.R;
import com.dam.peluqueriacanina.utils.MiApplication;

public class Registro4 extends AppCompatActivity implements View.OnClickListener {

    //private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    Button btnSiguienteRegCuatro, btnMandarOtraVezRegCuatro;
    Intent i;
    String codigo;
    SmsManager sms;
    int numeroConfirmar;
    EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;

    //Listener de mensajes estoy bastante seguro de que solo escucha mensajes de numeros de telefonos distintos al mio aunque me parece raro
    /*SmsListener smsListener = new SmsListener() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);

        codigo = msg;

        inputCode1.setText(String.valueOf(codigo.charAt(0)));
        inputCode2.setText(String.valueOf(codigo.charAt(1)));
        inputCode3.setText(String.valueOf(codigo.charAt(2)));
        inputCode4.setText(String.valueOf(codigo.charAt(3)));
        inputCode5.setText(String.valueOf(codigo.charAt(4)));
        inputCode6.setText(String.valueOf(codigo.charAt(5)));


        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(smsListener, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro4);

        btnMandarOtraVezRegCuatro = findViewById(R.id.btnMandarOtraVezRegCuatro);
        btnSiguienteRegCuatro = findViewById(R.id.btnSiguienteRegCua);

        inputCode1 = findViewById(R.id.inputCode1);
        inputCode2 = findViewById(R.id.inputCode2);
        inputCode3 = findViewById(R.id.inputCode3);
        inputCode4 = findViewById(R.id.inputCode4);
        inputCode5 = findViewById(R.id.inputCode5);
        inputCode6 = findViewById(R.id.inputCode6);

        btnSiguienteRegCuatro.setOnClickListener(this);
        btnMandarOtraVezRegCuatro.setOnClickListener(this);


        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS))
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS}, 1001);
        } else {
            numeroConfirmar = (int) ((Math.random() * 9 + 1) * 100000);
            setearCodigo(numeroConfirmar);

        }


    }


    @Override
    public void onClick(View v) {
        if (v.equals(btnMandarOtraVezRegCuatro)) {
            numeroConfirmar = (int) ((Math.random() * 9 + 1) * 100000);
            setearCodigo(numeroConfirmar);
        } else {
            if (inputCode1.getText().toString().isEmpty()) {
                Toast.makeText(this, R.string.error_verificar_codigo, Toast.LENGTH_SHORT).show();
            } else {
                i = new Intent(this, Registro5.class);
                startActivity(i);

                overridePendingTransition(R.anim.animacion_derecha_izquierda, R.anim.animacion_izquierda_izquierda);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        i = new Intent(this, Registro3.class);
        startActivity(i);

        overridePendingTransition(R.anim.animacion_derecha_derecha, R.anim.animacion_izquierda_derecha);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("+34" + ((MiApplication) getApplicationContext()).getTelefono(), null, String.valueOf(numeroConfirmar), null, null);

        } else {
            Toast.makeText(getApplicationContext(), "Se debe dar permisos", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setearCodigo(int codigoNum) {
        sms = SmsManager.getDefault();
        sms.sendTextMessage("+34" + ((MiApplication) getApplicationContext()).getTelefono(), null, String.valueOf(numeroConfirmar), null, null);

        codigo = String.valueOf(codigoNum);

        inputCode1.setText(String.valueOf(codigo.charAt(0)));
        inputCode2.setText(String.valueOf(codigo.charAt(1)));
        inputCode3.setText(String.valueOf(codigo.charAt(2)));
        inputCode4.setText(String.valueOf(codigo.charAt(3)));
        inputCode5.setText(String.valueOf(codigo.charAt(4)));
        inputCode6.setText(String.valueOf(codigo.charAt(5)));

    }


}