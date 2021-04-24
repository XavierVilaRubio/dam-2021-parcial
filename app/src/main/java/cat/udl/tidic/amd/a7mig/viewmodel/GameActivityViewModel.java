package cat.udl.tidic.amd.a7mig.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.a7mig.GameActivity;
import cat.udl.tidic.amd.a7mig.R;
import cat.udl.tidic.amd.a7mig.models.Carta;
import cat.udl.tidic.amd.a7mig.models.Jugador;
import cat.udl.tidic.amd.a7mig.models.Partida;
import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;

public class GameActivityViewModel extends ViewModel {

    private static final String TAG = "GameActivityViewModel";
    public MutableLiveData<String> Nom;
    public MutableLiveData<String> Aposta;
    public MutableLiveData<String> Puntuacio;
    public MutableLiveData<Integer> Imatge;
    public MutableLiveData<Boolean> Final;
    private Carta currentCarta;
    private List<Jugador> jugadors = new ArrayList<>();
    private int i;
    private Partida partida;
    private GameActivity gameActivity;

    public GameActivityViewModel(GameActivity gameActivity) {
        Nom = new MutableLiveData<>();
        Aposta = new MutableLiveData<>();
        Puntuacio = new MutableLiveData<>();
        Imatge = new MutableLiveData<>();
        i = 0;
        partida = new Partida();
        this.gameActivity = gameActivity;
    }

    public void saveLists(List<String> noms, List<Integer> apostes){
        for(int j = 0; j < noms.size(); j++) {
            jugadors.add(new Jugador(noms.get(j), apostes.get(j)));
        }
        newCarta();
        update();
    }

    public void newCarta(){
        currentCarta = partida.cogerCarta();
        jugadors.get(i).setPuntuacion(jugadors.get(i).getPuntuacion()+currentCarta.getValue());
        update();
    }

    public void plantarse(){
        nextPlayer();
    }

    public void seguir() {
        newCarta();
        if(jugadors.get(i).getPuntuacion() > 7.5) {
            nextPlayer();
        }
    }

    private void nextPlayer() {
        if(i == jugadors.size()-1){
            puntuacioFinal();
        }else {
            i++;
        }
        newCarta();
    }

    public void update(){
        Nom.setValue(jugadors.get(i).getNombre());
        Aposta.setValue(jugadors.get(i).getApuestaStr());
        Puntuacio.setValue(jugadors.get(i).getPuntuacionStr());
        //Imatge.setValue(partida.cogerCarta().getResource());
    }

    public void puntuacioFinal() {
        for(int a = 0; a < jugadors.size(); a++) {
            if(jugadors.get(i).getPuntuacion() < 7.5) {
                jugadors.get(i).setApuesta((int) Math.round(jugadors.get(i).getPuntuacion() * 0.9));
            } else if(jugadors.get(i).getPuntuacion() > 7.5) {
                int banca = PreferenceProvider.providePreferences().getInt("banca", -1);
                PreferenceProvider.providePreferences().edit().putInt("banca", banca+jugadors.get(i).getApuesta()).apply();
            } else if(jugadors.get(i).getPuntuacion() == 7.5) {
                int val = jugadors.get(i).getApuesta()*2;
                jugadors.get(i).setApuesta((int) (jugadors.get(i).getPuntuacion() + (jugadors.get(i).getApuesta()*2)));
                int banca = PreferenceProvider.providePreferences().getInt("banca", -1);
                PreferenceProvider.providePreferences().edit().putInt("banca", banca-val).apply();
            }



        }

        gameActivity.finalPartida();
    }

}