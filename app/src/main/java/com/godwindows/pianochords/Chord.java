package com.godwindows.pianochords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public enum Chord {
    //DO
    C_MAJOR("C", false, false, false),
    C_MINOR("C", true, false, false),
    C_MAJOR_SHARP("C", false, false, true),
    C_MINOR_SHARP("C", true, false, true),
    //RE
    D_MAJOR_FLAT("D", false, true, false),
    D_MINOR_FLAT("D", true, true, false),
    D_MAJOR("D", false, false, false),
    D_MINOR("D", true, false, false),
    D_MAJOR_SHARP("D", false, false, true),
    D_MINOR_SHARP("D", true, false, true),
    //MI
    E_MAJOR_FLAT("E", false, true, false),
    E_MINOR_FLAT("E", true, true, false),
    E_MAJOR("E", false, false, false),
    E_MINOR("E", true, false, false),
    //FA
    F_MAJOR("F", false, false, false),
    F_MINOR("F", true, false, false),
    F_MAJOR_SHARP("F", false, false, true),
    F_MINOR_SHARP("F", true, false, true),
    //SOL
    G_MAJOR_FLAT("G", false, true, false),
    G_MINOR_FLAT("G", true, true, false),
    G_MAJOR("G", false, false, false),
    G_MINOR("G", true, false, false),
    G_MINOR_SHARP("G", true, false, true),
    G_MAJOR_SHARP("G", false, false, true),
    //LA
    A_MINOR_FLAT("A", true, true, false),
    A_MAJOR_FLAT("A", false, true, false),
    A_MAJOR("A", false, false, false),
    A_MINOR("A", true, false, false),
    A_MAJOR_SHARP("A", false, false, true),
    A_MINOR_SHARP("A", true, false, true),
    //SI
    B_MAJOR_FLAT("B", false, true, false),
    B_MINOR_FLAT("B", true, true, false),
    B_MAJOR("B", false, false, false),
    B_MINOR("B", true, false, false);
    private final String value;
    private final boolean isMinor;
    private final boolean isFlat;
    private final boolean isSharp;
    Chord(String value, boolean isMinor, boolean isFlat, boolean isSharp) {
        this.value = value;
        this.isMinor = isMinor;
        this.isFlat = isFlat;
        this.isSharp = isSharp;
    }
    public String getValue() {
        return value;
    }
    public boolean isMinor() {
        return isMinor;
    }
    public boolean isFlat() {
        return isFlat;
    }
    public boolean isSharp() {
        return isSharp;
    }

    public static ArrayList<Chord> getChords(boolean withMinors, boolean withFlatAndSharp){ //Return all the chords which match the parameters in arguments
        ArrayList<Chord> allChords = new ArrayList<>();
        //add all the basic chords
        allChords.add(C_MAJOR);
        allChords.add(D_MAJOR);
        allChords.add(E_MAJOR);
        allChords.add(F_MAJOR);
        allChords.add(G_MAJOR);
        allChords.add(A_MAJOR);
        allChords.add(B_MAJOR);
        if (withMinors && withFlatAndSharp){ // add the flat/sharp and minor chords
            allChords.add(C_MINOR_SHARP);
            allChords.add(D_MINOR_FLAT);
            allChords.add(E_MINOR_FLAT);
            allChords.add(D_MINOR_SHARP);
            allChords.add(F_MINOR_SHARP);
            allChords.add(G_MINOR_FLAT);
            allChords.add(A_MINOR_FLAT);
            allChords.add(G_MINOR_SHARP);
            allChords.add(A_MINOR_SHARP);
            allChords.add(B_MINOR_FLAT);
        }
        if (withMinors){ // add the minor chords
            allChords.add(C_MINOR);
            allChords.add(D_MINOR);
            allChords.add(E_MINOR);
            allChords.add(G_MINOR);
            allChords.add(F_MINOR);
            allChords.add(A_MINOR);
            allChords.add(B_MINOR);
        }
        if (withFlatAndSharp){ // add the flat/sharp chords
            allChords.add(C_MAJOR_SHARP);
            allChords.add(D_MAJOR_FLAT);
            allChords.add(D_MAJOR_SHARP);
            allChords.add(E_MAJOR_FLAT);
            allChords.add(F_MAJOR_SHARP);
            allChords.add(G_MAJOR_FLAT);
            allChords.add(G_MAJOR_SHARP);
            allChords.add(A_MAJOR_FLAT);
            allChords.add(A_MAJOR_SHARP);
            allChords.add(B_MAJOR_FLAT);
        }
        Collections.shuffle(allChords);//Shuffle the chords in order to have a different order at each call
        return allChords;
    }

    public static String translate(String value){
        final HashMap<String, String> internationalToFrench = new HashMap<>();
        internationalToFrench.put("C", "Do");
        internationalToFrench.put("D", "Ré");
        internationalToFrench.put("E", "Mi");
        internationalToFrench.put("F", "Fa");
        internationalToFrench.put("G", "Sol");
        internationalToFrench.put("A", "La");
        internationalToFrench.put("B", "Si");
        String traduction ;
        if (internationalToFrench.containsKey(value)){
            traduction = internationalToFrench.get(value);
        }
        else {
            traduction = "XX";
        }

        return traduction;
    }
}