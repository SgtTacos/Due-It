package com.example.due_it;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Scanner;

public class CanvasAPI {
    private static SharedPreferences sp;
    public static void Canvas(String[] args) {
        HTTPHelper httpHelper = new HTTPHelper();
        Scanner scanner = new Scanner(System.in);
        SharedPreferences sp = new MainActivity().getSharedPreferences("MyToken", Context.MODE_PRIVATE);

        /** Provided by Brother Macbeth
        * Each user can get their own Canvas Token by going to:
        * https://byui.instructure.com/profile/settings and select "New Access Token".
        * Only do this once and copy the token to the textedit so it can be saved
        * to be used again later.
        */
        String url = "https://canvas.instructure.com/api/v1/courses";
        String es = "?enrollment_state=active";
        SharedPreferences token = sp;

        String result1 = httpHelper.readHTTP(url+es, "Bearer "+token);



        /** The following lines should be used only to verify first API call*/
        System.out.println("Canvas API Demo");
        System.out.println("==================");
        System.out.println("JSON Output for Courses:");
        System.out.println(result1);
        /**/

        /** The following lines should be used only to verify second API call*/
        System.out.println("Enter the course ID you want assignments for (look at output above): ");
        String courseID = scanner.nextLine();
        String result2 = httpHelper.readHTTP("https://canvas.instructure.com/api/v1/courses/"+courseID+"/assignments?bucket=upcoming", "Bearer "+token);
        System.out.println("JSON Output for Assignments of that Course:");
        String unescresult = Unescaper.unescape_string(result2);
        System.out.println(unescresult);
        /**/
    }
}
