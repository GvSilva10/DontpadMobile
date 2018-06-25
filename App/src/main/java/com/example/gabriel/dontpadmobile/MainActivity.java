package com.example.gabriel.dontpadmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference textoReference;
    private EditText palavrasTag;
    private EditText nomesTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        palavrasTag = findViewById(R.id.palavrasTag);
        nomesTag = findViewById(R.id.nomeTag);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.textoFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palavra = palavrasTag.getEditableText().toString();
                configuraDatabase();
                textoReference.setValue(palavra);
            }
        });

        FloatingActionButton atualizarfab = (FloatingActionButton) findViewById(R.id.atualizarFab);
        atualizarfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textoReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            String valor = dataSnapshot.getValue(String.class);
                            palavrasTag.setText(valor);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this,
                                getString(R.string.erro_firebase),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void configuraDatabase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        textoReference = firebaseDatabase.getReference(nomesTag.getEditableText().toString());
    }

}
