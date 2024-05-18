package com.godwindows.pianochords;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView chordText;
    private Button resetButton;
    private Button startButton;
    private  boolean[] running = {true};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chordText =  findViewById(R.id.chordText);
        resetButton = (Button) findViewById(R.id.resetButton);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        running[0] = true;
                        startButton.setActivated(false);
                        resetButton.setActivated(true);
                        swipeChords(2000);
                        Toast.makeText(MainActivity.this, "Début", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetButton.setActivated(false);
                        startButton.setActivated(true);
                        running[0] = false;
                        Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    protected void swipeChords(int timeGap){
        chordText.setText("Chord n° 0");
        Handler myHandler = new Handler(Looper.getMainLooper());
        final int[] i={1};
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < 20 && running[0]) {
                            chordText.setText("Chord n° " + i[0]);
                            i[0]++;
                                myHandler.postDelayed(this, timeGap);
                        }
                    }
                },
                timeGap
        );
    }
}