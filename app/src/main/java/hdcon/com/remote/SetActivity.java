package hdcon.com.remote;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;

public class SetActivity extends AppCompatActivity {
    EditText urlText;
   // EditText portText;
    EditText passwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        urlText = (EditText) SetActivity.this.findViewById(R.id.controllerUrl);
        //portText = (EditText) SetActivity.this.findViewById(R.id.controllerPort);
        passwdText = (EditText) SetActivity.this.findViewById(R.id.controllerPasswd);

        SharedPreferences share = getSharedPreferences("perference", MODE_PRIVATE);
        urlText.setText(share.getString("url", ""));
        //portText.setText(share.getInt("port", 12345) + "");
        passwdText.setText(share.getString("passwd", "888888"));

        Button connectBtn = (Button) findViewById(R.id.connectButton);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("app","buttonClick");
                String url = urlText.getText().toString();
               // int port = Integer.valueOf(portText.getText().toString());
                String passwd=passwdText.getText().toString();
                SharedPreferences share = getSharedPreferences("perference", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();//取得编辑器
                editor.putString("url", url);//存储配置 参数1 是key 参数2 是值
                editor.putInt("port", 12345);
                editor.putString("passwd", passwd);
                editor.commit();//提交刷新数据
                Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{0, 100}, -1);

                try{
                    int port =share.getInt("port", 12345);
                    Post.config(url, port,passwd);
                    Intent intent = new Intent(getApplicationContext(), LocalService.class);
                    if(!LocalService.started)
                        stopService(intent);
                        startService(intent);
                         }catch (Exception e){
                    e.printStackTrace();
                        }
//            if (Post.auth){
//                    Intent in = new Intent();
//                    in.setClassName(getApplicationContext(), "hdcon.com.remote.CtrlActivity");
//                    startActivity(in);
//                    finish();
//                }
                //    setContentView(R.layout.activity_main);

            }
        });

    }
    @Override
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(LocalService.AUTHRESULT));
        super.onResume();
    if (Post.auth){
    Intent in = new Intent();
    in.setClassName(getApplicationContext(), "hdcon.com.remote.CtrlActivity");
    startActivity(in);
}
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
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt("status");
                boolean auth = bundle.getBoolean("auth");
                boolean hide = bundle.getBoolean("hide");
                if (auth) {
                    Intent in = new Intent();
                    in.setClassName(context, "hdcon.com.remote.CtrlActivity");
                    startActivity(in);
                    finish();
                }else{
//                    Post.close();
                    if(!hide){
                        Toast.makeText(context, "登陆失败 请重新配置", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }};
}
