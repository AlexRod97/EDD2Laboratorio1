package com.e.edd2laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.e.edd2laboratorio1.Classes.ListadoCompresion;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {

    Button btnHuffman, btnLZW, btnCrear,btnIrCompresiones;
    static ListadoCompresion listado = new ListadoCompresion();
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnHuffman = findViewById(R.id.btnHuffman);
        btnLZW = findViewById(R.id.btnLZW);
        btnCrear = findViewById(R.id.btnCrear);
        btnIrCompresiones = findViewById(R.id.btnIrCompresiones);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        try {
            listado.CrearArchivo();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        btnHuffman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent huffman = new Intent(Main3Activity.this, MainActivity.class);
                startActivity(huffman);
            }
        });

        btnLZW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LZW = new Intent(Main3Activity.this, Main2Activity.class);
                startActivity(LZW);
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Crear = new Intent(Main3Activity.this, Main4Activity.class);
                startActivity(Crear);
            }
        });

        btnIrCompresiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent compresiones = new Intent(Main3Activity.this,ListadoActivity.class);
                startActivity(compresiones);
            }
        });
    }

    protected  void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":")+1);
               // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
