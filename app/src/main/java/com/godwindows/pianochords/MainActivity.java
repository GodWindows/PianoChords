package com.godwindows.pianochords;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView chordText;
    private  boolean[] running = {true};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chordText =  findViewById(R.id.chordText);
        swipeChords(5000);
        Handler myHandler = new Handler(Looper.getMainLooper());
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                swipeChords(5000);
            }
        });
    }

    protected void swipeChords(int timeGap){
        chordText.setText("Chord n° 0");
        Handler myHandler = new Handler(Looper.getMainLooper());
        final int[] i={1};
        final boolean[] isAlreadyRunning = {false};
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < 5) {
                            chordText.setText("Chord n° " + i[0]);
                            i[0]++;
                            if(running[0]){
                                myHandler.postDelayed(this, timeGap);
                            }
                        }
                    }
                },
                timeGap
        );
    }
}