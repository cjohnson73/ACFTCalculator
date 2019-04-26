package com.chancelot.acftcalculator.feature;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorFragment extends Fragment {

    private TextView sdres;
    private TextView ptres;
    private TextView pures;
    private TextView sdcres;
    private TextView ltres;
    private TextView runres;
    private TextView totalRes;
    private EditText sdin;
    private EditText ptin;
    private EditText puin;
    private EditText sdcin;
    private EditText sdcin2;
    private EditText ltin;
    private EditText runin;
    private EditText runin2;
    private EditText e[] = new EditText[8];
    private Event events[] = new Event[6];
    private TextView t[] = new TextView[6];
    private boolean created = false;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        created = true;

        sdres = (TextView) view.findViewById(R.id.sdres);
        ptres = (TextView) view.findViewById(R.id.ptres);
        pures = (TextView) view.findViewById(R.id.pures);
        sdcres = (TextView) view.findViewById(R.id.sdcres);
        ltres = (TextView) view.findViewById(R.id.ltres);
        runres = (TextView) view.findViewById(R.id.runres);
        totalRes = (TextView) view.findViewById(R.id.totalRes);
        sdin = (EditText) view.findViewById(R.id.sdin);
        ptin = (EditText) view.findViewById(R.id.ptin);
        puin = (EditText) view.findViewById(R.id.puin);
        sdcin = (EditText) view.findViewById(R.id.sdcin);
        sdcin2 = (EditText) view.findViewById(R.id.sdcin2);
        ltin = (EditText) view.findViewById(R.id.ltin);
        runin = (EditText) view.findViewById(R.id.runin);
        runin2 = (EditText) view.findViewById(R.id.runin2);

        e[0] = sdin;
        e[1] = ptin;
        e[2] = puin;
        e[3] = sdcin;
        e[4] = sdcin2;
        e[5] = ltin;
        e[6] = runin;
        e[7] = runin2;

        events[0] = new Event("sd");
        events[1] = new Event("pt");
        events[2] = new Event("pu");
        events[3] = new Event("sdc");
        events[4] = new Event("lt");
        events[5] = new Event("run");

        t[0] = sdres;
        t[1] = ptres;
        t[2] = pures;
        t[3] = sdcres;
        t[4] = ltres;
        t[5] = runres;

        for(int k = 0; k<e.length; k++) {
            final int l = k;
            e[l].setOnKeyListener(
                    new TextView.OnKeyListener() {
                        public boolean onKey(View v, int id, KeyEvent event) {
                            update();
                            return false;
                        }
                    }
            );
            e[l].setOnFocusChangeListener(
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
        String eText = "";
        for (int i = 0; i < events.length; i++) {
            final int j = i;
            if(i!=3 && i!=5 && i!=4){
                eText = e[j].getText().toString();
            }
            else if(i==3){
                eText = e[3].getText().toString() + ":" + e[4].getText().toString();
            }
            else if(i==4){
                eText = e[5].getText().toString();
            }
            else if(i==5){
                eText = e[6].getText().toString() + ":" + e[7].getText().toString();
            }
            events[j].convert(eText);
            if (events[j].pts != -1) {
                int c = scoreCat(events[j].pts);
                String cat = "";
                int cl = 0;
                if (c == 0) {
                    cat = " NO GO";
                    cl = Color.RED;
                } else if (c == 1) {
                    cat = " (M)";
                    cl = Color.BLACK;
                } else if (c == 2) {
                    cat = " (S)";
                    cl = Color.rgb(255, 200, 0);
                } else if (c == 3) {
                    cat = " (H)";
                    cl = Color.rgb(0, 200, 0);
                }
                t[j].setText("" + events[j].pts + cat);
                t[j].setTextColor(cl);
            } else
                t[j].setText("");
        }
        boolean anynegs = false;
        for (Event ev : events) {
            if (ev.pts == -1) {
                anynegs = true;
                break;
            }
        }
        if (!anynegs) {
            int c = 3;
            int total = 0;
            for (Event ev : events) {
                if (scoreCat(ev.pts) < c) {
                    c = scoreCat(ev.pts);
                }
                total += ev.pts;
            }
            String cat = "";
            int cl = 0;
            if (c == 0) {
                cat = " NO GO";
                cl = Color.RED;
            } else if (c == 1) {
                cat = " (M)";
                cl = Color.BLACK;
            } else if (c == 2) {
                cat = " (S)";
                cl = Color.rgb(255, 200, 0);
            } else if (c == 3) {
                cat = " (H)";
                cl = Color.rgb(0, 200, 0);
            }
            totalRes.setText("" + total + cat);
            totalRes.setTextColor(cl);
        } else
            totalRes.setText("");
    }
    private int scoreCat(double score) { // 0 = moderate physical demand unit/mos (army min) 1 = <- significant 2 = <- heavy 3 = fail
        if(score >= 70)
            return 3;
        else if(score >= 65)
            return 2;
        else if(score >= 60)
            return 1;
        else
            return 0;
    }
}