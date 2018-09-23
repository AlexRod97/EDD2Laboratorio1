package com.e.edd2laboratorio1.Classes;

import android.widget.Toast;

import java.util.*;

public class LZW {

    public static List<Integer> compress(String uncompress){

        int size = 256; //por caracterees ASCII
        Map<String,Integer> dictionary = new HashMap<>();
        for (int i = 0; i < size; i++){
            dictionary.put("" + (char)i, i);
        }

        String p = "";
        List<Integer> result = new ArrayList<>();

        for(char c: uncompress.toCharArray()){

            String pc = p + c;

            if(dictionary.containsKey(pc.trim())){
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

}
