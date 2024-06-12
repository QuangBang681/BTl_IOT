package bku.iot.iotlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Third_Activity extends AppCompatActivity {

    private ImageButton move3to1, move3to2;
    MQTTHelper mqttHelper;
    TextView txtTemp,Date,Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Date = findViewById(R.id.text_date);
        Time = findViewById(R.id.text_time);

        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat currenttimenow = new SimpleDateFormat("hh:mm");
        SimpleDateFormat currentdatenow = new SimpleDateFormat("EEE ,dd MMM, yyyy");
        String timenow = currenttimenow.format(currentTime.getTime());
        String datenow = currentdatenow.format(currentTime.getTime());

        Date.setText(datenow);
        Time.setText(timenow);

        txtTemp = findViewById(R.id.txtTemp);


        move3to1=findViewById(R.id.Button2);
        move3to1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Third_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        move3to2=findViewById(R.id.Button3);
        move3to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Third_Activity.this,Second_Activity.class);
                startActivity(intent);
            }
        });

        startMQTT();
    }



    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }
    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "*** " + message.toString());
                float value = Float.parseFloat(message.toString());

                if (topic.contains("cambien1")){
                    txtTemp.setText(message.toString() + "℃");
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}