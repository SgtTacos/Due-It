package com.example.due_it;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
    private MainActivity activity;
    public long datlon;
    public String datstr;
    public String mycourses;
    public String myassignments;
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
    public String pp = "?per_page=40";
    public String es = "&enrollment_state=active";
    public String op_bu_fu = "&bucket=future";
    public String op_bu_ov = "&bucket=overdue";
    public String op_bu_up = "&bucket=upcoming";
    public String op_bu_pa = "&bucket=past";
    public String op_bucket;
    public String minsec = ":59:59";
    public String endsem = "2021-07-22T05:59:59Z";
    public String token = "";

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
        token = ""; // Here is needed the token load from SharedPreferences
        op_bucket = op_bu_fu; //Option of results, could be defined by buttons variety
        courses = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses" + pp
                + es, "Bearer " + token);
        mycourses = "{\"masterCourses\":"+courses+"}";
        Gson gson_c = new Gson();
        final Courses cc = gson_c.fromJson(mycourses, Courses.class);
        List<String> due_assignments = new ArrayList<String>();
        String As_Line;
        for (CourseItem item_c : cc.getCourseItems()) {
            String gsi = item_c.getCo_Grading_standard_id();
            if (item_c.getCo_Grading_standard_id() != null) {
                courseID = item_c.getCo_id();
                courseNAME = item_c.getCo_Name();
                courseCODE = item_c.getCo_Code();
                assignments = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses/"
                        + courseID + "/assignments"+ pp + op_bucket,"Bearer " + token);
                myassignments = "{\"masterAssignments\":"+assignments+"}";
                Gson gson_a = new Gson();
                final Assignments as = gson_a.fromJson(myassignments, Assignments.class);
                for (AssignmentItem item_a : as.getAssignmentItems()) {
                    asDueDATE = item_a.getAs_due_at();
                    if (asDueDATE == null) {  // Null dates were found that made the app crash
                        asDueDATE = endsem;  // A constant date is moved to null dates
                    }
                    asNAME = item_a.getAs_name();
                    asID = item_a.getAs_id();
                    asPOINTS = item_a.getAs_points_possible();
                    asSUBTYPE = item_a.getAs_submission_types();
                    asATTEMPTS = item_a.getAs_allowed_attempts();
                    datlon = (Instant.parse(asDueDATE).toEpochMilli())-21600;
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");
                    datstr = df.format(datlon)+minsec; // seconds difference after conversion
                    asDueDATE = datstr;
                    As_Line = "\nDate : " + asDueDATE + " Course : " + courseCODE
                            + " Assignment : " + asNAME + " Points: " + asPOINTS
                            + " Type : " + asSUBTYPE;
                    due_assignments.add(As_Line);
                }
                Log.d("MainActivity", "due_assignments: " + due_assignments);
            }
        }
    }
}
/** At this point we should have the complete list of due assignments
 * TODO still WE NEED TO SORT THE OBTAINED LIST BY DATE AND COURSE
 * API option "ordered_by:due_at" crashes due to null dates
 */
//        activity.runOnUiThread(() -> {
//            activity.resultsResponse(due_assignments);
//        });
