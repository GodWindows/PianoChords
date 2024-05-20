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
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView chordMainText;
    private TextView chordExponantText;
    private TextView chordSubscriptText;
    private Button resetButton;
    private Button startButton;
    private RadioButton smallValueRadio ;
    private RadioButton mediumValueRadio  ;
    private RadioButton largeValueRadio  ;
    private RadioGroup secondsRadioGroup;
    private Switch minorSwitch;
    private Switch flatAndSharpSwitch;
    private Switch traductionSwitch;
    private final int SMALL_DURATION = 1000;
    private final int MEDIUM_DURATION = 10000;
    private final int LARGE_DURATION = 15000;
    private int delay ;
    private  boolean[] running = {true};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chordMainText =  findViewById(R.id.chordMainText);
        chordExponantText =  findViewById(R.id.chordExponantText);
        chordSubscriptText =  findViewById(R.id.chordSubscriptText);
        resetButton = (Button) findViewById(R.id.resetButton);
        startButton = (Button) findViewById(R.id.startButton);
        smallValueRadio = findViewById(R.id.smallValueRadio);
        mediumValueRadio = findViewById(R.id.mediumValueRadio);
        largeValueRadio = findViewById(R.id.largeValueRadio);
        secondsRadioGroup = (RadioGroup) findViewById(R.id.radioGroup) ;
        minorSwitch = (Switch) findViewById(R.id.minorSwitch) ;
        flatAndSharpSwitch = (Switch) findViewById(R.id.flatAndSharpSwitch) ;
        traductionSwitch = (Switch) findViewById(R.id.traductionSwitch) ;
        initializeComponents();

    }

    protected void swipeChords(int timeGap){
        Handler myHandler = new Handler(Looper.getMainLooper());
        ArrayList<Chord> chords = Chord.getChords(minorSwitch.isChecked(), flatAndSharpSwitch.isChecked());
        updateChordText(chords.get(0));
        final int[] i={1};
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < chords.size() && running[0]) {
                            updateChordText(chords.get(i[0]));
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

        chordMainText.setTextSize(70);
        chordExponantText.setTextSize(35);
        chordSubscriptText.setTextSize(35);
        minorSwitch.setChecked(true);
        flatAndSharpSwitch.setChecked(true);

        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        running[0] = true;
                        startButton.setEnabled(false);
                        resetButton.setEnabled(true);
                        disableOptions();
                        swipeChords(delay);
                    }
                }
        );

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearChordText();
                        resetButton.setEnabled(false);
                        startButton.setEnabled(true);
                        enableOptions();
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

    private void clearChordText() {
        chordMainText.setText("");
        chordExponantText.setText("");
        chordSubscriptText.setText("");
    }

    protected void updateChordText(Chord myChord){
        String exponant  = "";
        String subscript  = "";
        String main = "";
        if(myChord.isFlat()) exponant = "b";
        if(myChord.isSharp()) exponant = "#";
        if (myChord.isMinor()) subscript  = "m";
        if (traductionSwitch.isChecked()){
            main = Chord.translate(myChord.getValue());
        }
        else {
            main = myChord.getValue();
        }
        chordMainText.setText(main);
        chordExponantText.setText(exponant);
        chordSubscriptText.setText(subscript);

    }

    protected void disableOptions(){
        changeOptionsState(false);
    }
    protected void enableOptions(){
        changeOptionsState(true);
    }

    protected void changeOptionsState(boolean state) {
        smallValueRadio.setEnabled(state);
        mediumValueRadio.setEnabled(state);
        largeValueRadio.setEnabled(state);
        minorSwitch.setEnabled(state);
        flatAndSharpSwitch.setEnabled(state);
        traductionSwitch.setEnabled(state);
    }
}