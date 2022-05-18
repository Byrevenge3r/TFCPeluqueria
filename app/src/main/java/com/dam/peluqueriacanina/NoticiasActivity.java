package com.dam.peluqueriacanina;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NoticiasActivity extends AppCompatActivity {
    WebView wVNoticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        wVNoticias = findViewById(R.id.webVNoticias);
        wVNoticias.setWebViewClient(new WebViewClient());
        wVNoticias.loadUrl("https://elpais.com/noticias/mascotas/");
    }
    //hola

    @Override
    public void onBackPressed() {
        if(wVNoticias.canGoBack()) wVNoticias.goBack();
        else super.onBackPressed();
    }
}