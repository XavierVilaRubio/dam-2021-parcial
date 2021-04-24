package cat.udl.tidic.amd.a7mig.models;

import androidx.annotation.NonNull;


public class Jugador {
    
    private final String nombre;
    private int apuesta;
    private double puntuacion;

    public Jugador(String nombre, Integer apuesta) {
        this.nombre = nombre;
        this.apuesta = apuesta;
        this.puntuacion = 0.0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }

    public int getApuesta() {
        return apuesta;
    }

    @NonNull
    @Override
    public String toString(){
        if (this.puntuacion == 7.5){
            return this.nombre + " ha guanyat " + this.getApuesta()*2 + " euros amb una puntuació de " + this.getPuntuacion();
        } else if (this.puntuacion < 7.5){
            return this.nombre + " ha perdut " + this.getApuesta()*0.1 + " euros amb una puntuació de " + this.getPuntuacion();
        } else {
            return this.nombre + " ha perdut " + this.getApuesta() + " euros amb una puntuació de " + this.getPuntuacion();
        }
    }

}
