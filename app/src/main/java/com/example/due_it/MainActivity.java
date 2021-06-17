package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences sp;
    public String sec_Token;

    /** This is the Main Activity Class.
     * This class will command the app work.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** Loading the app, it will retrieve a security token from SharedPreferences
         * If a Token is present, it will be shown on screen and copied to text
         * If there is no token in SharedPreferences
         * a hint requesting Token will be shown
         */
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        EditText hint1 = findViewById(R.id.editToken);
        if (sp == null) {
            hint1.setHint("Security Access Token Required");
        } else {
            hint1.setHint((CharSequence) sp);
            hint1.setText((CharSequence) sp);
        }
    }

    public void secToken(View view) {
        /** This method will run when SECURITY Button is clicked
         * Supposedly user will press security only if a new Token is provided
         * Anyways if there is already a previous existing token, it will be re-saved
         */
        sec_Token = securityToken();
    }

    private String securityToken() {
        /** This method is invoked by method secToken and it is in charge of
         *  getting the text content from the EditView object and verifying
         *  that it is not null. If it is will show a toast message.
         */
        EditText txtToken = findViewById(R.id.editToken);
        if (txtToken == null) {
            Toast.makeText(MainActivity.this, "Missing Token", Toast.LENGTH_SHORT).show();
        }
        saveToken(sec_Token);
        return sec_Token;
    }

    public static void saveToken(String sec_token) {
        /** This method is invoked by securityToken and it is in charge of
         *  saving the Token in SharedPreferences for future uses
         *  It shows a Toast message indicating Token was saved
         */
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("MyToken", sec_token);
        editor.apply();
        Toast.makeText((Context) sp,"Token was Saved", Toast.LENGTH_LONG).show();
    }

    public void duesResults(View view) {
        /** This method will run when DUES button is clicked
         *  It is in charge of requesting the dues results
         *  and initiating it in a new thread
         */
        ListView list = findViewById(R.id.list);
        Results current = new Results();
        Thread localThread = new Thread(current);
        localThread.start();
    }

}
//Hey this is Rachel Vargas!