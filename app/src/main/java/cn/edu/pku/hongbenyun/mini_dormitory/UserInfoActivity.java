package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by Mike_Hong on 2017/10/25.
 */

public class UserInfoActivity extends Activity implements View.OnClickListener{

    private Button start_selection_Bt;
    private TextView studentid_Tv;
    private TextView name_Tv;
    private TextView gender_Tv;
    private TextView vcode_Tv;
    private TextView room_Tv;
    private TextView building_Tv;
    private TextView location_Tv;
    private TextView grade_Tv;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.user_info);

        studentid_Tv = (TextView)findViewById(R.id.studentid);
        name_Tv = (TextView)findViewById(R.id.name);
        gender_Tv = (TextView)findViewById(R.id.gender);
        vcode_Tv = (TextView)findViewById(R.id.vcode);
        room_Tv = (TextView)findViewById(R.id.room);
        building_Tv = (TextView)findViewById(R.id.building);
        location_Tv = (TextView)findViewById(R.id.location);
        grade_Tv = (TextView)findViewById(R.id.grade);

        start_selection_Bt = (Button)findViewById(R.id.start_selection);
        start_selection_Bt.setOnClickListener(this);

        Intent intent =this.getIntent();
        String username=intent.getStringExtra("username");

        String url = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail";
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .params("stuid",username)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject all_object = new JSONObject(s);
                            int errcode = all_object.getInt("errcode");
                            String data = all_object.getString("data");
                            Gson gson = new Gson();
                            user = gson.fromJson(data, User.class);
                            if(errcode != 0)
                                Toast.makeText(UserInfoActivity.this,"服务器错误!",Toast.LENGTH_LONG).show();
                            else
                            {
                                studentid_Tv.setText(user.getStudentid());
                                name_Tv.setText(user.getName());
                                gender_Tv.setText(user.getGender());
                                vcode_Tv.setText(user.getVcode());
                                room_Tv.setText(user.getRoom());
                                building_Tv.setText(user.getBuilding());
                                location_Tv.setText(user.getLocation());
                                grade_Tv.setText(user.getGrade());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_selection)
        {
            Intent i = new Intent(this, SelectDormitory.class);
            i.putExtra("userID",user.getStudentid());
            startActivity(i);
        }
    }
}
