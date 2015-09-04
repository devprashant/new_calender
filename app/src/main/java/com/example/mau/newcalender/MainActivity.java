package com.example.mau.newcalender;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.example.mau.newcalender.database.ScheduleDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private ScheduleDataSource dataSource;
    ConnectionDetector cd ;
    Boolean isInternetPresent ;

    private static final String SCHEDULE = "schedule";
    private static final String TAG_SUBJECT_NAME = "subject_name";
    private static final String TAG_ROOM_NO = "room_no";
    private static final String TAG_SLOT = "slot";
    private static final String TAG_DAY = "day";

    private ArrayList<HashMap<String, String>> scheduleList;
    private ProgressDialog pDialog;
    private String url = "https://nodejs-calender-prashantdawar.c9.io/schedule.json";
    private JSONArray scheduleJSONArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleList = new ArrayList<>();

        dataSource = new ScheduleDataSource(this);

        //open database for r/w
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //detecting connection
        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            new GetSchedule().execute();
        } else {
            showFullSchedule();
        }

    }

    private void showFullSchedule() {
        Cursor data = dataSource.fullSchedule();

        data.moveToFirst();

        while (data.isAfterLast() ==  false){

            HashMap<String, String> scheduleMap = new HashMap<>();
            scheduleMap.put(TAG_SUBJECT_NAME, data.getString(1));
            scheduleMap.put(TAG_ROOM_NO, data.getString(4));
            scheduleMap.put(TAG_SLOT, data.getString(2));

            scheduleList.add(scheduleMap);
            data.moveToNext();
        }



        ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                scheduleList,
                R.layout.list_item,
                new String[] { TAG_SUBJECT_NAME, TAG_ROOM_NO, TAG_SLOT },
                new int[] { R.id.subjectName, R.id.room, R.id.slot });

        setListAdapter(adapter);
    }

    private class GetSchedule extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //show progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting it...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", " > " + jsonStr);

            if (jsonStr != null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    scheduleJSONArray = jsonObj.getJSONArray(SCHEDULE);

                    //Looping through full schedule
                    for (int i = 0; i < scheduleJSONArray.length(); i++){
                        JSONObject s = scheduleJSONArray.getJSONObject(i);

                        String subject_name = s.getString(TAG_SUBJECT_NAME);
                        String room_no = s.getString(TAG_ROOM_NO);
                        String slot = s.getString(TAG_SLOT);
                        String day = "monday";

                        dataSource.addSchedule(subject_name, day, slot, room_no);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldnot receive any data");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pDialog.isShowing())
                pDialog.dismiss();

            showFullSchedule();
        }
    }
}