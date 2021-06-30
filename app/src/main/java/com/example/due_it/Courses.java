package com.example.due_it;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** This class will contain the courses information returned from the first API call.
 *  Since the parameter "enrollment_state = "active" exists and is accepted in the API call,
 *  it helps to retrieve only the courses where the user is enrolled for the current semester.
 *  Therefore we do not need to change the pagination. Nobody enrolls in more than ten courses.
 *  It will be necessary to select from the results only those courses with the field
 *  grading_standard_id != null to extract "id" and "name" from one to perform
 *  the iterations of assignments API calls that request assignment for each course
 */
public class Courses {
    @SerializedName("masterCourses")
    private List<CourseItem> courseItems;
    public List<CourseItem> getCourseItems() {
        return courseItems;
    }
    public void setCourseItems(List<CourseItem> courseItems) {
        this.courseItems = courseItems;
    }
}
