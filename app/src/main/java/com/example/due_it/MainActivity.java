package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

/** This is the Main Activity Class.
 * This class will command the app work.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SEC_TOKEN = "secToken";
    public static final String APP_THEME = "appTheme";
    private MainActivity activity;
    public String op_bucket;
    public String op_bu_ov = "&bucket=overdue";
    public String op_bu_pa = "&bucket=past";
    public String op_bu_fu = "&bucket=future";
    public String op_bu_up = "&bucket=upcoming";

    /** Loading the app, it will retrieve a security token from SharedPreferences
     *  If a Token is present, it will be shown on screen and copied to text
     *  If there is no token in SharedPreferences
     *  a hint requesting Token will be shown
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    /** This method is invoked by securityToken and it is in charge of
     *  saving the Token in SharedPreferences for future uses
     *  It shows a Toast message indicating Token was saved
     */
    public void saveToken(View view) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        EditText tokenText = findViewById(R.id.editToken);
        if (tokenText.getText().toString().matches("")) {
            Toast.makeText(this,"Text field cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            editor.putString(SEC_TOKEN, tokenText.getText().toString());
            editor.apply();
            Toast.makeText(this,"Token was Saved", Toast.LENGTH_LONG).show();
        }
    }

    /** This method......
     *  Tsosie, you need to add comments for this method
     *
     */
    public void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String tokenString = sp.getString(SEC_TOKEN, "");

        EditText text1 = findViewById(R.id.editToken);

        text1.setHint("Security Access Token Required");
        text1.setText(tokenString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button2:
                op_bucket=op_bu_ov;
                this.duesResults();
                break;
            case R.id.button3:
                op_bucket=op_bu_pa;
                this.duesResults();
                break;
            case R.id.button4:
                op_bucket=op_bu_fu;
                this.duesResults();
                break;
            case R.id.button5:
                op_bucket=op_bu_up;
                this.duesResults();
                break;

        }
    }

    /** This method will run when DUES button is clicked
     *  It is in charge of requesting the dues results
     *  and initiating it in a new thread
     */
    public void duesResults() {
        ListView list = findViewById(R.id.list);
        Results current = new Results(this, op_bucket);
        Thread localThread = new Thread(current);
        localThread.start();
    }

    /** This method exists to receive the results response,
     *  translate it to the list and display the list
     */
    void resultsResponse(List<String> due_assignments) {
        Log.d("MainActivity", "Results: " + due_assignments);
        ArrayAdapter<String> ListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, due_assignments);
        ListView listview = findViewById(R.id.list);
        listview.setAdapter(ListAdapter);
    }

}