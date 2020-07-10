package com.example.workout;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {



    private TabLayout tabLayout;
    private DatabaseHelper dbHelper;
    private ArrayList<String> liftNames;
    private ArrayList<String> repAmount;
    private ArrayList<String> setAmount;
    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> repAdapter;
    private ArrayAdapter<String> setAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureAddBtn();
        dbHelper = new DatabaseHelper(this);
        tabSelect();
    }

    private void configureAddBtn()
    {
        Button add = (Button) findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    public void tabSelect()
    {
        tabLayout = (TabLayout) findViewById(R.id.day);
        populateListView(Day.SUNDAY);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                Day day = Day.toDay(tab.getPosition());
                populateListView(day);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void populateListView(Day d)
    {
        Cursor data = dbHelper.getData();
        ListView lv = findViewById(R.id.liftsListView);
        liftNames = new ArrayList<>();

        while (data.moveToNext())
        {
            //if(data.getString(1).equals(d.toString()))
            liftNames.add(data.getString(1));
        }
        nameAdapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.liftName, liftNames);

        lv.setAdapter(nameAdapter);
    }

    public void addLift(View v)
    {
        setContentView(R.layout.add_lift);
    }
}
