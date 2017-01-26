package com.ouellette.www.namethattune;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Playing extends AppCompatActivity {

    TextView timer;
    TextView score;
    TextView question;
    int scoreNum=0;
    int currQuestion;
    Button[] options = new Button[4];
    MediaPlayer mediaPlayer;
    Random r = new Random();
    HashMap<String, Integer> files;
    ArrayList<String> setOfSongs;
    CountDownTimer c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);
        timer = (TextView)findViewById(R.id.timer);
        score = (TextView)findViewById(R.id.Score);
        question = (TextView)findViewById(R.id.qNumber);
        question.setText("Question 1 of 10");
        score.setText("Score: 0");

        options[0] =(Button)findViewById(R.id.b1);
        options[1] = (Button)findViewById(R.id.b2);
        options[2] = (Button)findViewById(R.id.b3);
        options[3] = (Button)findViewById(R.id.b4);

        files = MainActivity.ac.getMap();
        setOfSongs = new ArrayList<String>(files.keySet());

        currQuestion = 1;

        newQuestion();
    }

    protected void onPause(){
        mediaPlayer.stop();
        c.cancel();
        finish();
        super.onPause();
    }

    protected void onStop(){
        mediaPlayer.stop();
        c.cancel();
        finish();
        super.onStop();
    }

    protected void questionFinished(boolean correct, String corrAns) {
        mediaPlayer.stop();
        c.cancel();
        AlertDialog.Builder alert = new AlertDialog.Builder(Playing.this);
        if(correct){
            Integer timeRemaining = Integer.parseInt((String)timer.getText());
            scoreNum+=timeRemaining;
            score.setText("Score: " + scoreNum);
            alert.setTitle("Correct!");
            alert.setMessage(String.format("%s was indeed the correct answer! You earned %d points!", corrAns, timeRemaining));
        }
        else{
            alert.setTitle("Incorrect");
            alert.setMessage(String.format("Sorry, the correct answer was %s.", corrAns));
        }
        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(currQuestion < 10){
                    currQuestion++;
                    question.setText(String.format("Question %d of 10", currQuestion));
                    newQuestion();
                }
                else{
                    HighScores.addScore(scoreNum);
                    finish();
                }
            }
        });
        AlertDialog a = alert.create();
        a.show();

    }

    protected void newQuestion(){
        //play song
        int songIndex = r.nextInt(setOfSongs.size());
        mediaPlayer = MediaPlayer.create(this, files.get(setOfSongs.get(songIndex)));
        final int buttonIndex = r.nextInt(4);
        options[buttonIndex].setText(setOfSongs.get(songIndex));
        options[buttonIndex].setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    questionFinished(true, (String)options[buttonIndex].getText());
                }
        });
        setOfSongs.remove(songIndex);
        ArrayList<String> tempSongSet = (ArrayList<String>)setOfSongs.clone();
        for (int i = (buttonIndex + 1) %4; i != buttonIndex; i= (i+1)%4){
            int si = r.nextInt(tempSongSet.size());
            options[i].setText(tempSongSet.get(si));
            tempSongSet.remove(si);
            options[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    questionFinished(false, (String)options[buttonIndex].getText());
                }
            });
        }


        int lastStart = mediaPlayer.getDuration() - 30000;
        mediaPlayer.seekTo(r.nextInt(lastStart));
        mediaPlayer.start();

        timer.setText("20");
        c = new CountDownTimer(21000, 1000){

            public void onTick(long millisUntilFinished){
                timer.setText(String.format("%d", millisUntilFinished / 1000));
            }

            public void onFinish() {
                timer.setText("0");
                questionFinished(false, (String)options[buttonIndex].getText());
            }
        };
        c.start();
    }

}
