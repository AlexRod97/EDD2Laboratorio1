package com.e.edd2laboratorio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    Button btnHuffman, btnLZW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnHuffman = findViewById(R.id.btnHuffman);
        btnLZW = findViewById(R.id.btnLZW);

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
                Intent huffman = new Intent(Main3Activity.this, Main2Activity.class);
                startActivity(huffman);
            }
        });
    }
}
