package com.chancelot.acftcalculator.feature;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private FloatingActionButton fabBack;
    private ArrayList<Fragment> frags = new ArrayList<>();
    private FragmentTransaction ft;
    private FragmentManager fm;
    private int fragNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        fragNum = 0;
        fab = findViewById(R.id.fab);
        fabBack = findViewById(R.id.fabBack);

        frags.add(new CalculatorFragment());
        frags.add(new WeightFragment());
        frags.add(new PercentFragment());

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment, frags.get(fragNum));
        ft.commit();

        fab.setOnClickListener(
                new FloatingActionButton.OnClickListener(){
                    public void onClick(View view){
                        fragNum++;
                        fragNum %= frags.size();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment, frags.get(fragNum));
                        ft.commit();
                    }
                }
        );
        fabBack.setOnClickListener(
                new FloatingActionButton.OnClickListener(){
                    public void onClick(View view){
                        fragNum--;
                        fragNum += (fragNum<0?frags.size():0);
                        fragNum %= frags.size();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment, frags.get(fragNum));
                        ft.commit();
                    }
                }
        );

    }
}
