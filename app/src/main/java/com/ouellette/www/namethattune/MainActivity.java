package com.ouellette.www.namethattune;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static AudioContainer ac = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ac = new AudioContainer(this);

        Button p = (Button)findViewById(R.id.play);
        p.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, Playing.class);
                startActivity(intent);
            }
        });

        Button i = (Button)findViewById(R.id.info);
        i.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, Instructions.class);
                startActivity(intent);
            }
        });

        Button h = (Button)findViewById(R.id.scores);
        h.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, HighScores.class);
                startActivity(intent);
            }
        });

    }

}
