package com.e.edd2laboratorio1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.e.edd2laboratorio1.Classes.ListadoCompresion;

import java.io.IOException;
import java.util.Stack;

public class ListadoActivity extends AppCompatActivity {

    private Compresiones_Adapter adapter;
    Stack<String> data;
    ListView lvCompresiones;
    Button compBack;
    static ListadoCompresion listado = new ListadoCompresion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

       try {
           data = ListadoCompresion.ObtenerDatos();
       }
       catch (IOException e){
           e.printStackTrace();
       }
        Start();
    }

    private void Start(){
        lvCompresiones = findViewById(R.id.lvCompresiones);
        compBack = findViewById(R.id.btnRegresarCompres);

        adapter = new Compresiones_Adapter(ListadoActivity.this,this.data);
        lvCompresiones.setAdapter(adapter);

        compBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack;
                goBack = new Intent(ListadoActivity.this, Main3Activity.class);
                startActivity(goBack);
            }
        });



    }
}
