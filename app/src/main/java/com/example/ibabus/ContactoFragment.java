package com.example.ibabus;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ContactoFragment extends Fragment implements OnMapReadyCallback {

    private static int CAMERA_ZOOM = 14;
    GoogleMap mMap;// varible de mapa

    private DatabaseReference midatabaseReference;

    private ArrayList<Marker> anteriorMarker = new ArrayList<>();
    private ArrayList<Marker> actualMarker = new ArrayList<>();

    private ArrayList<Double> actualDistancia = new ArrayList<>();
    private ArrayList<Double> anteriorDistancia = new ArrayList<>();

    //prueba
    LatLng Parada1, Parada2;
    TextView mdistancia;
    TextView mVelocidad;
    TextView mTiempo;
    String velocidad;
    String dista;

    float speed=1;
    float distance=1;

    LatLng[] Paradas ={Parada1,Parada2};







    public ContactoFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contacto, container, false);


        return root;
        //creaccion de movimiento marcador


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapasfragment);
        mapFragment.getMapAsync(this);

        midatabaseReference = FirebaseDatabase.getInstance().getReference();

        mVelocidad = (TextView)getActivity().findViewById(R.id.Tv_velocidad);
        mTiempo = (TextView)getActivity().findViewById(R.id.tv_tiempo);

        midatabaseReference.child("Conductor").child("velocidad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    velocidad = dataSnapshot.child("valor").getValue().toString();
                    mVelocidad.setText("la velocidad es "+ velocidad+ " m/min");
                    speed = Float.parseFloat(velocidad);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lecturaDatos();

    }

    private List<WeatherSample> weatherSamples = new ArrayList<>();

    private void lecturaDatos() {
        InputStream is = getResources().openRawResource(R.raw.prueba2ruta);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            //paso sobre las cabeceras

            reader.readLine();

            while (((line = reader.readLine()) != null)) {
                Log.d("Myactividad", "Line" + line);
                // split creamos una matriz
                String[] tokens = line.split(";");

                //lectura de datos
                WeatherSample sample = new WeatherSample();
                sample.setLatitud((float) Double.parseDouble(tokens[0]));
                sample.setLongitud((float) Double.parseDouble(tokens[1]));
                weatherSamples.add(sample);

                Log.d("mi activida", "solo creado:  " + sample);
            }


        } catch (IOException e) {
            Log.wtf("My", "error leyendo datos" + line, e);
            e.printStackTrace();
        }


    }


    public void hilo ()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;




                midatabaseReference.child("Conductor").child("K3C20KMcesUFvfmygdp08O12fCF2").child("l").addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(Marker marker: actualMarker){

                            marker.remove();


                        }

                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            List<Object> map = (List<Object>) dataSnapshot.getValue();
                            double locationLat = 0;
                            double locationLng = 0;
                            if(map.get(0) != null){
                                locationLat = Double.parseDouble(map.get(0).toString());
                            }
                            if(map.get(1) != null){
                                locationLng = Double.parseDouble(map.get(1).toString());
                            }
                            LatLng posicionmove = new LatLng(locationLat, locationLng);
                            //prueba de datos en logcat
                            String daaa = String.valueOf(locationLat);
                            Log.e("longitud ",daaa);

                            //calculo de distncias y movimiento de paradas

                            for(int i=0; i<Paradas.length;i++){

                                //obteniendo distacia
                                Location loc2 = new Location("");
                                loc2.setLatitude(posicionmove.latitude);
                                loc2.setLongitude(posicionmove.longitude);

                                //paradas
                                Location loc1 = new Location("");
                                loc1.setLatitude(Parada1.latitude);
                                loc1.setLongitude(Parada1.longitude);

                                distance = loc1.distanceTo(loc2);

                            }


                            float tiempo_llegada = (distance)/speed;
                            mTiempo.setText("El bus llegara en "+ tiempo_llegada +" min");

                            dista = String.valueOf(distance);
                            Log.e("distacia ",dista);
                            mdistancia = (TextView)getActivity().findViewById(R.id.tw_distancia);
                            mdistancia.setText("parada esta a:  "+dista+" metros");

                            //int distace = Integer.parseInt(dista);
                            //speed_int = Integer.parseInt(velocidad);

                            //tiempo = speed_int/distace;

                            //mTiempo.setText("El tiempo "+tiempo);


                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(new LatLng(locationLat,locationLng));
                            markerOptions.title("hola ");
                            //guadamos marcadores en primer array
                            anteriorMarker.add(mMap.addMarker(markerOptions));
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionmove, CAMERA_ZOOM));



                        }

                        /// borramos marcador
                        actualMarker.clear();
                        //cambiamos de array
                        actualMarker.addAll(anteriorMarker);



                        //anteriorMarker.clear();
                        timer();
                        //calculo de tiempo

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });


        // Ahora que el Mapa estalisto:
        // Agregamos un MArker en laUniversidad Tecnica del norte, Ibarra y movemos la camara.
        setMarker(Posiciones.SAGRADA_FAMILIA, "Universidad Tecnica del Norte", "Distrito: Ibarra");


        //Dibujamos una Polylinea
        drawPolilyne(Posiciones.POLILINEA);
        //Dibujamos una Polygono
        drawPoligono(Posiciones.POLIGONO);


    }

    //timer
    private void timer(){

        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);

            }

            public void onFinish() {
                //Toast.makeText(getActivity(), "puntos actualizados", Toast.LENGTH_SHORT).show();
                onMapReady(mMap);

            }
        }.start();

    }



    public void setMarker(LatLng position, String titulo, String info) {
        // Agregamos marcadores para indicar sitios de intereses.
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo)  //Agrega un titulo al marcador
                .snippet(info)   //Agrega informacion detalle relacionada con el marcador
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))); //Color del marcador

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, CAMERA_ZOOM));
        //PARADAS
        Parada1 = new LatLng(0.0889989,-78.092222);

        // agrgamos icono
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_paradas))
                .anchor(0.0f,1.0f).position(Parada1).title("parada1"));
        Parada2 = new LatLng(0.934533,-78.078876);

        // agrgamos icono
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_paradas))
                .anchor(0.0f,1.0f).position(Parada1).title("parada1"));

    }






    public void drawPolilyne(PolylineOptions options){
        mMap.addPolyline(options);
    }

    public void drawPoligono(PolygonOptions opts){
        mMap.addPolygon(opts);
    }

}
