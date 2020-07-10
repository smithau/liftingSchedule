package com.example.workout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements GestureDetector.OnGestureListener {

    private static final String ID_INDEX = "ID Index";
    private static final int DB_ID = 0;
    private static final int DB_LIFT = 1;
    private static final int DB_DAY = 2;
    private static final int DB_SETS = 3;
    private static final int DB_REPS = 4;
    private static final int DB_WEIGHT = 5;
    private static final int DB_NOTES = 7;
    CustomAdapter customAdapter = new CustomAdapter();
    private ArrayList<Integer> dbId = new ArrayList<>(); //ID is stored in its position in the current listView
    private TabLayout tabLayout;
    private DatabaseHelper dbHelper;
    private ArrayList<String> liftNames;
    private ArrayList<String> sets;
    private ArrayList<String> reps;
    private ArrayList<String> weights;
    private ArrayList<String> notes;
    private ArrayAdapter<String> nameAdapter;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureListView();
        liftNames = new ArrayList<>();
        sets = new ArrayList<>();
        reps = new ArrayList<>();
        weights = new ArrayList<>();
        notes = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);

        tabSelect();

        gestureDetector = new GestureDetector(this);

    }


    private void configureListView() {
        final ListView gv = findViewById(R.id.lifts_listview);
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                final int pos = position;
                AlertDialog.Builder delete = new AlertDialog.Builder(MainActivity.this);
                delete.setTitle("Delete").setMessage("Are you sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.delete(dbId.get(pos));
                                dbId.remove(pos);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
                return true;
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { ;
                if (dbId.get(position) != -1) {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra(ID_INDEX, dbId.get(position) + "");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    intent.putExtra(ID_INDEX, "-1");
                    intent.putExtra("day", tabLayout.getSelectedTabPosition());
                    startActivity(intent);
                    // need to repopulate the grid after a work out is added
                    tabLayout = findViewById(R.id.day);
                }
            }
        });

        gv.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent e) {
                gestureDetector.onTouchEvent(e);
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * This determines what tab is selected and populates the list appropriately
     */
    public void tabSelect()
    {
        //start application on current day
        tabLayout = (TabLayout) findViewById(R.id.day);
        String d = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        int day = getDayID(d);
        TabLayout.Tab tab = tabLayout.getTabAt(day);
        tab.select();
        populateArrays(day);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                int day = tab.getPosition();
                populateArrays(day);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
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
     * This populates the arrays used to display each workout.  It then displays the information in
     * a listview
     * @param d the day the arrays should be populated with
     */
    private void populateArrays(int d)
    {
        ListView lv = (ListView)findViewById(R.id.lifts_listview);
        liftNames.clear();
        sets.clear();
        reps.clear();
        weights.clear();
        notes.clear();

        Cursor data = dbHelper.getData();
        dbId.clear();
        while (data.moveToNext()) {
            int dbDay = data.getInt(DB_DAY);
            if (dbDay == d) {
                dbId.add(data.getInt(DB_ID));
                liftNames.add(data.getString(DB_LIFT).toUpperCase());
                sets.add("SETS: " + data.getString(DB_SETS));
                reps.add("REPS: " + data.getString(DB_REPS));
                weights.add(data.getString(DB_WEIGHT) + "lb");
                String note = data.getString(DB_NOTES);
                if (note.equals("Notes"))
                    note = "";
                notes.add(note);
            }
        }
        dbId.add(-1);
        liftNames.add("ADD NEW LIFT");
        sets.add("");
        reps.add("");
        weights.add("");
        notes.add("");
        lv.setAdapter(customAdapter);
    }

    /**
     * creates a toast message used in debugging
     * @param message
     */
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    /**
     * used to determine which way a swipe is
     * @param down initial position
     * @param move final position
     * @param velocityX speed in x direction
     * @param velocityY speed in Y direction
     * @return true if an action was performed
     */
    @Override
    public boolean onFling(MotionEvent down, MotionEvent move, float velocityX, float velocityY) {
        float difY = move.getY() - down.getY();
        float difX = move.getX() - down.getX();

        if ( Math.abs(difX) > 400 && Math.abs(velocityX) > 100)
        {
            if (difX > 0)
                swipeRight();
            else
                swipeLeft();
            return true;
        }

        return false;
    }

    /**
     * moves forward one day updating both the selected tab and the lifts displayed
     */
    private void swipeLeft()
    {
        tabLayout = findViewById(R.id.day);
        int day = tabLayout.getSelectedTabPosition();
        if (day == 6)
            day = 0;
        else
            day++;
        TabLayout.Tab tab = tabLayout.getTabAt(day);
        tab.select();
        populateArrays(day);
    }

    /**
     * moves back one day updating both the selected tab and the lifts displayed
     */
    private void swipeRight()
    {
        tabLayout = findViewById(R.id.day);
        int day = tabLayout.getSelectedTabPosition();
        if (day == 0)
            day = 6;
        else
            day--;
        TabLayout.Tab tab = tabLayout.getTabAt(day);
        tab.select();
        populateArrays(day);
    }

    /**
     * used to create unique items in the listview
     */
    class CustomAdapter extends BaseAdapter {
        /**
         * @return the ammount of items in the current listview
         */
        @Override
        public int getCount() {
            return liftNames.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * creates an item for the next position in the listview
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.custom_layout,null);
            TextView name = (TextView) convertView.findViewById(R.id.liftName_lst);
            TextView set = (TextView) convertView.findViewById(R.id.sets_lst);
            TextView rep = (TextView) convertView.findViewById(R.id.reps_lst);
            TextView weight = (TextView) convertView.findViewById(R.id.weight_lst);
            TextView note = (TextView) convertView.findViewById(R.id.notes_lst);

            name.setText(liftNames.get(position));
            set.setText(sets.get(position));
            rep.setText(reps.get(position));
            weight.setText(weights.get(position));
            note.setText(notes.get(position));

            return convertView;
        }
    }
}
