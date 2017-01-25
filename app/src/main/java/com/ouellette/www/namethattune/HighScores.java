package com.ouellette.www.namethattune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Collections;
import java.util.PriorityQueue;

public class HighScores extends AppCompatActivity {

    static Integer[] scores = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    TextView[] labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        labels = new TextView[]{(TextView) findViewById(R.id.s1), (TextView)findViewById(R.id.s2), (TextView)findViewById(R.id.s3),
                (TextView)findViewById(R.id.s4), (TextView)findViewById(R.id.s5), (TextView)findViewById(R.id.s6),
                (TextView)findViewById(R.id.s7), (TextView)findViewById(R.id.s8), (TextView)findViewById(R.id.s9),
                (TextView)findViewById(R.id.s10)};

        for(int i = 0; i <10; i++){
            if(scores[i] == 0)
                labels[i].setText("-");
            else
                labels[i].setText(Integer.toString(scores[i]));
        }

    }

    public static void addScore(int score){
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(10, Collections.<Integer>reverseOrder());
        priorityQueue.add(score);
        for(Integer i : scores)
            priorityQueue.add(i);
        for(int count = 0; count<10; count++){
            scores[count] = priorityQueue.poll();
        }
    }
}
