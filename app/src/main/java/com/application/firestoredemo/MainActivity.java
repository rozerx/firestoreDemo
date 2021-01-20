package com.application.firestoredemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button yeniKayit, kayitliKullaniciGiris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yeniKayit = findViewById(R.id.button_yeniKullaniciKaydi);
        kayitliKullaniciGiris = findViewById(R.id.button_kullaniciGirisi);

        yeniKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayit = new Intent(MainActivity.this, KayitEtActivity.class);
                startActivity(kayit);
            }
        });

        kayitliKullaniciGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kayit = new Intent(MainActivity.this, GirisYapActivity.class);
                startActivity(kayit);
            }
        });
    }
}