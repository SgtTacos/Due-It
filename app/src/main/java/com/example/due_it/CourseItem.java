package com.example.due_it;

import com.google.gson.annotations.SerializedName;

/** This class will unfold the different courses information returned from the first API call
 *  It will be necessary to select from the results only those courses with
 *  grading_standard_id !== null to extract "id" and "name" from each one to perform
 *  the iterations of assignments API calls that request assignment for each course
 */

public class CourseItem {
    @SerializedName("id")
    private String Co_id;
    @SerializedName("name")
    private String Co_name;
    @SerializedName("grading_standard_id")
    private String Co_grading_standard_id;
    @SerializedName("course_code")
    private String Co_course_code;

    public String getCo_id() {return Co_id;}
    public void setCo_Id(String Co_id) {this.Co_id = Co_id;}

    public String getCo_Name() {return Co_name;}
    public void setCo_Name(String Co_name) {this.Co_name = Co_name;}

    public String getCo_Grading_standard_id() {return Co_grading_standard_id;}
    public void setCoGrading_standard_id(String Co_grading_standard_id) {
        this.Co_grading_standard_id = Co_grading_standard_id;
    }
    public String getCo_Code() {return Co_course_code;}
    public void setCo_Code(String Co_course_code) {this.Co_course_code = Co_course_code;}

}
