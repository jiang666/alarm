package com.example.alarm.evenbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alarm.R;

import org.greenrobot.eventbus.EventBus;

public class EvenbusTActivity extends AppCompatActivity {
    private Button btn_FirstEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenbus_two);
        btn_FirstEvent = (Button) findViewById(R.id.btn_first_event);

        btn_FirstEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EventBus.getDefault().post(
                        new Event("FirstEvent btn clicked"));
            }
        });
    }
}
