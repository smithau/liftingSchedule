package com.example.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity
{
    private DatabaseHelper dbHelper;
    private MainActivity main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lift);
        configureSaveBtn();
        configureCancelBtn();
        configureNameTxt();
        configureSetTxt();
        configureRepsTxt();
        configureNoteTxt();
        dbHelper = new DatabaseHelper(this);
        main = new MainActivity();
    }

    private void configureRepsTxt()
    {
        TextView txt = (TextView) findViewById(R.id.numRepTxt);
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ((TextView)v).getText().toString().equals("Number of Reps"))
                    ((TextView) v).setText("");
                if(!hasFocus && ((TextView)v).getText().toString().equals(""))
                    ((TextView) v).setText("Number of Reps");
            }
        });
    }

    private void configureNoteTxt()
    {
        TextView txt = (TextView) findViewById(R.id.noteTxt);
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ((TextView)v).getText().toString().equals("Notes"))
                    ((TextView) v).setText("");
                if(!hasFocus && ((TextView)v).getText().toString().equals(""))
                    ((TextView) v).setText("Notes");
            }
        });
    }

    private void configureSetTxt()
    {
        TextView txt = (TextView) findViewById(R.id.numSetTxt);
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ((TextView)v).getText().toString().equals("Number of Sets"))
                    ((TextView) v).setText("");
                if(!hasFocus && ((TextView)v).getText().toString().equals(""))
                    ((TextView) v).setText("Number of Sets");
            }
        });
    }

    private void configureNameTxt()
    {
        TextView txt = (TextView) findViewById(R.id.newLiftTxt);
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && ((TextView)v).getText().toString().equals("Lift Name"))
                    ((TextView) v).setText("");
                if(!hasFocus && ((TextView)v).getText().toString().equals(""))
                    ((TextView) v).setText("Lift Name");
            }
        });

    }

    private void configureCancelBtn()
    {
        Button cancel = (Button) findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configureSaveBtn() {
        Button add = (Button) findViewById(R.id.saveBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                finish();
            }
        });
    }

    public void addData()
    {
        boolean insert;

        RadioGroup rg = (RadioGroup) findViewById(R.id.dayGroup_add);
        String name = ((TextView)findViewById(R.id.newLiftTxt)).getText().toString();
        String reps = ((TextView)findViewById(R.id.numRepTxt)).getText().toString();
        String sets = ((TextView)findViewById(R.id.numSetTxt)).getText().toString();
        String day = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();


        insert = dbHelper.addData(name , day, sets, reps);

        if (!insert)
            toastMessage("Error");
        else
            toastMessage("Saved Properly");
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    public void enterName(View v)
    {

    }

    public void leaveName(View v)
    {
        EditText txt = findViewById(R.id.newLiftTxt);
        if(txt.toString().equals(""))
            txt.setText("Lift Name", TextView.BufferType.EDITABLE);
    }
}
