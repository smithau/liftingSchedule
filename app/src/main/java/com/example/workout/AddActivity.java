package com.example.workout;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddActivity extends AppCompatActivity
{
    private static final String ID_INDEX = "ID Index";
    private static final int DB_ID = 0;
    private static final int DB_LIFT = 1;
    private static final int DB_DAY = 2;
    private static final int DB_SETS = 3;
    private static final int DB_REPS = 4;
    private static final int DB_WEIGHT = 5;
    private static final int DB_NOTES = 7;
    private String oldLift;
    private int oldSet;
    private int oldRep;
    private int oldDay;
    private String oldNote;
    private int oldWeight;
    private DatabaseHelper dbHelper;
    private boolean edit;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.add_lift);
        index = Integer.parseInt(getIntent().getStringExtra(ID_INDEX));
        edit = index != -1;
        if(edit)
        {
            configureEdit();
        }
        else
            selectDay(getIntent().getIntExtra("day", 0));
        configureSaveBtn();
        configureCancelBtn();
        configureNameTxt();
        configureSetTxt();
        configureRepsTxt();
        configureNoteTxt();
        configureWeightTxt();
    }

    /**
     * This sets the UI up for being edited instead of adding a new lift
     */
    private void configureEdit()
    {
        Cursor data = dbHelper.getData();
        TextView weight = (TextView) findViewById(R.id.newWeightTxt);
        TextView rep = (TextView) findViewById(R.id.numRepTxt);
        TextView note = (TextView) findViewById(R.id.noteTxt);
        TextView set = (TextView) findViewById(R.id.numSetTxt);
        TextView name = (TextView) findViewById(R.id.newLiftTxt);
        while (data.moveToNext())
        {
            if (Integer.parseInt(data.getString(DB_ID)) == index)
            {
                oldWeight = data.getInt(DB_WEIGHT);
                oldLift = data.getString(DB_LIFT);
                oldNote = data.getString(DB_NOTES);
                oldRep = data.getInt(DB_REPS);
                oldSet = data.getInt(DB_SETS);
                oldDay = data.getInt(DB_DAY);
                weight.setText(oldWeight + "");
                rep.setText(oldRep + "");
                set.setText(oldSet + "");
                name.setText(oldLift);
                note.setText(oldNote);
                selectDay(oldDay);
            }
        }
        data.close();
    }

    /**
     * Selects the appropriate button for the day
     */
    private void selectDay(int day)
    {
        RadioGroup rg = (RadioGroup) findViewById(R.id.dayGroup_add);
        switch (day) {
            case 0:
                rg.check(R.id.sunBtn);
                break;
            case 1:
                rg.check(R.id.monBtn);
                break;
            case 2:
                rg.check(R.id.tueBtn);
                break;
            case 3:
                rg.check(R.id.wedBtn);
                break;
            case 4:
                rg.check(R.id.thursBtn);
                break;
            case 5:
                rg.check(R.id.friBtn);
                break;
            case 6:
                rg.check(R.id.satBtn);
                break;
        }
    }

    /**
     * Clears and repopulates the text field for Weight
     */
    private void configureWeightTxt()
    {
        TextView txt = (TextView) findViewById(R.id.newWeightTxt);
        txt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus && ((TextView) v).getText().toString().equals("Weight"))
                        ((TextView) v).setText("");
                if (!hasFocus && ((TextView) v).getText().toString().equals(""))
                        ((TextView) v).setText("Weight");

            }
        });
    }

    /**
     * Clears and repopulates the text field for Reps
     */
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

    /**
     * Clears and repopulates the text field for Notes
     */
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

    /**
     * Clears and repopulates the text field for Sets
     */
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

    /**
     * Clears and repopulates the text field for Name
     */
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

    /**
     * Return to the main screen without saving
     */
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

    /**
     * Return to the main screen with saving
     */
    private void configureSaveBtn() {
        final Button add = (Button) findViewById(R.id.saveBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit && addData())
                    finish();
                else if (editData())
                    finish();
            }
        });
    }

    /**
     * Updates a lift in the database
     * @return False if there is an error
     */
    private boolean editData()
    {
        try {
            RadioGroup rg = (RadioGroup) findViewById(R.id.dayGroup_add);
            String name = ((TextView) findViewById(R.id.newLiftTxt)).getText().toString();
            int reps = Integer.parseInt(((TextView) findViewById(R.id.numRepTxt)).getText().toString());
            int sets = Integer.parseInt(((TextView) findViewById(R.id.numSetTxt)).getText().toString());
            int weight = Integer.parseInt(((TextView) findViewById(R.id.newWeightTxt)).getText().toString());
            String selected = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
            int day = getDayID(selected);
            String notes = ((TextView) findViewById(R.id.noteTxt)).getText().toString();
            dbHelper.editDay(day, oldDay, index);
            dbHelper.editLift(name, oldLift, index);
            dbHelper.editNotes(notes, oldNote, index);
            dbHelper.editRep(reps, oldRep, index);
            dbHelper.editSets(sets, oldSet, index);
            dbHelper.editWeight(weight, oldWeight, index);
            return true;
        }
        catch (Exception x) {
            toastMessage("Error saving data");
            return false;
        }
    }

    /**
     * Adds a new lift to the database
     * @return False if there is an error adding to the database
     */
    public boolean addData()
    {
            try {
                RadioGroup rg = (RadioGroup) findViewById(R.id.dayGroup_add);
                String name = ((TextView) findViewById(R.id.newLiftTxt)).getText().toString();
                int reps = Integer.parseInt(((TextView) findViewById(R.id.numRepTxt)).getText().toString());
                int sets = Integer.parseInt(((TextView) findViewById(R.id.numSetTxt)).getText().toString());
                int weight = Integer.parseInt(((TextView) findViewById(R.id.newWeightTxt)).getText().toString());
                String selected = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                int day = getDayID(selected);
                String notes = ((TextView) findViewById(R.id.noteTxt)).getText().toString();
                return dbHelper.addData(name, day, sets, reps, weight, notes);
            }
            catch (Exception x) {
                toastMessage("Error saving data");
                return false;
            }
    }

    private int getDayID(String selected) {
        switch (selected){
            case "Sunday": return 0;
            case "Monday": return 1;
            case "Tuesday": return 2;
            case "Wednesday": return 3;
            case "Thursday": return 4;
            case "Friday": return 5;
            case "Saturday": return 6;
            default: return 0;
        }
    }

    /**
     * Create a pop up with a custom message.
     * @param message The message to show
     */
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
