package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/** This class is in charge of doing what is necessary to
 * produce the results expected by the user:
 */
class Results extends AppCompatActivity implements Runnable {
    private static SharedPreferences sp;
    private MainActivity activity;
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
    public String GSI = "107060000000000001";
    public String pp = "40";
    public String es = "?enrollment_state=active";
    public String op_bu_up = "upcoming";
    public String op_bu_fu = "future";
    public String op_bu_pa = "past";
    public String op_bucket;
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

    @Override
    public void run() {
        token = "";// Here has to be recovered the token from SharadPreferences
        op_bucket = op_bu_up;
        courses = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses"
                + es, "Bearer " + token);
        Gson gson_c = new Gson();
        final Courses cc = gson_c.fromJson(courses, Courses.class);
        List<String> due_assignments = new ArrayList<>();
        String As_Line;
        for (CourseItem item_c : cc.getCourseItems()) {
            if (item_c.getCo_Grading_standard_id() != null) {
                courseID = item_c.getCo_id();
                courseCODE = item_c.getCo_Code();
                courseNAME = item_c.getCo_Name();
                assignments = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses/"
                        + courseID + "/assignments?per_page="+pp+"&bucket="+op_bucket,
                        "Bearer " + token);
                Gson gson_a = new Gson();
                final Assignments as = gson_a.fromJson(assignments, Assignments.class);
                Log.d("MainActivity", "Assignments: " + as);
                for (AssignmentItem item_a : as.getAssignmentItems()) {
                    asDueDATE = item_a.getAs_due_at();
                    asNAME = item_a.getAs_name();
                    asID = item_a.getAs_id();
                    asPOINTS = item_a.getAs_points_possible();
                    asSUBTYPE = item_a.getAs_submission_types();
                    asATTEMPTS = item_a.getAs_allowed_attempts();
                    As_Line = "\nDate : " + asDueDATE + " Course : " + courseCODE
                            + "\nAssignment : " + asNAME + " Points: " + asPOINTS
                            + "\nType : " + asSUBTYPE;
                    due_assignments.add(As_Line);
                    Log.d("MainActivity", "As_Line: " + As_Line);
                }
            }
        }
    }
}
/** At this point we should have the complete list of due assignments
  * TODO still WE NEED TO SORT THE OBTAINED LIST BY DATE AND COURSE BEFORE DISPLAYING IT
        activity.runOnUiThread(() -> activity.resultsResponse(due_assignments));
 */
