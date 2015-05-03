package com.smartcomet.e_health;

import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeopleFragment extends ListFragment {

    public static final String TAG = "people";
    private ProgressDialog progressDialog;

    private static String url = "http://10.0.2.2:8080/findDoctors";

    private static final String TAG_DOCTORS = "doctors";
    private static final String TAG_FIRST_NAME = "first_name";
    private static final String TAG_LAST_NAME = "last_name";
    private static final String TAG_WORK_LOCATION = "work_location";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_EMAIL = "email";

    JSONArray doctors = null;

    ArrayList<HashMap<String, String>> doctorsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.people_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context context;
        context = getActivity();

        doctorsList = new ArrayList<>();

        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String first_name = ((TextView) view.findViewById(R.id.first_namelbl))
                        .getText().toString();
                String last_name = ((TextView) view.findViewById(R.id.last_namelbl))
                        .getText().toString();
                String work_location = ((TextView) view.findViewById(R.id.work_locationlbl))
                        .getText().toString();
                String phone_number = ((TextView) view.findViewById(R.id.phone_numberlbl))
                        .getText().toString();
                String email = ((TextView) view.findViewById(R.id.emaillbl))
                        .getText().toString();

                /*mFragment = getFragmentManager().findFragmentByTag(SingleDoctorFragment.TAG);
                if (mFragment == null) {
                    Bundle args = new Bundle();
                    args.putString(TAG_FIRST_NAME, first_name);
                    args.putString(TAG_LAST_NAME, last_name);
                    args.putString(TAG_WORK_LOCATION, work_location);
                    args.putString(TAG_PHONE_NUMBER, phone_number);
                    args.putString(TAG_EMAIL, email);

                    SingleDoctorFragment singleDoctorFragment = new SingleDoctorFragment();
                    singleDoctorFragment.setArguments(args);
                }
                getFragmentManager().beginTransaction().add(R.id.container, mFragment, SingleDoctorFragment.TAG).commit();*/

                Intent intent = new Intent(getActivity().getBaseContext(),
                        MainActivity.class);
                intent.putExtra(TAG_FIRST_NAME, first_name);
                intent.putExtra(TAG_LAST_NAME, last_name);
                intent.putExtra(TAG_WORK_LOCATION, work_location);
                intent.putExtra(TAG_PHONE_NUMBER, phone_number);
                intent.putExtra(TAG_EMAIL, email);
                getActivity().startActivity(intent);

            }
        });

        new GetDoctors(context).execute();
    }

    private class GetDoctors extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        public GetDoctors(Context context){
            this.mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(this.mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ServiceHandler serviceHandler = new ServiceHandler();

            String jsonStr = serviceHandler.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    doctors = jsonObject.getJSONArray(TAG_DOCTORS);

                    for (int i = 0; i < doctors.length(); i++) {
                        JSONObject c = doctors.getJSONObject(i);

                        String first_name = c.getString(TAG_FIRST_NAME);
                        String last_name = c.getString(TAG_LAST_NAME);
                        String work_location = c.getString(TAG_WORK_LOCATION);
                        String phone_number = c.getString(TAG_PHONE_NUMBER);
                        String email = c.getString(TAG_EMAIL);

                        // Phone node is JSON Object
                       /* JSONObject phone = c.getJSONObject(TAG_PHONE);
                        String mobile = phone.getString(TAG_PHONE_MOBILE);
                        String home = phone.getString(TAG_PHONE_HOME);
                        String office = phone.getString(TAG_PHONE_OFFICE);*/

                        // tmp hashmap for single contact
                        HashMap<String, String> doctor = new HashMap<>();

                        // adding each child node to HashMap key => value
                        doctor.put(TAG_FIRST_NAME, first_name);
                        doctor.put(TAG_LAST_NAME, last_name);
                        doctor.put(TAG_WORK_LOCATION, work_location);
                        doctor.put(TAG_PHONE_NUMBER, phone_number);
                        doctor.put(TAG_EMAIL, email);

                        // adding contact to contact list
                        doctorsList.add(doctor);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    this.mContext, doctorsList,
                    R.layout.list_doctors, new String[]{TAG_FIRST_NAME, TAG_LAST_NAME, TAG_WORK_LOCATION, TAG_PHONE_NUMBER,
                    TAG_EMAIL}, new int[]{R.id.first_namelbl, R.id.last_namelbl, R.id.work_locationlbl, R.id.phone_numberlbl,
                    R.id.emaillbl});

            setListAdapter(adapter);
        }
    }
}