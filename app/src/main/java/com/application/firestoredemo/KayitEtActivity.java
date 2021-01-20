package com.application.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class KayitEtActivity extends AppCompatActivity {
    //TODO: Bu sınıfta kullanıcı kayıt edilmeli, bilgileri "Kayıt Et" butonuna basıldığında belirttiğimiz hiyeraşide FIRESTORE'da oluşturduğunuz uygulamadaki veri tabanına yazılamlı.
    public static final String TAG = "YOUR-TAG-NAME";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,6}" +               //at least 4 characters
                    "$");
    EditText kayitAd, kayitSoyad, kayitTelefon, kayitSifre;
    Button kayitEt;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_et);

        kayitEt = findViewById(R.id.button_yeniKullaniciKayitEt);
        kayitTelefon = findViewById(R.id.kayit_telefon);
        kayitAd = findViewById(R.id.kayit_ad);
        kayitSoyad = findViewById(R.id.kayit_soyad);
        kayitSifre = findViewById(R.id.kayit_sifre);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("client").document();

        kayitEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sifre= kayitSifre.getText().toString();

                if(kayitAd.getText().toString().equals("")) {
                    Toast.makeText(KayitEtActivity.this, "Adınız", Toast.LENGTH_SHORT).show();

                }else if(kayitSoyad.getText().toString().equals("")) {
                    Toast.makeText(KayitEtActivity.this, "Soyadınız", Toast.LENGTH_SHORT).show();

                }else if(kayitTelefon.getText().toString().equals("")) {
                    Toast.makeText(KayitEtActivity.this, "Telefon numaranız", Toast.LENGTH_SHORT).show();

                }else if(validatePassword(sifre) != true){ // Şifre uzunluk ve boşluk kontrol fonksiyonu


                }else {
                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(KayitEtActivity.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            } else {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Ad", kayitAd.getText().toString());
                                reg_entry.put("Soyad", kayitSoyad.getText().toString());
                                reg_entry.put("Telefon", kayitTelefon.getText().toString());

                                firebaseFirestore.collection("client")
                                        .add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(KayitEtActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    public boolean validatePassword(String v) {
        String passwordInput = kayitSifre.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            kayitSifre.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            kayitSifre.setError("Password too weak");
            return false;
        } else {
            kayitSifre.setError(null);
            return true;
        }
    }

}