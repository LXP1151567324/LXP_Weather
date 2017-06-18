package com.example.Weather;
 
import com.example.json.R;

import android.os.Bundle;  
import android.app.Activity;  
import android.view.Menu;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.TextView;  
import android.widget.Toast;  
  
public class MainActivity extends Activity {  
    EditText et_city;  
    Button btn_query;  
    TextView tv_result_air;  
    TextView tv_result_wtr;  
    Air_Task air_task;  
    Wtr_Task wtr_task;  
    
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
  
        et_city = (EditText)findViewById(R.id.city);  
        tv_result_air = (TextView)findViewById(R.id.result_air);  
        tv_result_wtr = (TextView)findViewById(R.id.result_wtr);  
        btn_query = (Button)findViewById(R.id.query);  
  
        btn_query.setOnClickListener(new OnClickListener() {  
            public void onClick(View view) {  
                String city = et_city.getText().toString();  
                if (city.length() < 1) {  
                    Toast.makeText(MainActivity.this, "请输入城市名",  
                            Toast.LENGTH_LONG).show();  
                    return;  
                }  
                air_task = new Air_Task(MainActivity.this, tv_result_air);  
                air_task.execute(city);  
                
                wtr_task = new Wtr_Task(MainActivity.this, tv_result_wtr);  
                wtr_task.execute(city);  
            }  
        });  
    }  
  
    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        // Inflate the menu; this adds items to the action bar if it is present.  
        getMenuInflater().inflate(R.menu.main, menu);  
        return true;  
    }  
  
}