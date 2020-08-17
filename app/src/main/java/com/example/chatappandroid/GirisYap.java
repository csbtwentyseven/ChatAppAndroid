package com.example.chatappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class GirisYap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        EditText girisKutu = findViewById(R.id.GirisYapKutu);
        EditText sifreKutu = findViewById(R.id.SifreKutu);
        Button girisButon = findViewById(R.id.button_giris);


    }

    public void girisYap(View view){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference Ref = database.getReference("kullanicilar");

        final EditText girisKutu = findViewById(R.id.GirisYapKutu);
        final EditText sifreKutu = findViewById(R.id.SifreKutu);


        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot kullanici:dataSnapshot.getChildren()){ //Kullanicilar listesinde dongu baslatiyor.
                    if(girisKutu.getText().toString().matches(String.valueOf(kullanici.getKey()))){ // eger input ile herhangi bi kullanici ismi eslesirse
                        System.out.println("Başarılı1");

                        for(DataSnapshot sifre:kullanici.getChildren()) { // sifre verisinin altindaki verileri cekiyor.

                            if (String.valueOf(sifre.getKey()).matches("sifre") && sifreKutu.getText().toString().matches(String.valueOf(sifre.getValue()))) { //sifre kontrol

                                System.out.println("Başarılı2");
                                //to - do main activty' bağla
                            }

                            else{
                                System.out.println("Yanlış Şifre");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
