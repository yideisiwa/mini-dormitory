package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.pku.hongbenyun.bean.User;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Mike_Hong on 2017/11/29.
 */

public class ResultActivity extends Activity implements View.OnClickListener{

    private Button return_back_Bt;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.result);

        return_back_Bt = (Button)findViewById(R.id.return_back);
        return_back_Bt.setOnClickListener(this);

        Intent intent =this.getIntent();
        username=intent.getStringExtra("username");

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.return_back)
        {
            Intent i = new Intent(this, UserInfoActivity.class);
            i.putExtra("username",username);
            startActivity(i);
        }
    }
}
