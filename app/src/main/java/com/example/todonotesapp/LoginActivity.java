package com.example.todonotesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    // Create some variables for editText or button type for editText or button
    EditText editTextFullName,editTextUserName;
    Button buttonLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mapping variable to XML file's editTextView or Button
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonLogin = findViewById(R.id.buttonLogin);

        //Let's create sharedPreferences
        setUpSharedPreferences();

        // create a OnClickListener variable clickAction
        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = editTextFullName.getText().toString();
                String userName = editTextUserName.getText().toString();

                // Put a condition to check String variable are empty or fill
                // fullName or userName should not be empty
                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)) {

                    // Log is something whatever we write her gets printed in the logcat
                    /*Log.d("LoginActivity","on click performed");*/
                    // Here we code onClick event
                    // Goes this activity to myNotesActivity using intent
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);

                    // we send some data from this activity to another using putExtra
                    // putExtra is part of intent
                    intent.putExtra(AppConstant.INSTANCE.getFULL_NAME(), fullName);
                    startActivity(intent);

                    // After login saved shared preference for login status
                    saveLoginStatus();
                    // save full name myNoteActivity
                    saveFullName(fullName);

                } else {

                    Toast.makeText(LoginActivity.this,
                            "FullName and UserName can't be empty",Toast.LENGTH_SHORT).show();
                }

            }
        };

        // set OnClickListener throw variable clickAction on buttonLogin variable
        buttonLogin.setOnClickListener(clickAction);
    }

    // this function for full name save in sharedPreference
    private void saveFullName(String fullName) {

        editor = sharedPreferences.edit();
        editor.putString(PrefConstant.INSTANCE.getFULL_NAME(),fullName);
        editor.apply();
    }

    // this function for full name save in sharedPreference
    private void saveLoginStatus() {

        // SharedPreferences editor is read write or delete SharedPreferences
        editor = sharedPreferences.edit(); // this step is like an open my register
        editor.putBoolean(PrefConstant.INSTANCE.getIS_LOGGED_IN(),true);// this step id like you write down in your register
        editor.apply();// Apply the all changes in sharedPreference
    }

    private void setUpSharedPreferences() {

        sharedPreferences = getSharedPreferences(PrefConstant.INSTANCE.getSHARED_PREFERENCE_NAME(),MODE_PRIVATE);
    }
}