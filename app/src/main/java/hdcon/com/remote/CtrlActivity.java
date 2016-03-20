package hdcon.com.remote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CtrlActivity extends AppCompatActivity {
    ImageView imageView;
    RelativeLayout mainLayout;
    RelativeLayout confLayout;
    long layoutid=0l;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //String string = bundle.getString(DownloadService.FILEPATH);
                int resultCode = bundle.getInt("status");
                boolean auth = bundle.getBoolean("auth");
                if (auth) {

                Log.e("return status", resultCode + "");
                if (resultCode == 106) {
                    if (layoutid != R.id.confmain) {
                        if (confLayout == null) {
                            setContentView(R.layout.conf_main);//通过这个函数设定显示layout
                            confLayout = (RelativeLayout) findViewById(R.id.confmain);
                            bindAction(confLayout);
                        } else {
                            setContentView(confLayout);//通过这个函数设定显示layout

                        }

                        layoutid = R.id.confmain;
                    }

                } else if(resultCode == 107) {
                    if (layoutid != R.id.contentmain) {
                        setContentView(mainLayout);//通过这个函数设定显示layout
                        // Log.e("app", contentLayout.getId() + "");
                        // contentLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.content_main, null);
                        layoutid = R.id.contentmain;
//                        bindAction();
                    }
                }
            }else{
                    Intent in = new Intent();
                    in.setClassName(getApplicationContext(), "hdcon.com.remote.SetActivity");
                    startActivity(in);
                    finish();
                }
            }
        }
    };

    private void bindAction(RelativeLayout contentViw){
//        imageView= (ImageView) findViewById(R.id.imageView);
//        ReceiveJob recive = new ReceiveJob(this);
//        recive.execute();

        for (int i = 0; i < contentViw.getChildCount(); i++) {
            View v = contentViw.getChildAt(i);
            if ( v instanceof Button){
                // Button btn = (Button)contentLayout.getChildAt(i);
                String btnName=v.getResources().getResourceName(v.getId()).split("/")[1];

                if(true){
                    Log.e("key ", btnName);
                    Button keybutton = (Button) findViewById(v.getId());

                    keybutton.setOnTouchListener(new View.OnTouchListener() {
                                                     public boolean onTouch(View view, MotionEvent event) {
//                            KeyJob job = new KeyJob();
                                                         try {
                                                             String keyName = view.getResources().getResourceName(view.getId()).split("/")[1];
                                                             //  Log.e("key", keyName);
                                                             Field f = Consts.class.getDeclaredField(keyName);
                                                             int keyCode = (int) f.get(Consts.class);
                                                             //Log.e("key", keyName + keyCode);
                                                             //job.execute(new String[]{keyCode + "", ""});
                                                             String pressType="";
                                                             if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
//                                    job.execute(new String[]{keyCode + "", ""});
                                                                 Log.e("key1_",keyName+"_"+keyCode+"key Press");
                                                                 sendKey(keyCode, pressType);
                                                             } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
//                                    job.execute(new String[]{keyCode + "", "0"});
                                                                 pressType="0";
                                                                 Log.e("key2_", keyName+"_"+keyCode + "key release");
                                                                 sendKey(keyCode,pressType);

                                                             }
                                                             Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                                                             vibrator.vibrate(new long[]{0, 100}, -1);

                                                         }
                                                         catch(Exception e){
                                                             e.printStackTrace();
                                                         }

                                                         return true;
                                                     }
                                                 }

                    );
//                    keybutton.setOnTouchListener(new View.OnTouchListener() {
//
//                        @Override
//                        public boolean onTouch(View view, MotionEvent motionevent) {
//                            int action = motionevent.getAction();
//                            if (action == MotionEvent.ACTION_DOWN) {
//                                Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
//                                mHandler.removeCallbacks(mUpdateTaskdown);
//                                mHandler.postAtTime(mUpdateTaskdown,
//                                        SystemClock.uptimeMillis() + 150);
//                            } else if (action == MotionEvent.ACTION_UP) {
//                                Log.i("repeatBtn", "MotionEvent.ACTION_UP");
//                                mHandler.removeCallbacks(mUpdateTaskdown);
//                            }//end else
//                            return false;
//                        }//end on touch
//
//                    });//end b other button

                }

            } }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        registerReceiver(receiver, new IntentFilter(LocalService.NOTIFICATION));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        SharedPreferences share = getSharedPreferences("perference", MODE_PRIVATE);

        String url =share.getString("url", "");
        int port =share.getInt("port", 12345);
        String passwd =share.getString("passwd", "888888");

        Log.e("config", url);
        Post.config(url, port, passwd);

        Intent intent = new Intent(this, ConnService.class);
        Calendar cal = Calendar.getInstance();
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 3 * 1000, pintent);

//        try{
//            Post.config(url, port,passwd);
//            Intent locintent = new Intent(getApplicationContext(), LocalService.class);
//            if(!LocalService.started)
//                startService(locintent);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Intent intent = new Intent(this, ConnService.class);
//        Calendar cal = Calendar.getInstance();
//        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
//        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP , cal.getTimeInMillis(), 1000, pintent);
//      //  contentLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.content_main, null);
        mainLayout= (RelativeLayout) findViewById(R.id.contentmain);

        //setContentView(mainLayout);
        bindAction(mainLayout);

//        Button backBtn = (Button) findViewById(R.id.Key_Escape);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SendfeedbackJob job = new SendfeedbackJob();
//                job.execute(new String[]{Consts.Key_Escape + "", ""});
//                ReceiveJob recive = new ReceiveJob();
//                recive.execute();
//                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                vibrator.vibrate(new long[]{0, 100}, -1);
//
//
//            }
//        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void sendKey(int keyCode,String pressType) {
        Intent intent = new Intent(this, ConnService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra("key", keyCode);
        intent.putExtra("press", pressType);
        startService(intent);

     //   textView.setText("Service started");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        Intent intent = new Intent(this, LocalService.class);
        stopService(intent);

    }
    @Override
    protected void onPause()
    {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(LocalService.NOTIFICATION));
        super.onResume();
        Log.e("app", "resume finished");

    }

    private Handler mHandler = new Handler();

    private Runnable mUpdateTaskup = new Runnable() {
        public void run() {
            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 100);
        }//end run
    };// end runnable

    private Runnable mUpdateTaskdown = new Runnable() {
        public void run() {
            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 100);
        }//end run
    };// end Runnable
}
