package com.example.roberto.tp_final;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class CityAdapter extends BaseAdapter {

    private List<City> ciudades;

    public CityAdapter(List<City> ciudades) {

        this.ciudades = ciudades;
    }

    @Override
    public int getCount() {
        return ciudades.size();
    }

    @Override
    public Object getItem(int position) {
        return ciudades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ciudades.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        //optimizacion de reutilizacion de recursos
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_ciudad,parent,false);
        }else{
            view = convertView;
        }

        City ciudad = (City)getItem(position);

        TextView textViewLetra = (TextView) view.findViewById(R.id.textViewLetra);
        TextView textViewCiudad = (TextView) view.findViewById(R.id.textViewCiudad);


        //maximo 50 caracteres
        //textViewMensaje.setFilters(new InputFilter[] {new InputFilter.LengthFilter(50)});


        //textViewLetra.getBackground().setColorFilter(Color.parseColor(mensaje.getColor()), PorterDuff.Mode.SRC);

        textViewLetra.setText(ciudad.getName().substring(0,1).toUpperCase());
        textViewCiudad.setText(ciudad.getName());


        return view;
    }
}
