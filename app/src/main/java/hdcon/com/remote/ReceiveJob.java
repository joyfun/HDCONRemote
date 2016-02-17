package hdcon.com.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ReceiveJob extends AsyncTask<String, Void, String> {
    private boolean auth=false;
    Context context;

    public ReceiveJob(Context callerclass)
    {
        context = callerclass;
    }

    @Override
    protected String doInBackground(String[] params) {
        String msg="";
        DataInputStream input=Post.getInput();
        try {
            Post.auth("888888");
//            Timer timer=new Timer();
//            timer.schedule(new TimerTask(){
//
//                @Override
//                public void run() {
//                    try {
//                        Post.keepLive();
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            },1000,30000);
        while (true){

                while(input.available()>0){
                    int length=input.readInt();
                    int type=input.readInt();
                    msg=msg+type;
                   switch (type){
                       case Consts.Comm_Live_Answer_Begined:;
                       case Consts.Comm_Live_Answer_UnBegin:Log.e("lie","Keep Alive");break;
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
//            Thread.sleep(3000);

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // do above Server call here
        return "some message";
    }

    @Override
    protected void onPostExecute(String message) {
        if((""+Consts.Comm_Live_Answer_Begined).equals(message)){
            ((CtrlActivity) context).imageView.setImageResource(R.drawable.bg2); // changing TextView
        }

        Log.v("app","send Successs");
        //process message
    }
}