package cat.udl.tidic.amd.a7mig;
import android.app.Application;
import android.util.Log;

import cat.udl.tidic.amd.a7mig.preferences.PreferenceProvider;

public class App extends Application {
    String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceProvider.init(this);
        inicialitzarBanca();
    }

    public void inicialitzarBanca() {
        int banca = PreferenceProvider.providePreferences().getInt("banca", -1);
        if(banca == -1) {
            PreferenceProvider.providePreferences().edit().putInt("banca", 30000).apply();
            Log.d(TAG, "banca was -1, setted to 30000");
        }else{
            Log.d(TAG, "banca is " + banca);
        }
    }
}
