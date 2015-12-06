package hdcon.com.remote;

import android.os.AsyncTask;
import android.util.Log;

public class SendfeedbackJob extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String[] params) {
        Post.connect();
        // do above Server call here
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
        Log.v("app","send Successs");
        //process message
    }
}