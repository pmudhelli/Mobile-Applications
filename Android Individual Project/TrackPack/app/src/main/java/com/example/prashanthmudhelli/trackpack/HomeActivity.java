package com.example.prashanthmudhelli.trackpack;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.raweng.built.Built;
import com.raweng.built.BuiltApplication;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.utilities.BuiltConstant;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements One.OnFragmentInteractionListener, Two.OnFragmentInteractionListener{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static int currentDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new One(), "Search Travellers");
        //adapter.addFragment(new Two(), "Package Requests");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("PM", uri.toString());
    }

    public void showDatePickerDialog(View v) {
        Log.d("PM", "ID: " + v.getId());
        currentDatePicker = v.getId();
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    String fullName, fromCity, fromDate, toCity, toDate, email, mobile;

    public void newTraSubmit(View view) {
        Log.d("PM", "Submit clicked");

        EditText editText = (EditText) findViewById(R.id.traName);

        fullName = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traFromCity);
        fromCity = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traFromDate);
        fromDate = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traToCity);
        toCity = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traToDate);
        toDate = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traEmail);
        email = editText.getText().toString();
        editText = (EditText) findViewById(R.id.traMobile);
        mobile = editText.getText().toString();

        if(fullName.trim().isEmpty() || fromCity.trim().isEmpty() || fromDate.trim().isEmpty() || toCity.trim().isEmpty() || toDate.trim().isEmpty() || email.trim().isEmpty() || mobile.trim().isEmpty()) {
            Log.d("PM", "One or more fields are empty");
            Toast.makeText(HomeActivity.this, "Please enter all the values before posting!", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("PM", "in else");
            //insert into travellers table
            new BackgroundInsertOperation().execute();
        }
    }

    /*public void search(View view) {
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayout newTraLayout = (LinearLayout) findViewById(R.id.newTravelLayout);

        //One o = new One();
        //o.searchOperation();

        recycleView.setVisibility(View.VISIBLE);
        newTraLayout.setVisibility(View.GONE);
    }*/

    public void postNewTravel(View view) {
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayout newTraLayout = (LinearLayout) findViewById(R.id.newTravelLayout);

        recycleView.setVisibility(View.GONE);
        newTraLayout.setVisibility(View.VISIBLE);
    }

    public class BackgroundInsertOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BuiltApplication builtApplication = null;
            try {
                builtApplication  = Built.application(getApplicationContext(), "bltb6c6685a753714b6");
            }
            catch(Exception e) {
                Log.d("PM", e.getMessage());
            }

            BuiltObject projectObject = builtApplication.classWithUid("travellers").object();

            projectObject.set("traveller_name", fullName);
            projectObject.set("from_city", fromCity);
            projectObject.set("departure_date", fromDate);
            projectObject.set("to_city", toCity);
            projectObject.set("arrival_date", toDate);
            projectObject.set("traveller_email", email);
            projectObject.set("traveller_mobile", mobile);

            projectObject.saveInBackground(new BuiltResultCallBack() {
                @Override
                public void onCompletion(BuiltConstant.ResponseType responseType, BuiltError error) {
                    if (error == null) {
                        Log.d("PM", "Successfully posted");
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
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);
            Toast.makeText(HomeActivity.this, "Travel details are posted successfully!", Toast.LENGTH_LONG).show();
        }
    }
}