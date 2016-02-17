package hdcon.com.remote;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by joyfun on 11/15/15.
 */
public class Post {
    private static  Socket s;
    private static OutputStream out;
    private static DataInputStream input;
    private static DataOutputStream output;
    public static boolean auth;
    public static String url;
    public static int port;
    static Timer timer=null;
    public static void  connect(){
        Log.e("app", "send start ");
        try {
            getConnect();
            // outgoing stream redirect to socket

            output.writeInt(7);
            output.writeInt(Consts.Comm_Password_Verify_Request);
           // output.writeInt(9);
            output.writeBytes("888888\0");
            output.flush();

            Log.e("app", "send success ");

        }catch (SocketException e){
            Log.e("app", "reconnect " + e.toString());

            getConnect();
        }
        catch(Exception e){
            Log.e("app", "connect() returned: " + e.toString());
        }}
    public static void  config(String aurl,int aport) {
        url=aurl;
        port=aport;
      //  getConnect();
    }
    public static void sendKey(int key) throws IOException {
        getConnect();
        output.writeInt(4);
        output.writeInt(Consts.Comm_Key_Event);
        output.writeInt(key);
        output.writeInt(0);
        output.writeInt(7);
        output.flush();

    }
    public static void sendKey(int key,boolean press) throws IOException {
       // Log.e("app","111Press Key"+key);

        getConnect();
        try{


        output.writeInt(4);
        output.writeInt(Consts.Comm_Key_Event);
        output.writeInt(key);
        output.writeInt(0);
        if(press){
            output.writeInt(6);
        }else{
            output.writeInt(7);

        }
       //     Log.e("app","222Press Key"+key);

            output.flush();
        }catch(SocketException se){
            Log.e("app","connect timeout");
        }

    }
    public static void keepLive() throws IOException {
        output.writeInt(0);
        output.writeInt(Consts.Comm_Live);
    }
    public static void readContent() throws IOException {
        while (true){
            while(input.available()>0){
                int type=input.readInt();
                int length=input.readInt();
                if(length==0){
                    continue;
                }
                int rst=input.readInt();
                Log.e("return", "type"+type+"  length" +length+" result"+rst);
            }
        }
    }
    public static synchronized void   getConnect(){
        try {
            if(s==null||s.isClosed()||!s.isConnected()){
                s = new Socket(url, port);
                input = new DataInputStream(s.getInputStream());
                output=new DataOutputStream(s.getOutputStream());
                Post.auth("888888");
    if(null==timer){
        timer  =new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    Post.keepLive();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        },1000,10000);
    }

            }
            Log.e("app", "connect  complete ");

        } catch (Exception e) {
           Log.e("error", e.toString());
        }
    }
    public static boolean auth(String password) throws IOException {
        try {
        output.writeInt(password.length()+1);
        output.writeInt(Consts.Comm_Password_Verify_Request);
        output.write((password+"\0").getBytes());
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return true;
    }
    public static DataInputStream getInput(){
        getConnect();
        return input;
    }
}
