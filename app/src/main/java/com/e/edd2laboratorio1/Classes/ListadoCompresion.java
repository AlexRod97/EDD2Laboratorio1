package com.e.edd2laboratorio1.Classes;

import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ListadoCompresion {

    static File file;
    static String path;
    static Stack<String> obtenerLista;

    /**
     * Crear un archivo vacio con el nombre "MisCompresiones.txt"
     * IOException si el archivo "MisCompresiones" ya existe
     * @return
     * @throws IOException
     */
    public static boolean CrearArchivo() throws IOException {

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                "MisCompresiones"+".txt");

        boolean exist = file.createNewFile();

        if (exist){
            throw new IOException("el archivo ya existe");
        }

        return true;
    }

    /**
     * Escribe datos solicitados al archivo, set: 1|2|3|4|5|6
     * 1. Nombre Original del Archivo
     * 2. Nombre Archivo Compresion
     * 3. Ruta archivo Comprimido
     * 4. Razon de compresion
     * 5. factor compresion
     * 6. porcentaje reduccion
     * IOException si el archivo "MisCompresiones"no existe
     * o esta abierto antes de ejecutar este proceso
     * @param nombreArchivoOriginal
     * @param rutaArchivoComp
     * @param nombreArchivoComp
     * @param rutaArchivoOriginal
     * @return
     * @throws IOException
     */
    public static boolean EscribirArchivo(String nombreArchivoOriginal, String rutaArchivoComp,
                                          String nombreArchivoComp, String rutaArchivoOriginal)
            throws IOException{

        double razonCompresion = 0;
        double factorCompresion = 0;
        long porcentajeReduccion = 0;
        String separador = "|";

        if(file.exists()){

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(nombreArchivoOriginal+separador);
            bw.write(nombreArchivoComp+separador);
            bw.write(rutaArchivoComp+separador);

            File getRatio =  new File(rutaArchivoComp);
            long comp = getRatio.length();

            File original = new File(rutaArchivoOriginal);
            long ori = original.length();

            razonCompresion = (comp/ori);

            factorCompresion = (ori/comp);

            String razon = "";
            String factor = "";

            razon = Double.toString(razonCompresion);
            razon = razon.substring(0,4);

            factor = Double.toString(factorCompresion);
            factor = factor.substring(0,4);

            bw.write(razon);
            bw.write(factor);

            porcentajeReduccion = (ori-comp)*100;

            String porcentaje = Double.toString(porcentajeReduccion);
            porcentaje = porcentaje.substring(0,1);

            bw.write(porcentaje);

            bw.flush();
            bw.close();

        }else {
            throw new IOException("El archivo no existe o esta siendo utilizado");
        }

        return true;

    }

    /**
     * devuelve un stack con el texto seteado segun instrucciones de laboratorio
     * IOException si el archivo "MisCompresiones" no existe o esta abierto
     * @return
     * @throws IOException
     */
    public static Stack<String> ObtenerDatos() throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(file));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            List<String> lista = br.lines().collect(Collectors.<String>toList());
            for (String data : lista){
                obtenerLista.push(data);
            }
        }else{
            //puede que el data != null no funcione debido a comparacion,
            // en dado caso, meter en un try-catch y
            //en el catch simplemente darle la instruccion de regresar la lista
            String data = br.readLine();
            while (data != null || !data.equals("")){
                obtenerLista.push(data);
                data = br.readLine();
            }
        }

        return obtenerLista;
    }

}
