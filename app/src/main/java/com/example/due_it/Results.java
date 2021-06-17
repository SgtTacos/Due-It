package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/** This class is in charge of doing what is necessary to
 * produce the results expected by the user:
 */
class Results extends AppCompatActivity implements Runnable {
    private static SharedPreferences sp;
    public String result1;
    public String result2;
    public String courses;
    public String assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
    }

    @Override
    public void run() {
        resultsToListView();
    }

    private String resultsToListView() {
        /**This method is in charge of calling the method coursesCall to
         * Retrieve all the courses where the user is enrolled.
         */


        return null;
    }

    public String coursesCall() {
        /** This method is in charge of calling the first API call
         * to get the courses information for the user
         * Iterate for each standard grading course calling the method assignmentsCall
         * to get their individual assignments.
         */

        return null;
    }

    public String assignmentsCall() {
        /** This method is in charge of calling as many API calls
         * as upcoming assignments has a course
         * Iterate for each standard grading course calling the method assignmentsCall
         * to get their individual assignments.
         * Then from the assignments it will select the upcoming ones,
         * their names, due dates and max grading
         * to store them in a list one by one, then show then on the screen
         */

        return null;
    }
}