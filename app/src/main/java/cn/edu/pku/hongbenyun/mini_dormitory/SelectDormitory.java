package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Mike_Hong on 2017/10/26.
 */

public class SelectDormitory extends Activity implements View.OnClickListener{

    private String userID;
    private Button submit_Bt;
    private Spinner location_Sp;
    private Spinner total_number_Sp;
    private ListView other_students_Lv;
    private ImageView mBackBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.selection);

        Intent i =this.getIntent();
        userID=i.getStringExtra("userID");

        mBackBtn = (ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);
        submit_Bt = (Button)findViewById(R.id.submit);
        submit_Bt.setOnClickListener(this);

        other_students_Lv = (ListView)findViewById(R.id.other_students);
        final MyAdapter myAapter = new MyAdapter(this);
        other_students_Lv.setAdapter(myAapter);

        location_Sp = (Spinner)findViewById(R.id.location);
        String[] lItems = {"5号楼","13号楼","9号楼"};
        ArrayAdapter<String> location_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lItems);
        location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_Sp .setAdapter(location_adapter);

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
                    other_students_Lv.setVisibility(ListView.VISIBLE);
                else
                    other_students_Lv.setVisibility(ListView.INVISIBLE);
                myAapter.updateListView(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit)
        {
            String s= location_Sp.getSelectedItem().toString()+"\n"+total_number_Sp.getSelectedItem().toString()+"\n"+userID;
            for(int i = 0; i < other_students_Lv.getAdapter().getCount(); i++)
            {
                EditText editText = (EditText) other_students_Lv.getAdapter().getView(i,null,null).findViewById(R.id.id);
                s= s+"\n"+editText.getText().toString();
            }
            Toast.makeText(this,s , Toast.LENGTH_SHORT).show();

        }
        if (v.getId() == R.id.title_back)
        {
            onBackPressed();
        }
    }
}
