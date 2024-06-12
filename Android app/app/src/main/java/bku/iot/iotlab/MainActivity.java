package bku.iot.iotlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private ImageButton move1to2, move1to3;
    MQTTHelper mqttHelper;
    TextView countView1,countView2,countView3,countView4,countView5,txtHumi;
    LabeledSwitch btnMixer_1, btnMixer_2, btnMixer_3, btnPump_in, btnPump_out;
    boolean counterIsActive_1,counterIsActive_2,counterIsActive_3,counterIsActive_4,counterIsActive_5 = false;
    CountDownTimer countDownTimer_1,countDownTimer_2,countDownTimer_3,countDownTimer_4,countDownTimer_5;
    boolean flag = false;

    int Temp,Temp2,Temp3,Temp4,Temp5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countView1=findViewById(R.id.countView1);
        countView2=findViewById(R.id.countView2);
        countView3=findViewById(R.id.countView3);
        countView4=findViewById(R.id.countView4);
        countView5=findViewById(R.id.countView5);
        txtHumi=findViewById(R.id.txtHumi);

        Intent receiverIntent = getIntent();
        int Value_1 = receiverIntent.getIntExtra("MIXER_1",15);
        int Value_2 = receiverIntent.getIntExtra("MIXER_2",15);
        int Value_3 = receiverIntent.getIntExtra("MIXER_3",15);
        int Value_4 = receiverIntent.getIntExtra("PUMP_IN",15);
        int Value_5 = receiverIntent.getIntExtra("PUMP_OUT",15);

//        Bundle b = getIntent().getExtras();
//        Value_1 = b.getString("MIXER_1") + "000";
//        Value_2 = b.getString("MIXER_2") + "000";
//        Value_3 = b.getString("MIXER_3") + "000";
//        Value_4 = b.getString("PUMP_IN") + "000";
//        Value_5 = b.getString("PUMP_OUT") + "000";

        Temp = Value_1 * 1000;
        Temp2 = Value_2 * 1000;
        Temp3 = Value_3 * 1000;
        Temp4 = Value_4 * 1000;
        Temp5 = Value_5 * 1000;

        move1to2=findViewById(R.id.Button1);
        move1to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Third_Activity.class);
                startActivity(intent);
            }
        });

        move1to3=findViewById(R.id.Button3);
        move1to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Second_Activity.class);
                startActivity(intent);
            }
        });


        btnMixer_1=findViewById((R.id.btnMixer_1));
        btnMixer_2=findViewById((R.id.btnMixer_2));
        btnMixer_3=findViewById((R.id.btnMixer_3));
        btnPump_in=findViewById((R.id.btnPump_in));
        btnPump_out=findViewById((R.id.btnPump_out));


        btnMixer_1.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true){
                    sendDataMQTT("QuangBang/feeds/nutnhan1","1");
                }
                else{
                    sendDataMQTT("QuangBang/feeds/nutnhan1","0");
                }
            }
        });

        btnMixer_2.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true){
                    sendDataMQTT("QuangBang/feeds/nutnhan2","1");
                }
                else{
                    sendDataMQTT("QuangBang/feeds/nutnhan2","0");
                }
            }
        });

        btnMixer_3.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true){
                    sendDataMQTT("QuangBang/feeds/nutnhan3","1");
                }
                else{
                    sendDataMQTT("QuangBang/feeds/nutnhan3","0");
                }
            }
        });

        btnPump_in.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true){
                    sendDataMQTT("QuangBang/feeds/nutnhan4","1");
                }
                else{
                    sendDataMQTT("QuangBang/feeds/nutnhan4","0");
                }
            }
        });

        btnPump_out.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn == true){
                    sendDataMQTT("QuangBang/feeds/nutnhan5","1");
                }
                else{
                    sendDataMQTT("QuangBang/feeds/nutnhan5","0");
                }
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

                if (topic.contains("nutnhan1")){
                    if (message.toString().equals("1")){
                        btnMixer_1.setOn(true);
                    }else{
                        btnMixer_1.setOn(false);
                    }

                }
                else if (topic.contains("nutnhan2")){
                    if (message.toString().equals("1")){
                        btnMixer_2.setOn(true);
                    }else{
                        btnMixer_2.setOn(false);
                    }

                }
                else if (topic.contains("nutnhan3")){
                    if (message.toString().equals("1")){
                        btnMixer_3.setOn(true);
                    }else{
                        btnMixer_3.setOn(false);
                    }

                }
                else if (topic.contains("nutnhan4")){
                    if (message.toString().equals("1")){
                        btnPump_in.setOn(true);
                    }else{
                        btnPump_in.setOn(false);
                    }

                }
                else if (topic.contains("nutnhan5")){
                    if (message.toString().equals("1")){
                        btnPump_out.setOn(true);
                    }else{
                        btnPump_out.setOn(false);
                    }

                }
                else if (topic.contains("cambien3")){
                    float humi = Float.parseFloat(message.toString());
                    txtHumi.setText(message.toString() + "%");
                    if (humi < 80 && flag == false)
                    {
                        btnMixer_1.performClick();
                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void update_1(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        String secondsFinal = "";
        if (seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else {
            secondsFinal = "" + seconds;
        }
        countView1.setText("" + minutes + ":" + secondsFinal);
    }

    private void update_2(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        String secondsFinal = "";
        if (seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else {
            secondsFinal = "" + seconds;
        }
        countView2.setText("" + minutes + ":" + secondsFinal);
    }

    private void update_3(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        String secondsFinal = "";
        if (seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else {
            secondsFinal = "" + seconds;
        }
        countView3.setText("" + minutes + ":" + secondsFinal);
    }

    private void update_4(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        String secondsFinal = "";
        if (seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else {
            secondsFinal = "" + seconds;
        }
        countView4.setText("" + minutes + ":" + secondsFinal);
    }

    private void update_5(int i) {
        int minutes = i / 60;
        int seconds = i % 60;
        String secondsFinal = "";
        if (seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else {
            secondsFinal = "" + seconds;
        }
        countView5.setText("" + minutes + ":" + secondsFinal);
    }

    public void Start_timer_1(View view) {
        if (counterIsActive_1 == false){
            counterIsActive_1 = true;
            flag = true;
            countDownTimer_1 = new CountDownTimer(Temp + 100, 1000) {
                @Override
                public void onTick(long l) {
                    update_1((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    btnMixer_1.performClick();
                    btnMixer_2.performClick();
                    countView1.setText("0:00");
                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }.start();
        } else {
            countDownTimer_1.cancel();
            countView1.setText("0:00");
            counterIsActive_1 = false;
        }
    }

    public void Start_timer_2(View view) {
        if (counterIsActive_2 == false){
            counterIsActive_2 = true;
            flag = true;
            countDownTimer_2 = new CountDownTimer(Temp2 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    update_2((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    btnMixer_2.performClick();
                    btnMixer_3.performClick();
                    countView2.setText("0:00");
                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }.start();
        } else {
            countDownTimer_2.cancel();
            countView2.setText("0:00");
            counterIsActive_2 = false;
        }
    }

    public void Start_timer_3(View view) {
        if (counterIsActive_3 == false){
            counterIsActive_3 = true;
            flag = true;
            countDownTimer_3 = new CountDownTimer(Temp3 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    update_3((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    btnMixer_3.performClick();
                    btnPump_in.performClick();
                    countView3.setText("0:00");
                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }.start();
        } else {
            countDownTimer_3.cancel();
            countView3.setText("0:00");
            counterIsActive_3 = false;
        }
    }

    public void Start_timer_4(View view) {
        if (counterIsActive_4 == false){
            counterIsActive_4 = true;
            flag = true;
            countDownTimer_4 = new CountDownTimer(Temp4 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    update_4((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    btnPump_in.performClick();
                    btnPump_out.performClick();
                    countView4.setText("0:00");
                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }.start();
        } else {
            countDownTimer_4.cancel();
            countView4.setText("0:00");
            counterIsActive_4 = false;
        }
    }

    public void Start_timer_5(View view) {
        if (counterIsActive_5 == false){
            counterIsActive_5 = true;
            flag = true;
            countDownTimer_5 = new CountDownTimer(Temp4 + 100, 1000) {
                @Override
                public void onTick(long l) {
                    update_5((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    btnPump_out.performClick();
                    countView4.setText("0:00");
                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                }
            }.start();
        } else {
            countDownTimer_5.cancel();
            countView5.setText("0:00");
            counterIsActive_5 = false;
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if(counterIsActive_1)
        {
            countDownTimer_1.cancel();
            flag = false;
        }
        else if (counterIsActive_2)
        {
            countDownTimer_2.cancel();
            flag = false;
        }
        else if (counterIsActive_3)
        {
            countDownTimer_3.cancel();
            flag = false;
        }
        else if (counterIsActive_4)
        {
            countDownTimer_4.cancel();
            flag = false;
        }
        else if (counterIsActive_5)
        {
            countDownTimer_5.cancel();
            flag = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterIsActive_1)
        {
            countDownTimer_1.cancel();
            flag = false;
        }
        else if (counterIsActive_2)
        {
            countDownTimer_2.cancel();
            flag = false;
        }
        else if (counterIsActive_3)
        {
            countDownTimer_3.cancel();
            flag = false;
        }
        else if (counterIsActive_4)
        {
            countDownTimer_4.cancel();
            flag = false;
        }
        else if (counterIsActive_5)
        {
            countDownTimer_5.cancel();
            flag = false;
        }
    }


}