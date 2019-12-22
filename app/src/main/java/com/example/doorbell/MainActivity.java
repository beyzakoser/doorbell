package com.example.doorbell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText mesaj;
    Button gonder;
    TextView gor;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mesaj=(EditText)findViewById(R.id.message);
        gonder=(Button)findViewById(R.id.gonder);
        reff= FirebaseDatabase.getInstance().getReference("Message");
        gor=(TextView) findViewById(R.id.gosterilecek);



        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  key=reff.push().getKey();
                DatabaseReference reffYeni=FirebaseDatabase.getInstance().getReference("Message/"+key);
                reffYeni.setValue(mesaj.getText().toString());
                Toast.makeText(MainActivity.this,"basarili",Toast.LENGTH_LONG).show();
                mesaj.setText("");
                verileriOku();
            }
        });

    }
    public void verileriOku(){
        DatabaseReference oku=FirebaseDatabase.getInstance().getReference("Message");
        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gor.setText("");
                Iterable<DataSnapshot> keys= dataSnapshot.getChildren();
                for(DataSnapshot  key :keys){
                    gor.append(key.getValue().toString()+"\n");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
