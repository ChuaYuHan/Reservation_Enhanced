package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Presentation;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    CheckBox checkBox;
    Button btReserve;
    Button btReset;
    EditText etDate;
    EditText etTime;
    String msg;

    int theYear, theMonth, theDay, theHour, theMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);
        etDate = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);

        Calendar now = Calendar.getInstance();
        theYear = now.get(Calendar.YEAR);
        theMonth = now.get(Calendar.MONTH);
        theDay = now.get(Calendar.DAY_OF_MONTH);
        theHour = now.get(Calendar.HOUR_OF_DAY);
        theMin = now.get(Calendar.MINUTE);
        etDate.setText(theDay + "/" + (theMonth + 1) + "/" + theYear);
        etTime.setText(theHour + ":" + theMin);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                } else {
                    isSmoke = "non-smoking";
                }

                msg = "New Reservation\n";
                msg += "Name: " + etName.getText().toString() + "\n";
                msg += "Smoking: " + isSmoke + "\n";
                msg += "Size: " + etSize.getText().toString() + "\n";
                msg += "Date: " + etDate.getText().toString() + "\n";
                msg += "Time: " + etTime.getText().toString();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(msg);
                myBuilder.setCancelable(false);

                // Configure the 'positive' button
                myBuilder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);
                etDate.setText("");
                etTime.setText("");

                Calendar now = Calendar.getInstance();
                theYear = now.get(Calendar.YEAR);
                theMonth = now.get(Calendar.MONTH);
                theDay = now.get(Calendar.DAY_OF_MONTH);
                theHour = now.get(Calendar.HOUR_OF_DAY);
                theMin = now.get(Calendar.MINUTE);
                etDate.setText(theDay + "/" + (theMonth + 1) + "/" + theYear);
                etTime.setText(theHour + ":" + theMin);


            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        theYear = year;
                        theMonth = month;
                        theDay = dayOfMonth;

                        etDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };
                    DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, theYear, theMonth, theDay);

                    myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        theHour = hourOfDay;
                        theMin = minute;

                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, theHour, theMin, true);

                myTimeDialog.show();
            }
        });

    }

    @Override
    protected void onPause () {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", etName.getText().toString());
        edit.putString("tel", etTelephone.getText().toString());
        edit.putString("size", etSize.getText().toString());
        edit.putString("smoking", checkBox.getText().toString());
        edit.putString("date", etDate.getText().toString());
        edit.putString("time", etTime.getText().toString());
        edit.commit();
    }

    @Override
    protected void onResume () {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", ""));
        etTelephone.setText(prefs.getString("tel", ""));
        etSize.setText(prefs.getString("size", ""));
        checkBox.setChecked(prefs.getBoolean("smoking", false));
        etDate.setText(prefs.getString("date", ""));
        etTime.setText(prefs.getString("time", ""));

    }

}