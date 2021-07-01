package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

/** This is the Main Activity Class.
 * This class will command the app work.
 */
public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SEC_TOKEN = "secToken";
    public static final String APP_THEME = "appTheme";

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
    }

    /** This method is invoked by securityToken and it is in charge of
     *  saving the Token in SharedPreferences for future uses
     *  It shows a Toast message indicating Token was saved
     */
    public void saveToken(View view) {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
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

    public void loadData() {
        SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String tokenString = sp.getString(SEC_TOKEN, "Security Access Token Required");

        EditText text1 = findViewById(R.id.editToken);

        text1.setHint("Security Access Token Required");
        text1.setText(tokenString);
    }

    /** This method will run when DUES button is clicked
     *  It is in charge of requesting the dues results
     *  and initiating it in a new thread
     */
    public void duesResults(View view) {
        ListView list = findViewById(R.id.list);
        Results current = new Results();
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