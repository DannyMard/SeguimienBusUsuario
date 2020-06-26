package com.example.ibabus;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class Posiciones {

    //Constante de Posición del marcador
    public static final LatLng SAGRADA_FAMILIA = new LatLng(0.348758, -78.121853);

    //Constante de Opciones de Polilínea.
    public static final PolylineOptions POLILINEA = new PolylineOptions()


            .add(new LatLng(0.34217,-78.1433))
            .add(new LatLng(0.34427,-78.1405))
            .add(new LatLng(0.345818,-78.138))
            .add(new LatLng(0.346133,-78.1365))
            .add(new LatLng(0.346992,-78.1323))
            .add(new LatLng(0.348698,-78.1253))
            .add(new LatLng(0.348718,-78.125))
            .add(new LatLng(0.34864,-78.1246))
            .add(new LatLng(0.347868,-78.124))
            .add(new LatLng(0.346315,-78.1234))
            .add(new LatLng(0.345188,-78.1239))
            .add(new LatLng(0.344305,-78.1229))
            .add(new LatLng(0.344288,-78.1228))
            .add(new LatLng(0.343802,-78.1187))
            .add(new LatLng(0.343795,-78.1186))
            .add(new LatLng(0.343368,-78.1186))
            .add(new LatLng(0.343157,-78.1179))
            .add(new LatLng(0.34317,-78.1179))
            .add(new LatLng(0.344773,-78.1175))
            .add(new LatLng(0.3489,-78.1168))
            .add(new LatLng(0.351238,-78.1164))
            .add(new LatLng(0.354138,-78.1159))
            .add(new LatLng(0.354553,-78.1177))
            .add(new LatLng(0.355148,-78.1175))
            .add(new LatLng(0.35513,-78.1172))
            .add(new LatLng(0.355248,-78.1171))
            .add(new LatLng(0.355208,-78.1168))
            .add(new LatLng(0.355282,-78.1167))
            .add(new LatLng(0.35778,-78.1156))
            .add(new LatLng(0.359623,-78.1159))
            .add(new LatLng(0.359792,-78.1156))
            .add(new LatLng(0.35974,-78.1145))
            .add(new LatLng(0.35991,-78.1121));




    public static final PolygonOptions POLIGONO = new PolygonOptions()
            .add(new LatLng(0.3585081,-78.1190078),
                    new LatLng(0.3585081,-78.11900782),
                    new LatLng(0.3585081,-78.1190078),
                    new LatLng(0.3585081,-78.1190078),
                    new LatLng(0.3585081,-78.1190078))
            .strokeColor(Color.RED)
            .fillColor(Color.BLUE);
}


