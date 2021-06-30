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
    public String courses;
    public String assignments;
    public String courseID;
    public String courseNAME;
    public String asID;
    public String asDueDATE ;
    public String asPOINTS;
    public String asNAME;
    public String asATTEMPTS;
    public String asSUBTYPE;
    public String es = "?enrollment_state=active";
    String token = "";
    HTTPHelper httpHelper = new HTTPHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }

    /** This method is in charge of first making the call for courses.
     *  Since the parameter "enrollment_state = "active" exists and is accepted in the API call,
     *  it helps to retrieve only the courses where the user is enrolled for the current semester.
     *  Therefore we do not need to change the pagination. Nobody enrolls in more than ten courses.
     **********************************************************************************************
     **  This method is designed to retrieve, select and provide AUTOMATICALLY all assignments    *
     **  from all the courses where the user is enrolled, without any additional button pressing  *
     **  or further additional interaction from the user.                                         *
     **********************************************************************************************
     *  When the call for courses returns, the data is stored in the classes Courses and
     *  CourseItem. After getting the individual information courses, begins the iteration on each
     *  one select data fields and to proceed to the second iteration to get all the desired
     *  assignments for each course with a valid field grading_standard_id
     *********************************************************************************************
     *  During the iteration of assignments an output list will be filled with the assignments.
     *  When all the courses with their assignments have been retrieved, we have the complete
     *  list of courses and assignments required by the user.
     *  A final step is the sort of the list by date and course, inserting the code before the
     *  runOnUiThread which will return the sorted list to be shown on the screen.
     */
    @Override
    public void run() {
        courses = httpHelper.readHTTP("https://canvas.instructure.com/api/v1/courses" + es, "Bearer " + token);
        Gson gson_c = new Gson();
        final Courses cc = gson_c.fromJson(courses, Courses.class);
        Log.d("MainActivity", "Courses: " + cc);
        List<String> due_assignments = new ArrayList<String>();
        String As_Line;
        for (CourseItem item_c : cc.getCourseItems()) {
            if (item_c.getCo_Grading_standard_id() != null) {
                courseID = item_c.getCo_id();
                courseNAME = item_c.getCo_Name();
                assignments = httpHelper.readHTTP("https://canvas.instructure.com/api/v1/courses/" + courseID + "/assignments?bucket=upcoming", "Bearer " + token);
                Gson gson_a = new Gson();
                final Assignments as = gson_a.fromJson(assignments, Assignments.class);
                Log.d("MainActivity", "Course: " + cc);
                for (AssignmentItem item_a : as.getAssignmentItems()) {
                    asDueDATE = item_a.getAs_due_at();
                    asNAME = item_a.getAs_name();
                    asID = item_a.getAs_id();
                    asPOINTS = item_a.getAs_points_possible();
                    asSUBTYPE = item_a.getAs_submission_types();
                    asATTEMPTS = item_a.getAs_allowed_attempts();
                    As_Line = "Date : " + asDueDATE + "Course : " + courseNAME + "Assignment : " + asNAME
                            + "Type : " + asSUBTYPE + "Points: " + asPOINTS;
                    due_assignments.add(As_Line);
                    Log.d("MainActivity", "Assignment: " + as);
                }
            }
        }
/** At this point we should have the complete list of due assignments
  * TODO still WE NEED TO SORT THE OBTAINED LIST BY DATE AND COURSE
*/
        activity.runOnUiThread(() -> {
            activity.resultsResponse(due_assignments);
        });
    }
}
