package com.e.edd2laboratorio1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Stack;
import java.util.zip.Inflater;

public class Compresiones_Adapter extends BaseAdapter {

    Activity context;
    private Stack<String> obtenerCompresiones;
    private static LayoutInflater inflater = null;

    public Compresiones_Adapter(Activity activity, Stack<String> comp){
        context = activity;
        obtenerCompresiones = comp;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return obtenerCompresiones.size();

    }

    @Override
    public Object getItem(int position) {
        return obtenerCompresiones.pop();
    }

    @Override
    public long getItemId(int position) {
        return 0;
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

        String[] data = obtenerCompresiones.pop().split("|");
        Nombre.setText(data[0]);
        nombreComp.setText(data[1]);
        ruta.setText(data[2]);
        razon.setText(data[3]);
        factor.setText(data[4]);
        porcent.setText(data[5]);


        return itemView;
    }
}
