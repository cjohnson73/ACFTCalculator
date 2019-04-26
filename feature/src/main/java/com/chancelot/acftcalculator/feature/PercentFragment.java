package com.chancelot.acftcalculator.feature;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

public class PercentFragment extends Fragment {

    private boolean created = false;

    private int sex = -1;
    private int age = -1;
    private int neck = -1;
    private int waist = -1;
    private int hip = -1;
    private int height = -1;
    private int agegroup = -1;
    private int bfp = -1;
    private TextView result;
    private TextView sexres;
    private TextView maxres;
    private EditText sexin;
    private EditText agein;
    private EditText heightin;
    private EditText neckin;
    private EditText waistin;
    private EditText hipin;
    private EditText e[] = new EditText[6];

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_percent, container, false);

        created = true;

        result = (TextView) view.findViewById(R.id.result);
        sexres = (TextView) view.findViewById(R.id.sexres);
        maxres = (TextView) view.findViewById(R.id.maxres);
        maxres.setTextColor(Color.rgb(150, 150, 150));
        sexin = (EditText) view.findViewById(R.id.sexin);
        sexin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        agein = (EditText) view.findViewById(R.id.agein);
        heightin = (EditText) view.findViewById(R.id.heightin);
        neckin = (EditText) view.findViewById(R.id.neckin);
        waistin = (EditText) view.findViewById(R.id.waistin);
        hipin = (EditText) view.findViewById(R.id.hipin);
        e[0] = sexin;
        e[1] = agein;
        e[2] = heightin;
        e[3] = neckin;
        e[4] = waistin;
        e[5] = hipin;

        for(EditText et: e){
            et.setOnKeyListener(
                    new TextView.OnKeyListener() {
                        public boolean onKey(View v, int id, KeyEvent event) {
                            update();
                            return false;
                        }
                    }
            );

            et.setOnFocusChangeListener(
                    new TextView.OnFocusChangeListener() {
                        public void onFocusChange(View v, boolean b) {
                            update();
                        }
                    }
            );
        }

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(created)
            update();
    }

    private void update(){
        String text = agein.getText().toString();
        if (text != null && !text.isEmpty()) {
            age = Integer.parseInt(text);
            for (int i = ageGroups.length - 1; i >= 0; i--) {
                if (age >= ageGroups[i]) {
                    agegroup = i;
                    break;
                }
            }
        }
        else {
            age = -1;
            agegroup = -1;
        }

        text = sexin.getText().toString().toLowerCase();
        if(text != null && !text.isEmpty()) {
            if (text.startsWith("m")) {
                sex = 0;
                sexres.setText("");
                hipin.setHint("n/a");
            } else if (text.startsWith("f")) {
                sex = 4;
                sexres.setText("");
                hipin.setHint("in");
            } else {
                sexres.setText("Invalid input");
                sexres.setTextColor(Color.RED);
                sex = -1;
            }
        }

        text = heightin.getText().toString();
        if(text != null && !text.isEmpty())
            height = Integer.parseInt(text);
        else
            height = -1;

        text = neckin.getText().toString();
        if(text != null && !text.isEmpty())
            neck = Integer.parseInt(text);
        else
            neck = -1;

        text = waistin.getText().toString();
        if(text != null && !text.isEmpty())
            waist = Integer.parseInt(text);
        else
            waist = -1;

        text = hipin.getText().toString();
        if(text != null && !text.isEmpty())
            hip = Integer.parseInt(text);
        else
            hip = -1;

        if(agegroup !=-1 && sex !=-1) {
            maxres.setText("max: " + maxPercents[agegroup+sex] + "%");
        } else {
            maxres.setText("");
        }

        if(agegroup != -1 && sex !=-1 && height !=-1 && neck !=-1 && waist!=-1 && (hip!=-1 || sex==0)){
            double logH = Math.log10(height);
            double logF = Math.log10(sex==0?(waist-neck):(waist+hip-neck));
            double roundLogH = round(logH, 2);
            double roundLogF = round(logF, 2);
            double firstTerm = (sex==0?86.010:163.205)*roundLogF;
            double secTerm = (sex==0?70.041:97.684)*roundLogH;
            double roundFTerm = round(firstTerm, 2);
            double roundSTerm = round(secTerm, 2);
            double cons = (sex==0?36.76:-78.387);
            double end = roundFTerm-roundSTerm+cons;
            bfp = (int)round(end, 0);
            bfp = bfp<0?0:(bfp>100?100:bfp);
            if(bfp>maxPercents[agegroup+sex]){
                result.setText(bfp + "% NO GO");
                result.setTextColor(Color.RED);
            } else {
                result.setText(bfp + "% GO");
                result.setTextColor(Color.rgb(0, 200, 0));
            }
        } else {
            result.setText("");
        }

    }
    private double round(double value, int places){
        if(places<0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value *= factor;
        long tmp = Math.round(value);
        return (double)tmp/factor;
    }
    private int ageGroups[] = {17, 21, 28, 40};
    private int maxPercents[] = {20, 22, 24, 26, 30, 32, 34, 36};
}