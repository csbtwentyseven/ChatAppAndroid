package com.example.chatappandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mesajlarTablo);
        editText = findViewById(R.id.mesajAlan);
        button = findViewById(R.id.Button);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference();

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mesajGoster();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Mesaj Hata Ekle
            }
        });
    }

    protected String tarihAl(Boolean mod){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// Mod = True,Yıl/Ay Döndürür.Veritabanı Okurken Bu Baz Alınır.Veritabanı Yazarken Saniyeye Kadar Çeker.
        if(mod){
            dateFormat = new SimpleDateFormat("yyyy/MM");
        }
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    protected void mesajGoster(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference(tarihAl(true));

        System.out.println("MesajGoster");
        final List<String> mesajlarL = new ArrayList<String>();
        final ListView MesajTablo = findViewById(R.id.mesajlarTablo);

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot mesajlar: dataSnapshot.getChildren()){
                    mesajlarL.add(String.valueOf(mesajlar.getValue()));
                }
                System.out.println(mesajlarL);
                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, mesajlarL);
                MesajTablo.setAdapter(veriAdaptoru);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void mesajGonder(View view){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference(this.tarihAl(false));

        editText = findViewById(R.id.mesajAlan);
        Ref.setValue(editText.getText().toString());
    }
}
