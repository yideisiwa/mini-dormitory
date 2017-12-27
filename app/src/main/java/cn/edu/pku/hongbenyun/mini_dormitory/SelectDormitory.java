package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.pku.hongbenyun.bean.User;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Mike_Hong on 2017/10/26.
 */

public class SelectDormitory extends Activity implements View.OnClickListener{

    private User user;
    private TextView studentid_Tv;
    private TextView name_Tv;
    private TextView gender_Tv;
    private TextView vcode_Tv;
    private TextView building_5_Tv;
    private TextView building_13_Tv;
    private TextView building_14_Tv;
    private TextView building_8_Tv;
    private TextView building_9_Tv;
    private Button submit_Bt;
    private Spinner location_Sp;
    private Spinner total_number_Sp;
    private ListView other_students_Lv;
    private TextView group_info_Tv;
    private ImageView mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.selection);

        Intent i =this.getIntent();
        user=(User)i.getSerializableExtra("user");

        studentid_Tv = (TextView)findViewById(R.id.studentid);
        name_Tv = (TextView)findViewById(R.id.name);
        gender_Tv = (TextView)findViewById(R.id.gender);
        vcode_Tv = (TextView)findViewById(R.id.vcode);
        building_5_Tv = (TextView)findViewById(R.id.building_5);
        building_13_Tv = (TextView)findViewById(R.id.building_13);
        building_14_Tv = (TextView)findViewById(R.id.building_14);
        building_8_Tv = (TextView)findViewById(R.id.building_8);
        building_9_Tv = (TextView)findViewById(R.id.building_9);

        mBackBtn = (ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        submit_Bt = (Button)findViewById(R.id.submit);
        submit_Bt.setOnClickListener(this);

        group_info_Tv = (TextView)findViewById(R.id.group_info);
        other_students_Lv = (ListView)findViewById(R.id.other_students);
        final MyAdapter myAapter = new MyAdapter(this);
        other_students_Lv.setAdapter(myAapter);
        setListViewHeightBasedOnChildren(other_students_Lv);

        location_Sp = (Spinner)findViewById(R.id.location);

        studentid_Tv.setText(user.getStudentid());
        name_Tv.setText(user.getName());
        gender_Tv.setText(user.getGender());
        vcode_Tv.setText(user.getVcode());


        total_number_Sp = (Spinner)findViewById(R.id.total_number);
        final String[] mItems = {"一个人","两个人","三个人","四个人"};

        ArrayAdapter<String> number_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mItems);
        number_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        total_number_Sp .setAdapter(number_adapter);

        total_number_Sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos > 0)
                {
                    group_info_Tv.setVisibility(TextView.VISIBLE);
                    other_students_Lv.setVisibility(ListView.VISIBLE);
                }
                else
                {
                    group_info_Tv.setVisibility(TextView.INVISIBLE);
                    other_students_Lv.setVisibility(ListView.INVISIBLE);
                }
                myAapter.updateListView(pos);
                setListViewHeightBasedOnChildren(other_students_Lv);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String url = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom";
        OkGo.get(url)                            // 请求方式和请求url
                .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .params("gender",user.getGender().equals("男")?"1":"2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject all_object = new JSONObject(s);
                            int errcode = all_object.getInt("errcode");
                            String data = all_object.getString("data");
                            JSONObject building_info = new JSONObject(data);
                            if(errcode != 0)
                            {
                                JSONObject data_object = new JSONObject(data);
                                String errmsg = data_object.getString("errmsg");
                                Toast.makeText(SelectDormitory.this,errmsg,Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                List<String> building_list= new ArrayList<String>();

                                String t = building_info.getString("5");
                                building_5_Tv.setText(t);
                                if(!t.equals("0"))
                                    building_list.add("5");
                                t = building_info.getString("13");
                                building_13_Tv.setText(t);
                                if(!t.equals("0"))
                                    building_list.add("13");
                                t = building_info.getString("14");
                                building_14_Tv.setText(t);
                                if(!t.equals("0"))
                                    building_list.add("14");
                                t = building_info.getString("8");
                                building_8_Tv.setText(t);
                                if(!t.equals("0"))
                                    building_list.add("8");
                                t = building_info.getString("9");
                                building_9_Tv.setText(t);
                                if(!t.equals("0"))
                                    building_list.add("9");

                                ArrayAdapter<String> location_adapter=new ArrayAdapter<String>(SelectDormitory.this,
                                        android.R.layout.simple_spinner_item, building_list);
                                location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                location_Sp .setAdapter(location_adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit)
        {
            String s= location_Sp.getSelectedItem().toString()+"\n"+total_number_Sp.getSelectedItem().toString()
                    + "\n"+user.getStudentid()+" "+user.getVcode();
            for(int i = 0; i < other_students_Lv.getAdapter().getCount(); i++)
            {
                EditText studentid_Et = (EditText) other_students_Lv.getAdapter().getView(i,null,null).findViewById(R.id.studentid);
                EditText vcodeid_Et = (EditText) other_students_Lv.getAdapter().getView(i,null,null).findViewById(R.id.vcode_other);
                s= s+"\n"+studentid_Et.getText().toString()+" "+vcodeid_Et.getText().toString();
            }
            Log.d("mydoritory",s);

            String url = "https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
            HashMap<String,String> hashMap=new HashMap<>();
            for(int i = 0; i < other_students_Lv.getAdapter().getCount(); i++)
            {
                EditText studentid_Et = (EditText) other_students_Lv.getAdapter().getView(i,null,null).findViewById(R.id.studentid);
                EditText vcodeid_Et = (EditText) other_students_Lv.getAdapter().getView(i,null,null).findViewById(R.id.vcode_other);
                hashMap.put("stu"+i+1+"id",studentid_Et.getText().toString());
                hashMap.put("v"+i+1+"code",vcodeid_Et.getText().toString());
            }
            for(int i = 0;i < other_students_Lv.getAdapter().getCount(); i++)
            {
                String studentid = hashMap.get("stu"+i+1+"id");
                String vcode = hashMap.get("v"+i+1+"code");
                if(studentid.equals("") || vcode.equals(""))
                {
                    Toast.makeText(SelectDormitory.this,"请将同住人信息填写完整",Toast.LENGTH_LONG).show();
                    return;
                }
                if(studentid.equals(user.getStudentid()))
                {
                    Toast.makeText(SelectDormitory.this,"同住人不可为本人",Toast.LENGTH_LONG).show();
                    return;
                }
                for(int j = i + 1; j <other_students_Lv.getAdapter().getCount();j++)
                {
                    if(studentid.equals(hashMap.get("stu"+j+1+"id")))
                    {
                        Toast.makeText(SelectDormitory.this,"同住人学号不可相同",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
            OkGo.post(url)//
                    .tag(this)//
                    .params("num",total_number_Sp.getSelectedItemPosition()+1)
                    .params("stuid",user.getStudentid())
                    .params("buildingNo",Integer.parseInt(location_Sp.getSelectedItem().toString()))
                    .params(hashMap)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            try {
                                JSONObject all_object = new JSONObject(s);
                                int errcode = all_object.getInt("errcode");
                                if(errcode != 0)
                                {
                                    Toast.makeText(SelectDormitory.this,"err",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Intent i = new Intent(SelectDormitory.this,ResultActivity.class);
                                    i.putExtra("username",user.getStudentid());
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });

        }
        if (v.getId() == R.id.title_back)
        {
            onBackPressed();
        }
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
// 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
// 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
// listView.getDividerHeight()获取子项间分隔符占用的高度
// params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
