package com.example.ibabus;

class WeatherSample {
    private float latitud;
    private float longitud;

    public float getLongitud() {
        return longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "WeatherSample{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
