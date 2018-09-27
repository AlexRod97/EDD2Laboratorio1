package com.e.edd2laboratorio1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.edd2laboratorio1.Classes.HuffmanEncoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnOpenFile,button, btnComprimir, btnDescomprimir;
    EditText editText;
    TextView tvComprimir, tvDescomprimir;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    String mainData;
    File decompressionFile;
    HuffmanEncoder huffman = new HuffmanEncoder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvComprimir = findViewById(R.id.tvComprimir);
        tvDescomprimir = findViewById(R.id.tvDescomprimir);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                String fileName = "Trial";
                SaveText(fileName, content);
            }
        });

        btnOpenFile = findViewById(R.id.btnOpenFile);

        btnOpenFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileSearch();
            }
        });

        btnComprimir = (Button) findViewById(R.id.btnComprimir);
        btnComprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huffman.compressedData = huffman.compress(mainData);
            }
        });

        btnDescomprimir = (Button) findViewById(R.id.btnDescomprimir);
        btnDescomprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huffman.SetFileName(decompressionFile.getName());
                String decompression = huffman.decompress(huffman.ReadFile(decompressionFile));
                tvDescomprimir.setText(decompression);
            }
        });

    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de lectura concedido", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permiso de lectura denegado", Toast.LENGTH_SHORT).show();
                finish();
            }

            switch (requestCode) {
                case 1000:
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permiso de escritura concedido", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Permiso de lectura denegado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
            }
        }
    }

    private String readText(String input) {
        File file = new File(Environment.getExternalStorageDirectory(),input);
        StringBuilder text  = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                text.append(line);
                //text.append("\n");
            }
            br.close();
            decompressionFile = file;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private void SaveText(String filename, String content) {
        String fileName = filename + ".txt";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al almacenar el archivo", Toast.LENGTH_SHORT).show();
        }
    }

    private void fileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":")+1);
                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                mainData = readText(path);
                tvComprimir.setText(mainData);
            }
        }
    }
}
