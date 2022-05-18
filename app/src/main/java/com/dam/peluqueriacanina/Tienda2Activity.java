package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class Tienda2Activity extends AppCompatActivity implements View.OnClickListener{

    MaterialCardView cardTienda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda2);
        cardTienda = findViewById(R.id.card_tienda);
        cardTienda.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_tienda:
                tiendaDetalle();
                break;

        }
    }

    public void tiendaDetalle(){
        Intent tiendaDetalle = new Intent(getApplicationContext(),TiendaDetallesActivity.class);
        startActivity(tiendaDetalle);
    }
}