package pl.byd.promand.Team3.infrastructure.data;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

public class GlobalState extends Application{
    public Handler mainHandler;
    public Handler menuHandler;

    public void onCreate(){
        super.onCreate();
        instance = this;
        Log.d("MyDebug","class GlobalState onCreate()");
    }

    static public GlobalState getInstance(){
        return instance;
    }
    static GlobalState instance;

}
