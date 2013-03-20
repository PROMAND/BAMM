package pl.byd.promand.Team3.infrastructure.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.util.Pair;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ImgMgr {
    private ImgMgr() {
    }

    public static ImgMgr getInstance() {
        return instance;
    }

    private static ImgMgr instance = new ImgMgr();

    public void downloadImg(String url) {
        DownloadFile down = new DownloadFile();
        down.execute(url);
    }

    public Bitmap getBitmap(String path){
        Log.d("MyDebug","GetBitmap" + path);
        getBitmapFromArray(path);
        Bitmap tempBitmap;
        if((tempBitmap = getBitmapFromArray(path)) != null)
           return tempBitmap;

        if((tempBitmap = getBitmapFromCache(path)) != null )
            return tempBitmap;
        downloadImg(path);
        return null;
    }

    private Bitmap getBitmapFromCache(String path){
        File file = new File(Environment.getExternalStorageDirectory()+"/tmp/"+path);
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/tmp/"+path);
            Pair<String,Bitmap> pair = new Pair<String, Bitmap>(path,bm);
            bitmapArray.add(pair);
            return bm;
        }

        Log.d("MyDebug","File not in array");
        return null;
    }

    private Bitmap getBitmapFromArray(String path){

        for(int i =0;i < bitmapArray.size();i++){
            if(bitmapArray.get(i).first.compareTo(path) == 0){
                return bitmapArray.get(i).second;
            }
        }
        Log.d("MyDebug","File not in array");
        return null;
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                String tempS = Environment.getExternalStorageDirectory()+"/tmp/" + sUrl[0];
                String[] temp = tempS.split("/");
                String fileName;
                String dirName ="";
                for(int i = 1;i < temp.length - 1;i++){
                    dirName = dirName + "/" + temp[i];
                }

                fileName = temp[temp.length -1];

                File f = new File(dirName);
                f.mkdirs();


                dirName = dirName + "/";
                File file = new File(dirName + fileName) ;
                if (file.exists()){
                      Log.d("MyDebug","File exists !");
                }

                Log.d("MyDebug","File : "+ dirName);
                Log.d("MyDebug","Dir : "+dirName + fileName);
                URL url = new URL("http://promand.xaa.pl/server/img/" +sUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(dirName + fileName);

                byte data[] = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1)
                    output.write(data, 0, count);

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(String string){
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("type", "img_download");
            msg.setData(bundle);
            GlobalState.getInstance().mainHandler.sendMessage(msg);
            Log.d("MyDebug","Msg wyslana");
        }
    }

    private ArrayList<Pair<String,Bitmap>> bitmapArray = new ArrayList<Pair<String, Bitmap>>();
}
