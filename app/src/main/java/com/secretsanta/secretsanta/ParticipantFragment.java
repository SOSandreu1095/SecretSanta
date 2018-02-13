package com.secretsanta.secretsanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


public class ParticipantFragment extends Fragment {

    private FloatingActionButton btnSaveParticipant;
    private EditText txtPersonName;
    private EditText txtPersonBirthday;
    private EditText txtPersonEmail;
    private EditText txtPersonLikes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participant, container, false);

        //Areas
        this.txtPersonName = (EditText) v.findViewById(R.id.person_name_text);
        this.txtPersonBirthday = (EditText) v.findViewById(R.id.txtdate);
        this.txtPersonEmail = (EditText) v.findViewById(R.id.email_address_text);
        this.txtPersonLikes = (EditText) v.findViewById(R.id.likes_text);
        this.btnSaveParticipant = (FloatingActionButton) v.findViewById(R.id.btnSaveParticipant);
        this.btnSaveParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewParticipant();
            }
        });




        return v;
    }

    private void saveNewParticipant(){
        if (checkFields(txtPersonName) && checkFields(txtPersonBirthday) && checkFields(txtPersonEmail)) {

            int day = Integer.parseInt(txtPersonBirthday.getText().toString().substring(0,txtPersonBirthday.getText().toString().indexOf("-")));
            int month = Integer.parseInt(txtPersonBirthday.getText().toString().substring(txtPersonBirthday.getText().toString().indexOf("-")+1,
                    txtPersonBirthday.getText().toString().indexOf("-",txtPersonBirthday.getText().toString().indexOf("-")+1)));
            int year = Integer.parseInt(txtPersonBirthday.getText().toString().substring(txtPersonBirthday.getText().toString().indexOf("-",txtPersonBirthday.getText().toString().indexOf("-")+1)+1,
                    txtPersonBirthday.getText().toString().length()));

            int age = getAge(year, month, day);
            //Toast.makeText(getContext(), age, Toast.LENGTH_LONG).show();
            if (age > 0) {
                ParticipantLab participantLab = ParticipantLab.get(getContext());
                Person p = new Person();
                p.setName(txtPersonName.getText().toString());
                p.setAge(age);
                p.setEmail(txtPersonEmail.getText().toString());

                participantLab.addParticipant(p);
                Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_LONG).show();

                Log.v("NAME", p.getName());
                Log.v("AGE", Integer.toString(p.getAge()));
                Log.v("EMAIL", p.getEmail());

                Intent i = new Intent(getActivity(), ParticipantListActivity.class);
                startActivity( i );
            } else {
                Toast.makeText(getContext(), "Insert a valid date", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean checkFields(EditText editText){
        if (editText.getText().toString().length() == 0){
            editText.requestFocus();
            Toast.makeText(getContext(), "Complete the field", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void onStart(){
        super.onStart();

        EditText txtDate=(EditText) getView().findViewById(R.id.txtdate);
        txtDate.setKeyListener(null);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DateDialog dialog = new DateDialog(v);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });

    }


    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        return age;
    }

}
