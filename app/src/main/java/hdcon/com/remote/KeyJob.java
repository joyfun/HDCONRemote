package hdcon.com.remote;

import android.os.AsyncTask;
import android.util.Log;

public class KeyJob extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
        try{
            Log.e("key",params[0]);
            boolean press=true;
            if("0".equals(params[1])){
                press=false;
            }
            Post.sendKey(Integer.valueOf(params[0]),press);
            //Log.e("key","send complete");

        }catch(Exception e){
            e.printStackTrace();
        }
        // do above Server call here
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
      //  Log.v("app","send Successs");
        //process message
    }
}