package com.example.due_it;

import com.google.gson.Gson;



public class HTTPHelper2 {
    public static Course[] getCourse(String apiKey) {
        String result = HTTPHelper.readHTTP("https://canvas.instructure.com/api/v1/courses?per_page=100", "Bearer " + apiKey);

        Gson gson = new Gson();
        Course[] courses = gson.fromJson(result, Course[].class); //deserialization



        return courses;

    }
}