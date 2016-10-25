package com.example.prashanthmudhelli.trackpack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.raweng.built.Built;
import com.raweng.built.BuiltApplication;
import com.raweng.built.BuiltClass;
import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;
import com.raweng.built.utilities.BuiltConstant;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link One.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link One#newInstance} factory method to
 * create an instance of this fragment.
 */
public class One extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Travellers> travellersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TravellersAdapter tAdapter;
    public String searchFrom, searchTo, searchDate;
    public String requester_name, requester_email, traveller_email, requester_mobile, comments, comment_date;

    private OnFragmentInteractionListener mListener;

    public One() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment One1.
     */
    // TODO: Rename and change types and number of parameters
    public static One newInstance(String param1, String param2) {
        One fragment = new One();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        AutoCompleteTextView fromView = (AutoCompleteTextView) view.findViewById(R.id.airport_from);
        String[] airports = {"San Jose", "San Francisco", "Los Angeles", "Hyderabad", "New Delhi"};
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, airports);
        fromView.setAdapter(listAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        tAdapter = new TravellersAdapter(travellersList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final Travellers travellers = travellersList.get(position);
                Toast.makeText(getContext(), travellers.getEmail() + " is selected!", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Send Parcel Request");

                //final TextView mobileView = new TextView(getContext());
                //mobileView.setText("Mobile: 4085458918");
                //final TextView emailView = new TextView(getContext());
                //emailView.setText("Email: prash@m.com");
                //final EditText mobileView = new EditText(getContext());
                //mobileView.setInputType(InputType.TYPE_CLASS_TEXT);
                //mobileView.setText(); fetch from db and display if available
                //mobileView.setHint("Enter Contact #");
                final EditText commentsView = new EditText(getContext());
                commentsView.setInputType(InputType.TYPE_CLASS_TEXT);
                commentsView.setHint("Enter Comments");
                LinearLayout infoLayout = new LinearLayout(getContext());
                infoLayout.setOrientation(LinearLayout.VERTICAL);
                infoLayout.setPadding(75, 25, 75, 0);
                //infoLayout.addView(mobileView);
                //infoLayout.addView(emailView);
                infoLayout.addView(commentsView);

                builder.setView(infoLayout);

                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("PM", commentsView.getText().toString());
                        traveller_email = travellers.getEmail();
                        requester_email = LoginActivity.loggedInUserEmail;
                        comments = commentsView.getText().toString();
                        //requester_mobile = mobileView.getText().toString();
                        TimeZone pst = TimeZone.getTimeZone("America/Los_Angeles");
                        DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
                        isoDateFormat.setTimeZone(pst);
                        comment_date = isoDateFormat.format(new Date());
                        //get requester full name, mobile
                        Log.d("PM", "hereeeeee");
                        if(requester_email.equals("FB")) {
                            requester_email = LoginActivity.emailFB;
                            requester_name = LoginActivity.fullNameFB;
                            requester_mobile = "Not Available";
                            new BackgroundInsertRequestOperation().execute();
                        }
                        else {
                            BuiltApplication builtApplication = null;
                            try {
                                builtApplication  = Built.application(getContext(), "bltb6c6685a753714b6");
                            }
                            catch(Exception e) {
                                Log.d("PM", e.getMessage());
                            }
                            BuiltClass projectClass = builtApplication.classWithUid("built_io_application_user");
                            BuiltQuery projectQuery = projectClass.query();
                            projectQuery.where("email", requester_email);
                            projectQuery.execInBackground(new QueryResultsCallBack() {
                                @Override
                                public void onCompletion(BuiltConstant.ResponseType responseType, QueryResult queryResultObject, BuiltError error) {
                                    if (error == null) {
                                        List<BuiltObject> objects = queryResultObject.getResultObjects();
                                        Log.d("PM", "" + objects.size());
                                        for (BuiltObject object : objects) {
                                            Log.d("PM", "" + object.get("email"));
                                            requester_name = object.get("first_name") + " " + object.get("last_name");
                                            requester_mobile = "" + object.get("mobile");
                                            new BackgroundInsertRequestOperation().execute();
                                        }
                                    } else {
                                        Log.d("PM", "Request error: " +error.getErrorMessage());
                                        Toast.makeText(getActivity(), "Your request was not sent!" +error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //listener for search operation
        Button searchBtn = (Button) view.findViewById(R.id.search);
        final EditText searchFromView = (EditText) view.findViewById(R.id.airport_from);
        final EditText searchToView = (EditText) view.findViewById(R.id.airport_to);
        final EditText searchDateView = (EditText) view.findViewById(R.id.dateField);
        final RecyclerView recycleView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final LinearLayout newTraLayout = (LinearLayout) view.findViewById(R.id.newTravelLayout);

        TimeZone pst = TimeZone.getTimeZone("America/Los_Angeles");
        final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        isoDateFormat.setTimeZone(pst);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("PM", "listener");
                recycleView.setVisibility(View.VISIBLE);
                newTraLayout.setVisibility(View.GONE);
                searchFrom = searchFromView.getText().toString();
                searchTo = searchToView.getText().toString();
                Log.d("PM", "values: " +searchFrom +searchTo);
                try {
                    //searchDate = "" +isoDateFormat.parse(searchDateView.getText().toString());
                    searchDate = searchDateView.getText().toString();
                    Log.d("PM", "Search date: " +searchDate);
                } catch (Exception e) {
                    Log.d("PM", "Search date: " +e.getMessage());
                }
                new BackgroundSearchOperation().execute();
            }
        });

        fetchTravellersData();

        return view;
    }

    private void testData() {
        Travellers travellers = new Travellers("Mad Max: Fury Road", "San Jose", "2015", "San Francisco", "2015", "prash@m.com", "508");
        travellersList.add(travellers);

        travellers = new Travellers("Prash", "San Bruno", "2016", "Los Angeles", "2017", "prash@a.com", "408");
        travellersList.add(travellers);

        travellers = new Travellers("Prash", "San Bruno", "2016", "Los Angeles", "2017", "prash@a.com", "408");
        travellersList.add(travellers);

        travellers = new Travellers("Prash", "San Bruno", "2016", "Los Angeles", "2017", "prash@a.com", "408");
        travellersList.add(travellers);

        tAdapter.notifyDataSetChanged();
    }

    private void fetchTravellersData() {
        new BackgroundFetchOperation().execute();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private One.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final One.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class BackgroundFetchOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            travellersList.clear();
            TimeZone pst = TimeZone.getTimeZone("America/Los_Angeles");
            DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            isoDateFormat.setTimeZone(pst);
            final String currentDate = isoDateFormat.format(new Date());
            final DateFormat onlyDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            BuiltApplication builtApplication = null;
            try {
                builtApplication  = Built.application(getContext(), "bltb6c6685a753714b6");
            }
            catch(Exception e) {
                Log.d("PM", e.getMessage());
            }
            BuiltQuery projectQuery = builtApplication.classWithUid("travellers").query();
            projectQuery.greaterThanOrEqualTo("departure_date", currentDate);
            projectQuery.execInBackground(new QueryResultsCallBack() {

                @Override
                public void onCompletion(BuiltConstant.ResponseType responseType, QueryResult queryResultObject, BuiltError error) {
                    if(error == null){
                        List<BuiltObject> objects = queryResultObject.getResultObjects();
                        Log.d("PM", "Data fetched successfully" +objects.toArray().length +currentDate);
                        Travellers travellers = null;
                        for (BuiltObject object : objects) {
                            try {
                                travellers = new Travellers("" +object.get("traveller_name"), "" +object.get("from_city"), onlyDateFormat.format(onlyDateFormat.parse("" +object.get("departure_date"))), "" +object.get("to_city"), onlyDateFormat.format(onlyDateFormat.parse("" +object.get("arrival_date"))), "" +object.get("traveller_email"), "" +object.get("traveller_mobile"));
                            } catch (ParseException e) {
                                Log.d("PM", "ParseException: " + e.getMessage());
                            }
                            travellersList.add(travellers);
                        }
                        tAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.d("PM", "Fetch error: " + error.toString());
                    }
                }
            });
            return "Fetched";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("PM", "Done " + result);
        }
    }

    public class BackgroundSearchOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            travellersList.clear();
            final DateFormat onlyDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

            BuiltApplication builtApplication = null;
            try {
                builtApplication  = Built.application(getContext(), "bltb6c6685a753714b6");
            }
            catch(Exception e) {
                Log.d("PM", e.getMessage());
            }

            BuiltQuery queryFrom = builtApplication.classWithUid("travellers").query();
            queryFrom.where("from_city", searchFrom);
            BuiltQuery queryTo = builtApplication.classWithUid("travellers").query();
            queryTo.where("to_city", searchTo);
            BuiltQuery queryDate = builtApplication.classWithUid("travellers").query();
            queryDate.greaterThanOrEqualTo("departure_date", searchDate);
            BuiltQuery mainQuery = builtApplication.classWithUid("travellers").query();

            ArrayList queryList = new ArrayList();
            queryList.add(queryFrom);
            queryList.add(queryTo);
            queryList.add(queryDate);

            mainQuery.and(queryList);

            /*projectQuery.greaterThanOrEqualTo("departure_date", searchDate);
            projectQuery.addQuery("from_city", searchFrom);
            projectQuery.addQuery("to_city", searchTo);*/

            mainQuery.execInBackground(new QueryResultsCallBack() {

                @Override
                public void onCompletion(BuiltConstant.ResponseType responseType, QueryResult queryResultObject, BuiltError error) {
                    if(error == null){
                        List<BuiltObject> objects = queryResultObject.getResultObjects();
                        Log.d("PM", "Data fetched successfully" +objects.toArray().length);
                        Travellers travellers = null;
                        for (BuiltObject object : objects) {
                            try {
                                travellers = new Travellers("" +object.get("traveller_name"), "" +object.get("from_city"), onlyDateFormat.format(onlyDateFormat.parse("" +object.get("departure_date"))), "" +object.get("to_city"), onlyDateFormat.format(onlyDateFormat.parse("" +object.get("arrival_date"))), "" +object.get("traveller_email"), "" +object.get("traveller_mobile"));
                            } catch (ParseException e) {
                                Log.d("PM", "ParseException: " + e.getMessage());
                            }
                            travellersList.add(travellers);
                        }
                        tAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.d("PM", "Fetch error: " + error.toString());
                    }
                }
            });
            return "Searched";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("PM", "Done " + result);
        }
    }

    public class BackgroundInsertRequestOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            BuiltApplication builtApplication = null;
            try {
                builtApplication  = Built.application(getContext(), "bltb6c6685a753714b6");
            }
            catch(Exception e) {
                Log.d("PM", e.getMessage());
            }

            BuiltObject projectObject = builtApplication.classWithUid("requests").object();

            projectObject.set("requester_name", requester_name);
            projectObject.set("requester_email", requester_email);
            projectObject.set("traveller_email", traveller_email);
            projectObject.set("requester_mobile", requester_mobile);
            projectObject.set("comments", comments);
            projectObject.set("comment_date", comment_date);

            projectObject.saveInBackground(new BuiltResultCallBack() {
                @Override
                public void onCompletion(BuiltConstant.ResponseType responseType, BuiltError error) {
                    if (error == null) {
                        Log.d("PM", "Successfully sent");
                    } else {
                        Log.d("PM", "Insertion error: " + error.toString());
                    }
                }
            });
            return "Requested";
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("PM", "Done " + result);
            Toast.makeText(getActivity(), "Request sent to recipient!", Toast.LENGTH_LONG).show();
        }
    }
}