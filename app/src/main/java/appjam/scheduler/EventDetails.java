package appjam.scheduler;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class EventDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        registerButtons(savedInstanceState);
    }

    private void registerButtons(Bundle savedInstanceState) {
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button startDateButton = (Button) findViewById(R.id.startDateButton);
        Button startTimeButton = (Button) findViewById(R.id.startTimeButton);
        Button endDateButton = (Button) findViewById(R.id.endDateButton);
        Button endTimeButton = (Button) findViewById(R.id.endTimeButton);
        if(cancelButton != null)
            cancelButton.setOnClickListener(getExitListener());
        if(saveButton != null)
            saveButton.setOnClickListener(getSaveListener());
    }

    private View.OnClickListener getExitListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        };
    }

    private View.OnClickListener getSaveListener() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                if(validEventEntered()) {
                    Intent resultData = new Intent();
                    resultData.putExtra("New event", getEnteredEvent());
                    setResult(RESULT_OK, resultData);
                    finish();
                }
            }
        };
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFrag = new TimePickerFragment();
        newFrag.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFrag = new DatePickerFragment();
        newFrag.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean validEventEntered() {
        boolean result = false;
        CalendarEvent toTest = getEnteredEvent();
        try {
            result = toTest.validEvent();
        } catch (EndBeforeStartException | BeforeTodayException | EmptyEventTitleException e) {
            makeErrorToast(e.getMessage());
        }
        return result;
    }

    private CalendarEvent getEnteredEvent() {
        CalendarEvent result = new CalendarEvent();
        // get and set all fields
        return result;
    }

    private void makeErrorToast(CharSequence msg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}
