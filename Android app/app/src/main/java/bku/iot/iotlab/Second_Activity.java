package bku.iot.iotlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class Second_Activity extends AppCompatActivity {

    private ImageButton move2to1, move2to3;
    Button start_btn_1;
    EditText mixer_1,mixer_2,mixer_3,pump_in,pump_out;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        start_btn_1 = findViewById(R.id.start_btn_1);
        mixer_1 = findViewById(R.id.mixer_1);
        mixer_2 = findViewById(R.id.mixer_2);
        mixer_3 = findViewById(R.id.mixer_3);
        pump_in = findViewById(R.id.pump_in);
        pump_out = findViewById(R.id.pump_out);


        start_btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent senderIntent = new Intent(Second_Activity.this, MainActivity.class);
                int t1 = Integer.parseInt(mixer_1.getText().toString());
                int t2 = Integer.parseInt(mixer_2.getText().toString());
                int t3 = Integer.parseInt(mixer_3.getText().toString());
                int t4 = Integer.parseInt(pump_in.getText().toString());
                int t5 = Integer.parseInt(pump_out.getText().toString());
                senderIntent.putExtra("MIXER_1",t1);
                senderIntent.putExtra("MIXER_2",t2);
                senderIntent.putExtra("MIXER_3",t3);
                senderIntent.putExtra("PUMP_IN",t4);
                senderIntent.putExtra("PUMP_OUT",t5);
                startActivity(senderIntent);
            }
        });

        move2to1 = findViewById(R.id.Button1);
        move2to1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second_Activity.this, Third_Activity.class);
                startActivity(intent);
            }
        });

        move2to3 = findViewById(R.id.Button2);
        move2to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }



}