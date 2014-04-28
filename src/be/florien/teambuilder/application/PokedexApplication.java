package be.florien.teambuilder.application;

import android.app.Application;
import android.content.Context;

public class PokedexApplication extends Application {
    
    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        PokedexApplication.mApplicationContext = getApplicationContext();
    }
    
    public static Context getContext(){
        return mApplicationContext;
    }

}
