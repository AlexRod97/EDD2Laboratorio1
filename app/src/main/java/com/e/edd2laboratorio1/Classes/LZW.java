package com.e.edd2laboratorio1.Classes;

import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.util.*;

public class LZW {

    List<Integer> readData = new ArrayList<>();
    String encode = "";

    public static void compress(String uncompress){

        int size = 256; //por caracteres ASCII
        String p = "", c = "";
        int count  =1;
        Map<String,Integer> dictionary = new LinkedHashMap<>();
        List<String> result = new ArrayList<>();


        for (int i = 0; i < uncompress.length(); i++){

            if(!dictionary.containsKey(String.valueOf(uncompress.charAt(i)))) {
                dictionary.put(String.valueOf(uncompress.charAt(i)), count++);
            }
        }
        //TODO Encontrar por que no crea en la tabla la combinación de más de dos caracteres
        for (int j = 0; j < uncompress.length(); j++){
            c = String.valueOf(uncompress.charAt(j));
            String pc = p + c;

            if(!dictionary.containsKey(String.valueOf(pc))) {
                if(p != "") {
                    dictionary.put(pc, count++);
                }
                int a = dictionary.get(p);
                result.add(String.valueOf(a));
                //TODO Convertir a chars los valores
            }
            //Aqui CREO que es la razón por la que no logra hacer la tabla completa, tal vez falta una condición de cuando si está contenido el pc
            p = c;
        }

        GenerateFile(result);
        //Ya guarda y lee el archivo para comprobar la descompresión
    }

    public static String Decompress(List<Integer> compress){

        int size = 256;

        Map<Integer,String> dictionary = new HashMap<>();

        for(int i = 0; i < size; i++){
            dictionary.put(i,""+(char)i); //se inicializa el dictionario
        }

        String p = "" + (char)(int)compress.remove(0);
        StringBuffer result = new StringBuffer(); //se puede usar StringBuilder si asi se desea

        for (int k : compress){
            String data = null;
            if (dictionary.containsKey(k)){
                data = dictionary.get(k);
            }else if(k == size){
                data = p + p.charAt(0); //se toma solamente el primer dato de la siguiente palabra
            }else{
                //se llama al evento de mensaje en Android para no tirar exception
                Toast.makeText(null,"Archivo mal compreso",Toast.LENGTH_LONG);

            }

            result.append(data);
            dictionary.put(size++, p + data.charAt(0));
            p = data;
        }

        return result.toString();
    }

    public static Map<String,Integer> GenerateFile(List frq) {

        Map<String,Integer> table = new HashMap<String,Integer>();

        try {
            String line = "";
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "lzwFile" + ".txt");
            char var = ' ';

            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (int i = 0; i < frq.size(); i++) {
                   // var = (char)i;
                    bw.newLine();
                  //  table.put(String.valueOf(var), frq[i]);
                    line = "";
                    bw.write("");
                    line =  String.valueOf(frq.get(i));
                    bw.write(line);

            }
            bw.close();
        }
        catch(IOException ex)
        {

        }
        return table;
    }

    public List<Integer> ReadFile(File file) {
        try {
            Scanner inputFile = new Scanner(file);
            int i = 0, j =0, cont = 0;
            while(inputFile.hasNext()) {

                String linea = inputFile.nextLine();
                readData.add(Integer.valueOf(linea));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readData;
    }

}
