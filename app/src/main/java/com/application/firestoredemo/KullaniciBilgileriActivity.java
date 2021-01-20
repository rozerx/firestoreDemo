package com.application.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class KullaniciBilgileriActivity extends AppCompatActivity {
    //TODO: Gerekli bilgiler ekranda yazıldıktan sonra "Bilgileri kayıt et" butonuna basıldığında, oluşturduğunuz FIRESTORE veri tabanına ilgili alanlara, belirtilen hiyeraşide yazılamlı.
    //TODO: Aşağıdaki 3 butondan hangisine basılırsa onun üzerindeki textView'e ilgili veri çekilip yazılmalı
    public static final String TAG = "YOUR-TAG-NAME";

    private static final String KEY_KITAP = "kitap";
    private static final String KEY_YAZAR = "yazar";
    private static final String KEY_RENK = "renk";

    Button bilgiKaydet, kitapBilgi, yazarBilgi, renkBilgi;

    private EditText enSkitap, kitabinYazari, enRenk;
    private TextView view_kitap, view_yazar, view_renk;

    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_bilgileri);
        // Buttonlar
        bilgiKaydet = findViewById(R.id.button_kullaniciBilgisiniKayitEt);
        kitapBilgi = findViewById(R.id.button_kitapBilgisi);
        yazarBilgi = findViewById(R.id.button_yazarBilgisi);
        renkBilgi = findViewById(R.id.button_renkBilgisi);
        // Text alanları
        enSkitap = findViewById(R.id.Text_kitap);
        kitabinYazari = findViewById(R.id.Text_yazar);
        enRenk = findViewById(R.id.Text_renk);
        view_kitap = findViewById(R.id.textView_kitapAdi);
        view_yazar = findViewById(R.id.textView_yazar);
        view_renk = findViewById(R.id.textView_renk);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("kullanici_bilgisi").document();


        // BİLGİLERİ KAYDET BUTONU
        bilgiKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kitap = enSkitap.getText().toString().trim();
                String yazar = kitabinYazari.getText().toString().trim();
                String renk = enRenk.getText().toString().trim();

                ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists())
                        {
                            Toast.makeText(KullaniciBilgileriActivity.this, "Sorry,this DOC exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Map<String, Object> kullanici_bilgisi = new HashMap<>();
                            kullanici_bilgisi.put("kitap", kitap);
                            kullanici_bilgisi.put("yazar", yazar);
                            kullanici_bilgisi.put("renk", renk);

                            firebaseFirestore.collection("kullanici_bilgisi")
                                    .add(kullanici_bilgisi)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(KullaniciBilgileriActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("Error", e.getMessage());
                                        }
                                    });
                        }
                    }
                });
            }
        });

            // KİTAP BUTONU
        kitapBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kitap = enSkitap.getText().toString();

                Map<String, Object> kullanici_bilgisi = new HashMap<>();
                kullanici_bilgisi.put(KEY_KITAP, kitap);

                ref.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String kitap = documentSnapshot.getString(KEY_KITAP);

                                    view_kitap.setText("Kitap: " + kitap);
                                } else {
                                    Toast.makeText(KullaniciBilgileriActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(KullaniciBilgileriActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });

        // YAZAR BUTONU

        yazarBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String yazar = kitabinYazari.getText().toString();

                Map<String, Object> kullanici_bilgisi = new HashMap<>();
                kullanici_bilgisi.put(KEY_YAZAR, yazar);

                ref.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String yazar = documentSnapshot.getString(KEY_YAZAR);

                                    view_yazar.setText("Yazar: " + yazar);
                                } else {
                                    Toast.makeText(KullaniciBilgileriActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(KullaniciBilgileriActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });

                // RENK BUTONU
        renkBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String renk = enRenk.getText().toString();

                Map<String, Object> kullanici_bilgisi = new HashMap<>();
                kullanici_bilgisi.put(KEY_RENK, renk);

                ref.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    String renk = documentSnapshot.getString(KEY_RENK);

                                    view_renk.setText("Renk: " + renk);
                                } else {
                                    Toast.makeText(KullaniciBilgileriActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(KullaniciBilgileriActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
            }
        });
    }
}