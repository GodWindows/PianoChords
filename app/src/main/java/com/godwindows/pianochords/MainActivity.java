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
    /* The chord display on the screen is done using three TextViews:
     - a main one to write the value of the chord
     - an exponant to write the potential '#' or 'b' of the chord
     - a subscript to write the 'm' in case the chord is minor
     */

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
    private Handler myHandler;

    /*The following values can be changed if you want to custom the delay duration between two chords.
    Just make sure to change the 'string.xml' file accordingly on the firstValue, secondValue and thirdValue keys*/
    private final int SMALL_DURATION = 5000; //
    private final int MEDIUM_DURATION = 10000;
    private final int LARGE_DURATION = 15000;
    private int delay ;
    private  boolean[] running = {true};//This variable will be signal to know if the app can continue swiping chords or if the app has to stop swiping( when "reset" is pressed for example)

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
    /* This method will go through all the needed chords and put them on the screen, one after another */
    protected void swipeChords(int timeGap){
        myHandler = new Handler(Looper.getMainLooper());
        ArrayList<Chord> chords = Chord.getChords(minorSwitch.isChecked(), flatAndSharpSwitch.isChecked()); //Get all the needed chords from the Chord enum and put them in an chord array
        updateChordText(chords.get(0));//Put the first chord on the screen without any delay
        final int[] i={1};// an index variable to browse the chord array
        myHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if (i[0] < chords.size() && running[0]) {//If the index is still in the chord array's bounds and the "reset" button has not been pressed
                            updateChordText(chords.get(i[0]));//Put the next chord on the screen
                            i[0]++;
                            myHandler.postDelayed(this, timeGap);// Re-launch the runnable for the next index in the chord array
                        }else if (i[0]==chords.size()){ // If the chord array is entirely read
                            resetButton.callOnClick(); //stop the swiping
                        }
                    }
                },
                timeGap
        );
    }


    protected void initializeComponents(){
        /*Allow minors flats and sharps by default*/
        minorSwitch.setChecked(true);
        flatAndSharpSwitch.setChecked(true);

        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        running[0] = true; //Allow the swiping
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
                        myHandler.removeCallbacksAndMessages(null);
                    }
                }
        );
        resetButton.setEnabled(false); //Initialize the app with the reset button disabled


        secondsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            /* Affect the matching duration delay to each radio button */
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
            }
        });

        smallValueRadio.setChecked(true);// Select the small duration by default

    }

    /*Clear the chord from the screen*/
    private void clearChordText() {
        chordMainText.setText("");
        chordExponantText.setText("");
        chordSubscriptText.setText("");
    }

    /* Write a new chord on the screen */
    protected void updateChordText(Chord myChord){
        String exponant  = "";
        String subscript  = "";
        String main = "";
        if(myChord.isFlat()) exponant = "b";
        if(myChord.isSharp()) exponant = "#";
        if (myChord.isMinor()) subscript  = "m";
        if (traductionSwitch.isChecked()){//Select the french version of the chord this the traduction is allowed
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

    protected void changeOptionsState(boolean state) {//disable or enable all the switches and radio buttons
        smallValueRadio.setEnabled(state);
        mediumValueRadio.setEnabled(state);
        largeValueRadio.setEnabled(state);
        minorSwitch.setEnabled(state);
        flatAndSharpSwitch.setEnabled(state);
        traductionSwitch.setEnabled(state);
    }
}