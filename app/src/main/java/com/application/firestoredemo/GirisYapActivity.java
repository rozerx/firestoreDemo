package com.application.firestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;


public class GirisYapActivity extends AppCompatActivity {
    //TODO: Kullanıcı telefon numarası aracılığıyla firebase'deki auth özelliği ile giriş yapabilmeli, şifresi 6 haneli ve sırf sayılardan oluşmalı
    public static final String TAG = "YOUR-TAG-NAME";
    private FirebaseAuth mAuth;
    EditText telefon, sifre;
    Button girisYap;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null){
            Intent bilgi = new Intent(GirisYapActivity.this, KullaniciBilgileriActivity.class);
            startActivity(bilgi);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        telefon=findViewById(R.id.editText_kullanicininTelefonu);
        sifre=findViewById(R.id.editText_kullanicininSifresi);
        girisYap = findViewById(R.id.button_kullaniciGirisYap);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        //   Giriş Yap butonu bloğu
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Şimdiik görünsün diye bu şekilde ama eğer kullanıcı girişi başarılıysa kullanıcıya bilgi ekleme sayfası açılmalı
                Intent kullaniciBilgisiEkle = new Intent(GirisYapActivity.this, KullaniciBilgileriActivity.class);
                startActivity(kullaniciBilgisiEkle);

                //   Metin kutusu kontrolü
                if(telefon.getText().toString().equals("")){
                    Toast.makeText(GirisYapActivity.this, "Lütfen geçerli telefon numarası girin", Toast.LENGTH_SHORT).show();
                }else if(sifre.getText().toString().equals("")){
                    Toast.makeText(GirisYapActivity.this, "Lütfen geçerli şifre girin", Toast.LENGTH_SHORT).show();
                }
                firebaseFirestore.collection("client")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot doc : task.getResult()){
                                        String a=doc.getString("Telefon");
                                        String b=doc.getString("Şifre");
                                        String a1=telefon.getText().toString().trim();
                                        String b1=sifre.getText().toString().trim();
                                        if(a.equalsIgnoreCase(a1) & b.equalsIgnoreCase(b1)) {
                                            Intent home = new Intent(GirisYapActivity.this, KullaniciBilgileriActivity.class);
                                            startActivity(home);
                                            Toast.makeText(GirisYapActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                            break;
                                        }else
                                            Toast.makeText(GirisYapActivity.this, "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}