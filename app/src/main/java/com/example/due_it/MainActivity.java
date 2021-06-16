package com.example.due_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences sp;
    public String secToken;

    // This is the Main Activity Class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secToken(View view) {
        EditText txtToken = findViewById(R.id.editToken);
    }

    private String securityToken() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        if (sp == null) {
            String sec_Token = securityToken();
            if (sec_Token == "") {
                Toast.makeText(MainActivity.this, "Missing Token", Toast.LENGTH_SHORT).show();
            }
            saveToken(sec_Token);
        } else {
            SharedPreferences secToken = sp;
        }
        return secToken;
    }
//

    public void duesResults(View view) {
//        ListView list = findViewById(R.id.list);
        Results current = new Results();
        Thread localThread = new Thread((Runnable) current);
        localThread.start();
    }

    public static void saveToken(String sec_token) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("MyToken", sec_token);
        editor.apply();
        Toast.makeText((Context) sp,"Token Saved", Toast.LENGTH_LONG).show();
    }

}
//Hey this is Rachel Vargas!