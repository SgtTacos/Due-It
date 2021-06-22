package com.example.due_it;


import java.util.List;

/** This class will contain the courses information returned from the first API call
* It will be necessary to select from the results only those courses with
 * grading_standard_id !== null to extract "id" and "name" from one to perform
 * the iterations of assignments API calls that request assignment for each course
 */
public class Courses {
    private List<CourseItem> courseItems;

    public List<CourseItem> getCourseItems() {
        return courseItems;
    }
    public void setCourseItems(List<CourseItem> courseItems) {
        this.courseItems = courseItems;
    }

}
