package com.smartcomet.e_health;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

public class SingleDoctorFragment extends Fragment {

    public static final String TAG = "SingleDoctorFragment";

    private static final String TAG_FIRST_NAME = "first_name";
    private static final String TAG_LAST_NAME = "last_name";
    private static final String TAG_WORK_LOCATION = "work_location";
    private static final String TAG_PHONE_NUMBER = "phone_number";
    private static final String TAG_EMAIL = "email";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View V = inflater.inflate(R.layout.single_doctor_fragment, container, false);

        return V;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get JSON values from PeopleFragment
        Bundle bundle = getArguments();

        if(bundle != null){

            String first_name = getArguments().getString(TAG_FIRST_NAME);
            String last_name = getArguments().getString(TAG_LAST_NAME);
            String work_location = getArguments().getString(TAG_WORK_LOCATION);
            String phone_number = getArguments().getString(TAG_PHONE_NUMBER);
            String email = getArguments().getString(TAG_EMAIL);

            // Displaying all values on the screen
            TextView lblfirstname = (TextView) getView().findViewById(R.id.first_name_label);
            TextView lbllastname = (TextView) getView().findViewById(R.id.last_name_label);
            TextView lblworklocation = (TextView) getView().findViewById(R.id.work_location_label);
            TextView lblphonenumber = (TextView) getView().findViewById(R.id.phone_number_label);
            TextView lblemail = (TextView) getView().findViewById(R.id.email_label);

            lblfirstname.setText(first_name);
            lbllastname.setText(last_name);
            lblworklocation.setText(work_location);
            lblphonenumber.setText(phone_number);
            lblemail.setText(email);
        }

    }
}
