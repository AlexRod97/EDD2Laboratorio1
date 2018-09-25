package com.e.edd2laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e.edd2laboratorio1.Classes.LZW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    LZW compressionLZW = new LZW();
    Button btnOpenFile, btnOpenFile2, btnComprimir, btnDescomprimir;
    TextView tvComprimir, tvDescomprimir;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    String mainData,path;
    File decompressionFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnOpenFile = findViewById(R.id.btnOpenFile);
        btnOpenFile2 = findViewById(R.id.btnOpenFile2);
        btnComprimir = findViewById(R.id.btnComprimir);
        btnDescomprimir = findViewById(R.id.btnDescomprimir);
        tvComprimir = findViewById(R.id.tvComprimir);
        tvDescomprimir = findViewById(R.id.tvDescomprimir);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSearch();
            }
        });

        btnOpenFile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSearch();
            }
        });



        btnComprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compressionLZW.compress(mainData);
            }
        });

        btnDescomprimir = findViewById(R.id.btnDescomprimir);

        btnDescomprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String decompression = compressionLZW.Decompress(compressionLZW.ReadFile(decompressionFile));
                tvComprimir.setText(decompression);
            }
        });


    }

    @NonNull
    private String readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text  = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            decompressionFile = file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private void fileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    protected  void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                path = uri.getPath();
                path = path.substring(path.indexOf(":")+1);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                mainData = readText(path);
                tvComprimir.setText(mainData);
            }
        }
    }
}
