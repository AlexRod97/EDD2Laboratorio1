package com.e.edd2laboratorio1.Classes;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LZW {

    static Map<String,Integer> singleDictionary = new LinkedHashMap<>();
    List<Integer> readData = new ArrayList<>();
    String encode = "";
    static String originalFilename, compressedFilename, originalFilePath;
    static ListadoCompresion listado = new ListadoCompresion();

    public static void compress(String uncompress){

        int size = 256; //por caracteres ASCII
        String p = "";
        String c = "";
        int count  =1;
        Map<String,Integer> dictionary = new LinkedHashMap<>();
        List<Character> result = new ArrayList<>();
        List<String> singles = new ArrayList<String>();
        for (int i = 0; i < uncompress.length(); i++){

            if(!dictionary.containsKey(String.valueOf(uncompress.charAt(i)))) {
                dictionary.put(String.valueOf(uncompress.charAt(i)), count++);
                singles.add(String.valueOf(uncompress.charAt(i)));
            }
        }

        for (int j = 0; j < uncompress.length(); j++){
            c = String.valueOf(uncompress.charAt(j));
            String pc = p + c;

            if(!dictionary.containsKey(String.valueOf(pc))) {
                if(!p.equals("")) {
                    dictionary.put(pc, count++);
                }
                int a = dictionary.get(p);
                result.add((char)a);
                p = c;
            }else {
                p = pc;
            }

            if(j == uncompress.length()-1) {
                int a = dictionary.get(p);
                result.add((char)a);
            }
        }
        GenerateFile(result,singles);
    }

    public static String Decompress(List<Integer> compress){

        String p = "";
        String c = "";
        int ip = 0;
        int ic = 0;
        int count = singleDictionary.size()+1;
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < compress.size(); i++){
            ic = compress.get(i);
            c = String.valueOf(singleDictionary.keySet().toArray()[ic-1]);
            String pc = p + String.valueOf(c.charAt(0));



            //si no esta en el diccionario
            if(!singleDictionary.containsKey(pc)) {

                //verifica si tamanio de current > 1
                if(c.length() > 1){

                    String temp = "";

                    for(int j = 0; j < c.length(); j++){

                        if (j != 0){

                            if(!singleDictionary.containsKey(pc)){
                                break;
                            }else {
                                temp += String.valueOf(c.charAt(j));
                                pc = p + temp;
                            }

                        }if (j == 0) {
                            pc = p + String.valueOf(c.charAt(j));
                        }

                    }


                }
                singleDictionary.put(pc,count++);
                word.append(p);
                p = c;

            }else {
                p = pc;
            }



        }
        word.append(p);
        return word.toString();
    }

    public static Map<String,Integer> GenerateFile(List frq, List<String> single) {

        Map<String,Integer> table = new HashMap<String,Integer>();

        try {
            String line = "";

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),compressedFilename + ".txt");
            char var = ' ';
            StringBuilder map = new StringBuilder();
            StringBuilder compression = new StringBuilder();

            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.flush();
            for(int i =0; i < single.size(); i++) {

                if(i == single.size()-1){
                    map.append(single.get(i) + "|");
                }
                else {
                    map.append(single.get(i));
                }
            }

            for (int i = 0; i < frq.size(); i++) {
                int element = Integer.valueOf((char)frq.get(i));
                compression.append(frq.get(i).toString());
            }

            bw.write(map.toString());
            bw.write(compression.toString());
            bw.close();
            listado.EscribirArchivo(originalFilename, file.getAbsolutePath(), compressedFilename, originalFilePath);
        }
        catch(IOException ex)
        {

        }
        return table;
    }

    public List<Integer> ReadFile(File file) {
        try {
            int  j =1, cont = 0;
            BufferedReader br = new BufferedReader((new FileReader(file)));
            String line, map, ascii;
            StringBuilder text = new StringBuilder();
            FileInputStream fileStream = new FileInputStream(file);
            byte[] values = new byte[(int)file.length()];
            fileStream.read(values);
            fileStream.close();
            String content = new String(values,"UTF-8");

            line = content;

            boolean flag = true;

            for(int i =0; i < line.length(); i++) {
                String Char = String.valueOf(line.charAt(i));

                if (Char.equals("|")) {
                    flag = false;
                }
                else {
                    if(flag) {
                        singleDictionary.put(String.valueOf(line.charAt(i)), j++);
                    }
                    else {
                        int dato = Character.valueOf(line.charAt(i));
                        String element = String.valueOf(line.charAt(i));
                        readData.add(Integer.valueOf(dato));
                    }
                }
            }

        }
       catch (IOException e) {
            e.printStackTrace();
        }

        return readData;
    }

    public void setFilenames(String name, String path) {
        originalFilename = name;
        compressedFilename = name + "_comp";
        originalFilePath = path;
    }

}
