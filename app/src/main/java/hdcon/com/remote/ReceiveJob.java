package hdcon.com.remote;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;

public class ReceiveJob extends AsyncTask<String, Void, String> {
    private boolean auth=false;
    @Override
    protected String doInBackground(String[] params) {
        DataInputStream input=Post.getInput();
        try {
        while (true){

                while(input.available()>0){
                    int length=input.readInt();
                    int type=input.readInt();
                   switch (type){
                       case Consts.Comm_Live_Answer:Log.e("live","Keep Alive");break;
                       case Consts.Comm_Password_Verify_Result:if(input.readInt()!=0){
                           auth=true;
                           Log.e("auth","Success");
                       };break;

                       };


                    Log.e("return", "type" + type + "  length" + length );
                }
            if(auth){
         //       Post.keepLive();
            }
            Thread.sleep(3000);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // do above Server call here
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
        Log.v("app","send Successs");
        //process message
    }
}