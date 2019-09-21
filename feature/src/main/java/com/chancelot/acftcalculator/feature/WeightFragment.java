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

public class WeightFragment extends Fragment {

    private int age = -1;
    private int sex = -1;
    private int height = -1;
    private int weight = -1;
    private int agegroup = -1;
    private TextView result;
    private TextView sexres;
    private TextView weightres;
    private EditText sexin;
    private EditText agein;
    private EditText heightin;
    private EditText weightin;
    private EditText e[] = new EditText[4];
    private boolean created = false;
    private int maxHeight = 80;
    private int minHeight = 58;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        created = true;

        result = (TextView) view.findViewById(R.id.result);
        sexres = (TextView) view.findViewById(R.id.sexres);
        weightres = (TextView) view.findViewById(R.id.weightres);
        weightres.setTextColor(Color.rgb(150, 150, 150));
        sexin = (EditText) view.findViewById(R.id.sexin);
        sexin.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        agein = (EditText) view.findViewById(R.id.agein);
        heightin = (EditText) view.findViewById(R.id.heightin);
        weightin = (EditText) view.findViewById(R.id.weightin);
        e[0] = sexin;
        e[1] = agein;
        e[2] = heightin;
        e[3] = weightin;

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
            } else if (text.startsWith("f")) {
                sex = 4;
                sexres.setText("");
            } else {
                sexres.setText("Invalid input");
                sexres.setTextColor(Color.RED);
                sex = -1;
            }
        }

        text = heightin.getText().toString();
        if(text != null && !text.isEmpty()) {
            height = Integer.parseInt(text);
            if(height<minHeight)
                height = -2;
        }
        else
            height = -1;

        text = weightin.getText().toString();
        if(text != null && !text.isEmpty())
            weight = Integer.parseInt(text);
        else
            weight = -1;

        if(height == -2) {
            weightres.setText("min: N/A max: N/A");
        } else if(agegroup != -1 && sex !=-1 && height !=-1){
            weightres.setText("min:\t" + minWeights[(height>maxHeight?maxHeight-minHeight:height-minHeight)] + " lbs\nmax:\t" + (maxWeights[(height>maxHeight?maxHeight:height)-minHeight][agegroup+sex] + (height>maxHeight?(sex==0?6:5)*(height-maxHeight):0)) + " lbs");
        } else {
            weightres.setText("");
        }

        if(height == -2) {
            result.setText("N/A");
            result.setTextColor(Color.BLACK);
        } else if(agegroup != -1 && sex !=-1 && height !=-1 && weight !=-1){
            if(maxWeights[(height>maxHeight?maxHeight:height)-minHeight][agegroup+sex] + (height>maxHeight?(sex==0?6:5)*(height-maxHeight):0) < weight) {
                result.setText("NO GO: Over");
                result.setTextColor(Color.RED);
            } else if(minWeights[(height>maxHeight?maxHeight-minHeight:height-minHeight)] > weight){
                result.setText("NO GO: Under");
                result.setTextColor(Color.RED);
            } else {
                result.setText("GO");
                result.setTextColor(Color.rgb(0, 200, 0));
            }
        } else {
            result.setText("");
        }
    }
    private int ageGroups[] = {17, 21, 28, 40};
    private int maxWeights[][] = {
            {0, 0, 0, 0, 119, 121, 122, 124},
            {0, 0, 0, 0, 124, 125, 126, 128},
            {132, 136, 139, 141, 128, 129, 131, 133},
            {136, 140, 144, 146, 132, 134, 135, 137},
            {141, 144, 148, 150, 136, 138, 140, 142},
            {145, 149, 153, 155, 141, 143, 144, 146},
            {150, 154, 158, 160, 145, 147, 149, 151},
            {155, 159, 163, 165, 150, 152, 154, 156},
            {160, 163, 168, 170, 155, 156, 158, 161},
            {165, 169, 174, 176, 159, 161, 163, 166},
            {170, 174, 179, 181, 164, 166, 168, 171},
            {175, 179, 184, 186, 169, 171, 173, 176},
            {180, 185, 189, 192, 174, 176, 178, 181},
            {185, 189, 194, 197, 179, 181, 183, 186},
            {190, 195, 200, 203, 184, 186, 188, 191},
            {195, 200, 205, 208, 189, 191, 194, 197},
            {201, 206, 211, 214, 194, 197, 199, 202},
            {206, 212, 217, 220, 200, 202, 204, 208},
            {212, 217, 223, 226, 205, 207, 210, 213},
            {218, 223, 229, 232, 210, 213, 215, 219},
            {223, 229, 235, 238, 216, 218, 221, 225},
            {229, 235, 241, 244, 221, 224, 227, 230},
            {234, 240, 247, 250, 227, 230, 233, 236}
    };
    private int minWeights[] = {91, 94, 97, 100, 104, 107, 110, 114, 117, 121, 125, 128, 132, 136, 140, 144, 148, 152, 156, 160, 164, 168, 173};
}
