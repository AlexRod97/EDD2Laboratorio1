package com.e.edd2laboratorio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    Button btnHuffman, btnLZW, btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnHuffman = findViewById(R.id.btnHuffman);
        btnLZW = findViewById(R.id.btnLZW);
        btnCrear = findViewById(R.id.btnCrear);

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

    }
}
