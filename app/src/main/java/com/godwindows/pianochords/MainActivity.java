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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView chordText;
    private Button resetButton;
    private Button startButton;
    private RadioButton smallValueRadio ;
    private RadioButton mediumValueRadio  ;
    private RadioButton largeValueRadio  ;
    private RadioGroup secondsRadioGroup;
    private final int SMALL_DURATION = 5000;
    private final int MEDIUM_DURATION = 10000;
    private final int LARGE_DURATION = 15000;
    private int delay ;
    private  boolean[] running = {true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chordText =  findViewById(R.id.chordText);
        resetButton = (Button) findViewById(R.id.resetButton);
        startButton = (Button) findViewById(R.id.startButton);
        smallValueRadio = findViewById(R.id.smallValueRadio);
        mediumValueRadio = findViewById(R.id.mediumValueRadio);
        largeValueRadio = findViewById(R.id.largeValueRadio);
        secondsRadioGroup = (RadioGroup) findViewById(R.id.radioGroup) ;
        initializeComponents();

    }

    protected void swipeChords(int timeGap){
        Handler myHandler = new Handler(Looper.getMainLooper());
        ArrayList<Chord> chords = Chord.getChords(true, true);
        chordText.setText(chordToText(chords.get(0)));
        final int[] i={1};
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < chords.size() && running[0]) {
                            chordText.setText(chordToText(chords.get(i[0])));
                            i[0]++;
                            myHandler.postDelayed(this, timeGap);
                        }// TODO: Le défilement n'affiche pas le dernier élément
                        if (i[0]==chords.size()){
                            resetButton.callOnClick();
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
                        disableRadios();
                        swipeChords(delay);
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
                        enableRadios();
                        running[0] = false;
                    }
                }
        );
        resetButton.setEnabled(false);


        secondsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.smallValueRadio && smallValueRadio.isChecked() ){
                    delay = SMALL_DURATION;
                }
                if (checkedId==R.id.mediumValueRadio && mediumValueRadio.isChecked() ){
                    delay = MEDIUM_DURATION;
                }
                if (checkedId==R.id.largeValueRadio && largeValueRadio.isChecked() ){
                    delay = LARGE_DURATION;
                }

                Toast.makeText(MainActivity.this, ""+delay, Toast.LENGTH_SHORT).show();
            }
        });

        smallValueRadio.setChecked(true);

    }

    protected Spanned chordToText(Chord myChord){
        String exponant  = "";
        String subscriptMinor  = "";
        if(myChord.isFlat()) exponant = "<sup><small>b<small></sup>";
        if(myChord.isSharp()) exponant = "<sup>#</sup>";
        if (myChord.isMinor()) subscriptMinor  = "ₘ";
        return Html.fromHtml(myChord.getValue()+exponant+subscriptMinor,0);
    }

    protected void disableRadios(){
        changeRadiosState(false);
    }
    protected void enableRadios(){
        changeRadiosState(true);
    }

    protected void changeRadiosState(boolean state) {
        smallValueRadio.setEnabled(state);
        mediumValueRadio.setEnabled(state);
        largeValueRadio.setEnabled(state);
    }
}