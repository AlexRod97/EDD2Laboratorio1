package com.e.edd2laboratorio1.Classes;

import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class LZW {

    public static List<Integer> compress(String uncompress){

        int size = 256; //por caracteres ASCII
        Map<String,Integer> dictionary = new HashMap<>();
        for (int i = 1; i < size; i++){
            dictionary.put("" + (char)i, i);
        }

        String p = "";
        List<Integer> result = new ArrayList<>();

        for(char c: uncompress.toCharArray()){

            String pc = p + c;

            if(dictionary.containsKey(pc)){
                p = pc;
            }else{
                result.add(dictionary.get(p));
                //agregamos previous + current al diccionario
                dictionary.put(pc,size++);
                p = "" + c;
            }
        }

        //output

        if(!p.equals("")){
            result.add(dictionary.get(p));
        }

        GenerateFile(result);

        return result;

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

}
