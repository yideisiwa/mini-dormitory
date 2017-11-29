package cn.edu.pku.hongbenyun.mini_dormitory;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.hongbenyun.bean.User;

/**
 * Created by Mike_Hong on 2017/10/26.
 */

public class MyAdapter extends BaseAdapter {

    private List<User> list = new ArrayList<User>();
    private Context context;

    public MyAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        final EditText studentid_Et = (EditText)convertView.findViewById(R.id.studentid);
        final EditText vcode_Et = (EditText) convertView.findViewById(R.id.vcode_other);

        //为editText设置TextChangedListener，每次改变的值设置到hashMap
        //我们要拿到里面的值根据position拿值
        studentid_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count,int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                list.get(position).setStudentid(studentid_Et.getText().toString());
            }
        });

        vcode_Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count,int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                list.get(position).setVcode(vcode_Et.getText().toString());
            }
        });

        if(list.size()!=0)
        {
            studentid_Et.setText(list.get(position).getStudentid());
            vcode_Et.setText(list.get(position).getVcode());
        }
        return convertView;
    }

    public void updateListView(int other_student_number)
    {
        list.clear();
        for(int i = 0;i < other_student_number; i++)
        {
            User user = new User();
            user.setStudentid("");
            user.setVcode("");
            list.add(user);
        }
        notifyDataSetChanged();
    }
}
