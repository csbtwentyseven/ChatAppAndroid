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
                for(DataSnapshot kullanici:dataSnapshot.getChildren()){
                    if(girisKutu.getText().toString().matches(String.valueOf(kullanici.getKey()))){
                        System.out.println("Başarılı1");

                        if(sifreKutu.getText().toString().matches(String.valueOf(kullanici.getValue()))) {
                            System.out.println("Başarılı2");
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
