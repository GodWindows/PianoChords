package com.godwindows.pianochords;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        initializeComponents();

    }

    protected void swipeChords(int timeGap){
        Handler myHandler = new Handler(Looper.getMainLooper());
        chordText.setText(chordToText(Chord.getAllChords().get(0)));
        final int[] i={1};
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < Chord.getAllChords().size() && running[0]) {
                            /*SpannableStringBuilder cs = new SpannableStringBuilder("X3 + X#m");
                            cs.setSpan(new SuperscriptSpan(), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(0.75f), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new SuperscriptSpan(), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(0.75f), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new SubscriptSpan(), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(0.75f), 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            chordText.setText(cs);*/
                            chordText.setText(chordToText(Chord.getAllChords().get(i[0])));
                            i[0]++;
                                myHandler.postDelayed(this, timeGap);
                        }
                        if (i[0]==20){
                            chordText.setText("");
                        }
                    }
                },
                timeGap
        );
    }


    protected void initializeComponents(){

        chordText.setTextSize(70);

        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        running[0] = true;
                        startButton.setEnabled(false);
                        resetButton.setEnabled(true);
                        swipeChords(1000);
                        Toast.makeText(MainActivity.this, "Début", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chordText.setText("");
                        resetButton.setEnabled(false);
                        startButton.setEnabled(true);
                        running[0] = false;
                        Toast.makeText(MainActivity.this, "Stop", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    protected Spanned chordToText(Chord myChord){
        String exponant  = "";
        String subscriptMinor  = "";
        if(myChord.isFlat()) exponant = "<sup><small>b<small></sup>";
        if(myChord.isSharp()) exponant = "<sup>#</sup>";
        if (myChord.isMinor()) subscriptMinor  = "ₘ";
        return Html.fromHtml(myChord.getValue()+exponant+subscriptMinor,0);
    }
}