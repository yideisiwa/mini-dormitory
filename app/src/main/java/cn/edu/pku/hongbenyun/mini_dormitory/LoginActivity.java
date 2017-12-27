package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mike_Hong on 2017/11/22.
 */

public class LoginActivity extends Activity implements View.OnClickListener
{
    private EditText user_name_Et;
    private EditText password_Et;
    private Button login_Bt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.login);

        user_name_Et = (EditText)findViewById(R.id.user_name);
        password_Et = (EditText)findViewById(R.id.password);
        login_Bt = (Button)findViewById(R.id.login);
        login_Bt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }

    public void login()
    {
        String url = "https://api.mysspku.com/index.php/V1/MobileCourse/Login";
        final String username = user_name_Et.getText().toString();
        String password = password_Et.getText().toString();
        if(username.equals("") || password.equals(""))
            Toast.makeText(LoginActivity.this,"用户名密码不可为空",Toast.LENGTH_LONG).show();
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .params("username",username)
                .params("password",password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject all_object = new JSONObject(s);
                            int errcode = all_object.getInt("errcode");
                            String data = all_object.getString("data");
                            JSONObject data_object = new JSONObject(data);
                            String errmsg = data_object.getString("errmsg");
                            if(errcode == 0)
                            {
                                Intent i = new Intent(LoginActivity.this,UserInfoActivity.class);
                                i.putExtra("username",username);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,errmsg,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
