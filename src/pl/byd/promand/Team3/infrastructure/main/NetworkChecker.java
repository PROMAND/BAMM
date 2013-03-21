package pl.byd.promand.Team3.infrastructure.main;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import pl.byd.promand.Team3.presentation.main.NoNetworkDialog;

public class NetworkChecker {
    private static NetworkChecker instance = new NetworkChecker();

    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void displayNoNetworkDialog(Context context){
        NoNetworkDialog noNetworkDialog = new NoNetworkDialog((Activity)context);
    }

    public static NetworkChecker getInstance() {
        return instance;
    }
}
