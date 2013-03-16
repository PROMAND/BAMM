package pl.byd.promand.Team3.infrastructure.data;

import android.app.Application;
import android.util.Log;

public class GlobalState extends Application{
    public void onCreate(){
        super.onCreate();
        Log.d("MyDebug","class GlobalState onCreate()");
    }
}
