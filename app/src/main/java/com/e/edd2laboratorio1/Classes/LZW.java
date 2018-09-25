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

    static Map<String,Integer> singleDictionary = new LinkedHashMap<>();
    static Map<Integer,String> allCharsDictionary = new LinkedHashMap<>();
    List<Integer> readData = new ArrayList<>();
    String encode = "";

    public static void compress(String uncompress){

        int size = 256; //por caracteres ASCII
        String p = "";
        String c = "";
        int count  =1;
        Map<String,Integer> dictionary = new LinkedHashMap<>();
        List<Character> result = new ArrayList<>();
        StringBuilder singles = new StringBuilder();

        for (int i = 0; i < uncompress.length(); i++){

            if(!dictionary.containsKey(String.valueOf(uncompress.charAt(i)))) {
                dictionary.put(String.valueOf(uncompress.charAt(i)), count++);
                singles.append(uncompress.charAt(i));
                singles.append(",");
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
            String pc = p + c;

            if(!singleDictionary.containsKey(pc)) {
                if(!p.equals("")) {
                    singleDictionary.put(pc, count++);
                }
                word.append(c);
                p = c;
            }else {
                p = c;
                word.append(p);
            }
        }

        return word.toString();
    }

    public static Map<String,Integer> GenerateFile(List frq, StringBuilder single) {

        Map<String,Integer> table = new HashMap<String,Integer>();

        try {
            String line = "";
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "lzwFile" + ".txt");
            char var = ' ';

            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            bw.write(String.valueOf(single));
            bw.newLine();

            for (int i = 0; i < frq.size(); i++) {
                line = "";
                bw.write("");
                line =  String.valueOf(frq.get(i));
                bw.write(line + ",");
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
            int  j =1, cont = 0;
            while(inputFile.hasNext()) {
                String linea = inputFile.nextLine();
                String[] splited = linea.split(",");

                if(cont == 0) {
                    for (int i = 0; i < splited.length; i++) {
                        singleDictionary.put(splited[i],j++);
                        cont++;
                    }
                }
                else {
                    for (int i = 0; i < (splited.length); i++) {
                        int dato = Character.valueOf(splited[i].charAt(0));
                        readData.add(Integer.valueOf(dato));
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readData;
    }

}
