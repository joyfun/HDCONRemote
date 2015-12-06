package hdcon.com.remote;

import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

public class CtrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        SharedPreferences share = getSharedPreferences("perference", MODE_PRIVATE);

        String url =share.getString("url", "");
        int port =share.getInt("port", 12345);
        Log.e("config", url);
        Post.config(url, port);
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

        RelativeLayout contentLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.content_main, null);
        for (int i = 0; i < contentLayout.getChildCount(); i++) {
            View v = contentLayout.getChildAt(i);
            if ( v instanceof Button){
               // Button btn = (Button)contentLayout.getChildAt(i);
                String btnName=v.getResources().getResourceName(v.getId()).split("/")[1];

                if(true){
                    Log.e("key", btnName);
                    Button keybutton = (Button) findViewById(v.getId());

                    keybutton.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View view, MotionEvent event) {
                            KeyJob job = new KeyJob();
                            try {
                                String keyName = view.getResources().getResourceName(view.getId()).split("/")[1];
                              //  Log.e("key", keyName);
                                Field f = Consts.class.getDeclaredField(keyName);
                                int keyCode = (int) f.get(Consts.class);
                                //Log.e("key", keyName + keyCode);
                                //job.execute(new String[]{keyCode + "", ""});
                                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                                    job.execute(new String[]{keyCode + "", ""});
                                    Log.e("key",keyName+"key Press");

                                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                                    job.execute(new String[]{keyCode + "", "0"});
                                    Log.e("key", keyName+"key release");

                                }
                                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                                 vibrator.vibrate(new long[]{0, 100}, -1);

                            }
                                catch(Exception e){

                                }

                                return true;
                            }
                        }

                        );
//                        keybutton.setOnClickListener(new View.OnClickListener()
//
//                                                     {
//                                                         @Override
//                                                         public void onClick(View btv) {
//                                                             KeyJob job = new KeyJob();
//                                                             try {
//                                                                 String keyName = btv.getResources().getResourceName(btv.getId()).split("/")[1];
//                                                                 Log.e("key", keyName);
//                                                                 Field f = Consts.class.getDeclaredField(keyName);
//                                                                 int keyCode = (int) f.get(Consts.class);
//                                                                 Log.e("key", keyName + keyCode);
//                                                                 job.execute(new String[]{keyCode + "", ""});
//                                                             } catch (Exception e) {
//
//                                                             }
//
//
//                                                             Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//                                                             vibrator.vibrate(new long[]{0, 100}, -1);
//                                                         }
//                                                     }
//
//                        );
                    }

                } }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
