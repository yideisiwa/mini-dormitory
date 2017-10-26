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

/**
 * Created by Mike_Hong on 2017/10/26.
 */

public class MyAdapter extends BaseAdapter {

    private List<String> list = new ArrayList<String>();
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
        final EditText editText = (EditText)convertView.findViewById(R.id.id);

        //为editText设置TextChangedListener，每次改变的值设置到hashMap
        //我们要拿到里面的值根据position拿值
        editText.addTextChangedListener(new TextWatcher() {
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
                list.set(position,editText.getText().toString());
            }
        });
        if(list.size()!=0)
        {
            editText.setText(list.get(position));
        }
        return convertView;
    }

    public void updateListView(int other_student_number)
    {
        list.clear();
        for(int i=0;i<other_student_number;i++)
        {
            list.add(new String(""));
        }
        notifyDataSetChanged();
    }
}
