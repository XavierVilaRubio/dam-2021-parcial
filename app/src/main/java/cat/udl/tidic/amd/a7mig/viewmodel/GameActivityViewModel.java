package cat.udl.tidic.amd.a7mig.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.a7mig.GameActivity;
import cat.udl.tidic.amd.a7mig.models.Carta;
import cat.udl.tidic.amd.a7mig.models.Jugador;
import cat.udl.tidic.amd.a7mig.models.Partida;
import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;

public class GameActivityViewModel extends ViewModel {

    private static final String TAG = "GameActivityViewModel";
    public MutableLiveData<Integer> Imatge;
    public MutableLiveData<Boolean> Lose;
    public Carta currentCarta;
    public MutableLiveData<Integer> i;
    public MutableLiveData<Partida> partida;
    private GameActivity gameActivity;

    public GameActivityViewModel(GameActivity gameActivity) {
        this.Imatge = new MutableLiveData<>();
        this.Lose = new MutableLiveData<>(false);
        this.i = new MutableLiveData<>();
        this.partida = new MutableLiveData<>(new Partida());
        this.gameActivity = gameActivity;
    }

    public void saveLists(List <String> noms, List <Integer> aposta) {
        List <Jugador> jugadores = new ArrayList<>();
        for (int a = 0; a < noms.size(); a++) {
            jugadores.add(new Jugador(noms.get(a), aposta.get(a)));
        }
        this.partida.getValue().setJugadores(jugadores);
        i.setValue(0);
        treureCarta();
    }

    public void treureCarta(){
        currentCarta = partida.getValue().cogerCarta();
        partida.getValue().getJugadores().get(i.getValue()).setPuntuacion(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue()+currentCarta.getValue());
        Imatge.setValue(currentCarta.getResource());
    }

    public void onPlantarse(){
        Lose.setValue(false);
        if(i.getValue() == partida.getValue().getJugadores().size()-1){
            puntuacioFinal();
        }else {
            i.setValue(i.getValue()+1);
        }
        treureCarta();
    }

    public void onSeguir() {
        treureCarta();
        if(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue() > 7.5) {
            Lose.setValue(true);
        }
    }

    public void puntuacioFinal() {
        for(int a = 0; a < partida.getValue().getJugadores().size(); a++) {
            int banca = PreferenceProvider.providePreferences().getInt("banca", -1);
            if(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue() < 7.5) {
                PreferenceProvider.providePreferences().edit().putInt("banca", (int) (banca+(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue() * 0.9))).apply();
            } else if(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue() > 7.5) {
                PreferenceProvider.providePreferences().edit().putInt("banca", banca+partida.getValue().getJugadores().get(i.getValue()).getApuesta().getValue()).apply();
            } else if(partida.getValue().getJugadores().get(i.getValue()).getPuntuacion().getValue() == 7.5) {
                int val = partida.getValue().getJugadores().get(i.getValue()).getApuesta().getValue()*2;
                PreferenceProvider.providePreferences().edit().putInt("banca", banca-val).apply();
            }
        }
        gameActivity.finalPartida(partida.getValue().getJugadores());
    }

}
