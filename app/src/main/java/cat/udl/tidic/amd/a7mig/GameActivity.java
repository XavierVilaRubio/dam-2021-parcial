package cat.udl.tidic.amd.a7mig;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cat.udl.tidic.amd.a7mig.databinding.ActivityMainBinding;
import cat.udl.tidic.amd.a7mig.models.Jugador;
import cat.udl.tidic.amd.a7mig.models.Partida;
import cat.udl.tidic.amd.a7mig.viewmodel.GameActivityViewModel;


public class GameActivity extends AppCompatActivity {

    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
    public GameActivityViewModel gameActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameActivityViewModel = new GameActivityViewModel(this);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        activityMainBinding.setViewModel(gameActivityViewModel);
        initView();
    }

    public void initView(){
        promptForPlayer();
    }

    private void promptForPlayer() {
        GameBeginDialog dialog = GameBeginDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
    }

    public void finalPartida(List<Jugador> jugadors){
        GameEndDialog dialog = GameEndDialog.newInstance(this, jugadors);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
    }

}