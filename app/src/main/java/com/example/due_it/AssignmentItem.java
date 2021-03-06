package com.example.due_it;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** This class will unfold the several Assignments data from the second API call
 *   It will be necessary to extract fields:
 *   "id", "name", "due_at", "points_possible", "allowed_attempts" and "submission_types"
 */

public class AssignmentItem {
    @SerializedName("id")
    private String As_id;
    @SerializedName("name")
    private String As_name;
    @SerializedName("due_at")
    private String As_due_at;
    @SerializedName("points_possible")
    private String As_points_possible;
    @SerializedName("Allowed_attempts")
    private String As_allowed_attempts;
    @SerializedName("submission_types")
    private List As_submission_types;

    public String getAs_id() {return As_id;}
    public void setAs_id(String as_id) {As_id = as_id;}

    public String getAs_name() {return As_name;}
    public void setAs_name(String as_name) {As_name = as_name;}

    public String getAs_due_at() {return As_due_at;}
    public void setAs_due_at(String as_due_at) {As_due_at = as_due_at;}

    public String getAs_points_possible() {
        return As_points_possible;
    }
    public void setAs_points_possible(String as_points_possible) {
        As_points_possible = as_points_possible;
    }

    public String getAs_allowed_attempts() {
        return As_allowed_attempts;
    }
    public void setAs_allowed_attempts(String as_allowed_attempts) {
        As_allowed_attempts = as_allowed_attempts;
    }

    public List getAs_submission_types() {
        return As_submission_types;
    }
    public void setAs_submission_types(List as_submission_types) {
        As_submission_types = as_submission_types;
    }
}
