package cn.edu.pku.hongbenyun.mini_dormitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mike_Hong on 2017/10/25.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private Button start_selection_Bt;
    private TextView id_Tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.user_info);

        id_Tv = (TextView)findViewById(R.id.id);
        start_selection_Bt = (Button)findViewById(R.id.start_selection);
        start_selection_Bt.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_selection)
        {
            Intent i = new Intent(this, SelectDormitory.class);
            i.putExtra("userID",id_Tv.getText());
            startActivity(i);
        }
    }
}
