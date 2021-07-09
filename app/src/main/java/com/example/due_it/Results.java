package com.example.due_it;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** This class is in charge of doing what is necessary to
 * produce the results expected by the user:
 */
class Results extends AppCompatActivity implements Runnable {
    private static SharedPreferences sp;
    private Context context;
    private MainActivity activity;
    public long dat_lon;
    public String dat_str;
    public String my_courses;
    public String my_assignments;
    public String courses;
    public String assignments;
    public String courseID;
    public String courseNAME;
    public String courseCODE;
    public String asID;
    public String asDueDATE ;
    public String asPOINTS;
    public String asNAME;
    public String asATTEMPTS;
    public List asSUBTYPE;
    public String es = "&enrollment_state=active";
    public String pp = "?per_page=60";
    public String ob = "&order_by=due_at";
    public String opt_buck;
    public String min_sec = ":59:59";
    public String end_sem = "2021-07-22T05:59:59Z";
    public String token = "";

    public Results(MainActivity activity, MainActivity context, String op_bucket) {
        this.context = context;
        this.activity = activity;
        opt_buck = op_bucket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }
    /** This method is in charge of the app processes, making first the call for the courses that
     *  correspond to the token provided by the user.
     *  Using the parameter "enrollment_state = "active" allows to receive only the courses where
     *  the user is enrolled for the current semester.
     **********************************************************************************************
     *  When the call for courses returns, the data is stored in the classes Courses and          *
     *  CourseItem. After getting the individual information courses, begins the iteration on     *
     *  each one select data fields and to proceed to the second iteration to get all the desired *
     *  assignments for each course with a valid field grading_standard_id.                       *
     **********************************************************************************************
     *  This method is also designed to retrieve, select and provide AUTOMATICALLY assignments    *
     *  from all the courses where the user is enrolled, according to the user's selection        *
     *  between "past", "future" or just "upcoming".                                              *
     **********************************************************************************************
     *  During the iteration of assignments an output list will be filled with the assignments.   *
     *  When all the courses with their assignments have been retrieved, we have the complete     *
     *  list of courses and assignments required by the user. That list must be sorted by         *
     *  date and course, before the runOnUiThread will return the list to be shown on the screen. *
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        token = sharedPreferences.getString("secToken", ""); // Here is needed the token transfer from SharedPreferences
        courses = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses" + pp
                + es, "Bearer " + token);
        my_courses = "{\"masterCourses\":"+courses+"}";
        Gson gson_c = new Gson();
        final Courses cc = gson_c.fromJson(my_courses, Courses.class);
        List<String> due_assignments = new ArrayList<String>();
        String As_Line;
        for (CourseItem item_c : cc.getCourseItems()) {
            String gsi = item_c.getCo_Grading_standard_id();
            if (item_c.getCo_Grading_standard_id() != null) {
                courseID = item_c.getCo_id();
                courseNAME = item_c.getCo_Name();
                courseCODE = item_c.getCo_Code();
                Log.d("MainActivity", "opt_buck: " + opt_buck);
                assignments = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses/"
                        + courseID + "/assignments"+ pp + opt_buck,"Bearer " + token);
                my_assignments = "{\"masterAssignments\":"+assignments+"}";
                Gson gson_a = new Gson();
                final Assignments as = gson_a.fromJson(my_assignments, Assignments.class);
                for (AssignmentItem item_a : as.getAssignmentItems()) {
                    asDueDATE = item_a.getAs_due_at();
                    if (asDueDATE == null) {  // Null dates were found that made the app crash
                        asDueDATE = end_sem;  // A constant date is moved to null dates
                    }
                    asNAME = item_a.getAs_name();
                    asID = item_a.getAs_id();
                    asPOINTS = item_a.getAs_points_possible();
                    asSUBTYPE = item_a.getAs_submission_types();
                    asATTEMPTS = item_a.getAs_allowed_attempts();
                    dat_lon = (Instant.parse(asDueDATE).toEpochMilli())-21600;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
                    dat_str = df.format(dat_lon)+ min_sec; // seconds difference after conversion
                    asDueDATE = dat_str;
                    As_Line = "\nDate : " + asDueDATE + " Course : " + courseCODE
                            + " Assignment : " + asNAME + " Points: " + asPOINTS
                            + " Type : " + asSUBTYPE;
                    due_assignments.add(As_Line);
                }
            }
            Log.d("MainActivity", "due_assignments: " + due_assignments);
        }
        /** At this point we have the complete list of requested due assignments
         * TODO Unless we fix Canvas API "ordered_by" option crashes due to null due_at dates,
         * TODO WE NEED TO SORT THE OBTAINED LIST BY DATE AND COURSE
         */
        Log.d("MainActivity", "Results: " + due_assignments);
        ArrayAdapter<String> ListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, due_assignments);
        ListView listview = findViewById(R.id.list);
        listview.setAdapter(ListAdapter);
    }
}
