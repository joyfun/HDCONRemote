package hdcon.com.remote;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
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
    EditText portText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        urlText = (EditText)SetActivity.this.findViewById(R.id.controllerUrl);
        portText = (EditText)SetActivity.this.findViewById(R.id.controllerPort);
        SharedPreferences  share = getSharedPreferences("perference", MODE_PRIVATE);
        urlText.setText(share.getString("url",""));
        portText.setText(share.getInt("port",12345)+"");
        Button connectBtn = (Button) findViewById(R.id.connectButton);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url=urlText.getText().toString();
                int port=Integer.valueOf(portText.getText().toString());
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                SharedPreferences  share = getSharedPreferences("perference", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();//取得编辑器
                editor.putString("url", url);//存储配置 参数1 是key 参数2 是值
                editor.putInt("port", port);
                editor.commit();//提交刷新数据
                Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{0, 100}, -1);

                Intent in = new Intent();
                in.setClassName(getApplicationContext(), "hdcon.com.remote.CtrlActivity");
                startActivity( in );

                //    setContentView(R.layout.activity_main);

            }
        });

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
