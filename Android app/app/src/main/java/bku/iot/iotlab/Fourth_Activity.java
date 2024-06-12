package bku.iot.iotlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Fourth_Activity extends AppCompatActivity {

    private ImageButton move4to1, move4to2, move4to3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        move4to1=findViewById(R.id.Button1);
        move4to1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Fourth_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        move4to2=findViewById(R.id.Button2);
        move4to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Fourth_Activity.this,Second_Activity.class);
                startActivity(intent);
            }
        });

        move4to3=findViewById(R.id.Button3);
        move4to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Fourth_Activity.this,Third_Activity.class);
                startActivity(intent);
            }
        });
    }
}