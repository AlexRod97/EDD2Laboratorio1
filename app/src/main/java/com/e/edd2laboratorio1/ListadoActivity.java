package com.e.edd2laboratorio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);


        //Adapter = new playlistUI_Adapter(Main3Activity.this,this.data);
        //lvPlay.setAdapter(Adapter);

       try {
           data = ListadoCompresion.ObtenerDatos();
       }catch (IOException e){
           e.printStackTrace();
       }

    }

    private void Start(){
        lvCompresiones = findViewById(R.id.lvCompresiones);
        compBack = findViewById(R.id.btnRegresarCompres);

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
