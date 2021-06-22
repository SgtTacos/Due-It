package com.example.due_it;

import java.util.List;

    /** This class will contain all the returned Assignments data from the second API call
     *  With the help of the AssignmentItem class will extract fields:
     *  "due_at", "points_possible", "name", "allowed_attempts" and "submission_types"
     */

    public class Assignments {
        private List<AssignmentItem> assignmentItems;

        public List<AssignmentItem> getAssignmentItems() {
            return assignmentItems;
        }
        public void setAssignmentItems(List<AssignmentItem> assignmentItems) {
            this.assignmentItems = assignmentItems;
        }
}
