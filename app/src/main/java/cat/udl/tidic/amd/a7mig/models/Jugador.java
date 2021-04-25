package cat.udl.tidic.amd.a7mig.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


public class Jugador {
    
    private final MutableLiveData<String> nombre;
    private final MutableLiveData<Integer> apuesta;
    private MutableLiveData<Double> puntuacion;

    public Jugador(String nombre, Integer apuesta) {
        this.nombre = new MutableLiveData<>(nombre);
        this.apuesta = new MutableLiveData<>(apuesta);
        this.puntuacion = new MutableLiveData<>(1.0);
    }

    public MutableLiveData<String> getNombre() {
        return nombre;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = new MutableLiveData<>(puntuacion);
    }

    public MutableLiveData<Double> getPuntuacion() {
        return puntuacion;
    }

    public MutableLiveData<Integer> getApuesta() {
        return apuesta;
    }

    @NonNull
    @Override
    public String toString(){
        if (this.puntuacion.getValue() == 7.5){
            return this.nombre.getValue() + " ha guanyat " + this.getApuesta().getValue()*2 + " euros amb una puntuació de " + this.getPuntuacion();
        } else if (this.puntuacion.getValue() < 7.5){
            return this.nombre.getValue() + " ha perdut " + this.getApuesta().getValue()*0.1 + " euros amb una puntuació de " + this.getPuntuacion();
        } else {
            return this.nombre.getValue() + " ha perdut " + this.getApuesta().getValue() + " euros amb una puntuació de " + this.getPuntuacion();
        }
    }

}
