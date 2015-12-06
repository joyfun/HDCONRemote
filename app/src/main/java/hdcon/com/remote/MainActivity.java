package hdcon.com.remote;

import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences share = getSharedPreferences("perference", MODE_PRIVATE);

        String url =share.getString("url", "");
        int port =share.getInt("port", 12345);
        Log.e("config",url);
        Post.config(url,port);

        RelativeLayout contentLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
        for (int i = 0; i < contentLayout.getChildCount(); i++) {
            View v = contentLayout.getChildAt(i);
            if ( v instanceof Button){
                Button btn = (Button)contentLayout.getChildAt(i);
                String btnName=btn.getResources().getResourceName(btn.getId()).split("/")[1];

                if(true){
                    Log.e("key",btnName);

                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View btv) {
//                            KeyJob job = new KeyJob();
//                            try{
//                                String keyName=v.getResources().getResourceName(v.getId()).split("/")[1];
//                                Log.e("key",keyName);
//                                Field f = Consts.class.getDeclaredField(keyName);
//                                int keyCode = (int)f.get(Consts.class);
//                                Log.e("key",keyName+keyCode);
//                                job.execute(new String[]{keyCode + "", ""});
//                            }
//                            catch(Exception e){
//
//                            }


                            Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                            vibrator.vibrate(new long[]{0, 100}, -1);
                        }
                    });
                }

            } }

        Button backBtn = (Button) findViewById(R.id.Key_Escape);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendfeedbackJob job = new SendfeedbackJob();
                job.execute(new String[]{Consts.Key_Escape+"", ""});
                ReceiveJob recive = new ReceiveJob();
                recive.execute();
                Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{0, 100}, -1);



            }
        });
//        Button keyBtn =(Button)findViewById(R.id.key_F10);
//        keyBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                KeyJob job = new KeyJob();
//                job.execute(new String[]{Consts.Key_F10+"", ""});
//                Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
//                vibrator.vibrate(new long[]{0, 100}, -1);
//            }
//        });
      //  RelativeLayout rou=(RelativeLayout)findViewById(R.layout.activity_main);
      /**  RelativeLayout contentLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.content_main, null);
        for (int i = 0; i < contentLayout.getChildCount(); i++) {
            View v = contentLayout.getChildAt(i);
            if ( v instanceof Button){
                Button btn = (Button)contentLayout.getChildAt(i);
                String btnName=btn.getText()+"";
                if(btnName.startsWith("Key_")){
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            KeyJob job = new KeyJob();
                            job.execute(new String[]{Consts.Key_F10+"", ""});
                            Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                            vibrator.vibrate(new long[]{0, 100}, -1);
                        }
                    });
                }

            } }
**/
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
