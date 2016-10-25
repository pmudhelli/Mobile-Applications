package com.example.prashanthmudhelli.trackpack;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.raweng.built.Built;
import com.raweng.built.BuiltApplication;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.utilities.BuiltConstant;

public class SignUpActivity extends AppCompatActivity {

    String email, firstName, lastName, pwd, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void createNewUser(View view) {
        EditText signUpView = (EditText) findViewById(R.id.newEmail);
        email = signUpView.getText().toString();
        signUpView = (EditText) findViewById(R.id.newFirstName);
        firstName = signUpView.getText().toString();
        signUpView = (EditText) findViewById(R.id.newLastName);
        lastName = signUpView.getText().toString();
        signUpView = (EditText) findViewById(R.id.newPwd);
        pwd = signUpView.getText().toString();
        signUpView = (EditText) findViewById(R.id.newMobile);
        mobile = signUpView.getText().toString();

        if(email.trim().isEmpty() || firstName.trim().isEmpty() || lastName.trim().isEmpty() || pwd.trim().isEmpty() || mobile.trim().isEmpty()) {
            Log.d("PM", "One or more fields are empty");
            Toast.makeText(SignUpActivity.this, "Please enter all the values!", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("PM", "in else");
            //insert into travellers table
            new BackgroundCreateOperation().execute();
        }
        //this.finish();
    }

    public class BackgroundCreateOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BuiltApplication builtApplication = null;
            try {
                builtApplication  = Built.application(getApplicationContext(), "bltb6c6685a753714b6");
            }
            catch(Exception e) {
                Log.d("PM", e.getMessage());
            }

            BuiltObject projectObject = builtApplication.classWithUid("built_io_application_user").object();

            projectObject.set("email", email);
            projectObject.set("first_name", firstName);
            projectObject.set("last_name", lastName);
            projectObject.set("password", pwd);
            projectObject.set("mobile", mobile);

            projectObject.saveInBackground(new BuiltResultCallBack() {
                @Override
                public void onCompletion(BuiltConstant.ResponseType responseType, BuiltError error) {
                    if (error == null) {
                        Log.d("PM", "Successfully created");
                    } else {
                        Log.d("PM", "Insertion error: " + error.toString());
                    }
                }
            });
            return "Inserted";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("PM", "Done " + result);
            finish();
            Toast.makeText(SignUpActivity.this, "New account created. Login to continue!", Toast.LENGTH_LONG).show();
        }
    }
}