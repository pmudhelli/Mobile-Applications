package com.example.prashanthmudhelli.trackpack;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.raweng.built.Built;
import com.raweng.built.BuiltApplication;
import com.raweng.built.BuiltClass;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;
import com.raweng.built.utilities.BuiltConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    public static String loggedInUserEmail, emailFB, fullNameFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        //facebook login
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/
                String accessToken = loginResult.getAccessToken().getToken();
                Log.d("PM", "access token: " +accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        emailFB = bFacebookData.get("email").toString();
                        Log.d("PM", emailFB);
                        fullNameFB = bFacebookData.get("first_name").toString() +" " +bFacebookData.get("last_name").toString();
                        loggedInUserEmail = "FB";
                        Log.d("PM", "Starting home activity");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();
        try {
            String id = object.getString("id");
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public void login(View view) {
        /*Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);*/
        EditText emailView = (EditText) findViewById(R.id.inEmail);
        EditText pwdView = (EditText) findViewById(R.id.inPwd);
        final String email = emailView.getText().toString();
        final String pwd = pwdView.getText().toString();
        loggedInUserEmail = email;
        BuiltApplication builtApplication = null;
        try {
            builtApplication  = Built.application(getApplicationContext(), "bltb6c6685a753714b6");
        }
        catch(Exception e) {
            Log.d("PM", e.getMessage());
        }
        BuiltUser user = builtApplication.user();
        user.loginInBackground(email, pwd, new BuiltResultCallBack() {
            @Override
            public void onCompletion(BuiltConstant.ResponseType responseType, BuiltError builtError) {
                if (builtError == null) {
                    Log.d("PM", "No Error");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("PM", "Error: " + email + ", " + pwd + ", " + builtError.getErrorMessage());
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("Invalid Credentials");
                    alertDialog.setCancelable(true);
                    alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        /*BuiltApplication builtApplication = null;
        BuiltClass projectClass = builtApplication.classWithUid("users");
        BuiltQuery projectQuery = projectClass.query();
        projectQuery.execInBackground(new QueryResultsCallBack() {
            @Override
            public void onCompletion(BuiltConstant.ResponseType responseType, QueryResult queryResultObject, BuiltError error) {
                if (error == null) {
                    List<BuiltObject> objects = queryResultObject.getResultObjects();
                    Log.d("PM", "" + objects.size());
                    for (BuiltObject object : objects) {
                        Log.d("PM", "" + object.get("email"));
                    }
                } else {
                    Log.d("PM", "Error");
                }
            }
        });*/
    }

    public void signUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        LoginManager.getInstance().logOut();
    }
}