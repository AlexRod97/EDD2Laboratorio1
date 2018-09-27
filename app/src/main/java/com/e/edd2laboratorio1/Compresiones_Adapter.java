package com.e.edd2laboratorio1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Compresiones_Adapter extends BaseAdapter {

    Activity context;
    private Stack<String> obtenerCompresiones;
    private List<String> obtenerStack;
    private static LayoutInflater inflater = null;

    public Compresiones_Adapter(Activity activity, Stack<String> comp){
        context = activity;
        obtenerCompresiones = comp;
        obtenerStack = new ArrayList<>();
        for (String c : obtenerCompresiones){
            obtenerStack.add(c);
        }

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return obtenerStack.size();

    }

    @Override
    public Object getItem(int position) {
        return obtenerStack.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null)? inflater.inflate(R.layout.listado_view,null): itemView;
        TextView Nombre = itemView.findViewById(R.id.tvNombreOr);
        TextView nombreComp = itemView.findViewById(R.id.tvNombreComp);
        TextView ruta = itemView.findViewById(R.id.tvRutaComp);
        TextView razon = itemView.findViewById(R.id.tvRazonComp);
        TextView factor = itemView.findViewById(R.id.tvRazonComp);
        TextView porcent = itemView.findViewById(R.id.tvPorcentaje);

        String data = obtenerStack.get(position);
        String[] splited = data.split("\\|");
        Nombre.setText("Nombre de arhivo original: " + splited[0]);
        nombreComp.setText("Nombre de archivo comprimido: " + splited[1]);
        ruta.setText("Ruta: "+ splited[2]);
        razon.setText("Razón de compresión: " + splited[3]);
        factor.setText("Factor de compresión: " + splited[4]);
        porcent.setText("Porcentaje de compresión: " + splited[5]);



        return itemView;
    }
}
