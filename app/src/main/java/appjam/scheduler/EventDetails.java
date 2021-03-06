package appjam.scheduler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EventDetails extends AppCompatActivity {

    private Button startDateButton;
    private Button endDateButton;
    private Button startTimeButton;
    private Button endTimeButton;

    private ArrayList<ImageView> images = new ArrayList<>();

    private String m_selectedIcon = "Image1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        registerButtons(savedInstanceState);
    }

    private void registerButtons(Bundle savedInstanceState) {
        ImageButton cancelButton = (ImageButton) findViewById(R.id.cancelButton);
        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);

        Calendar cal = Calendar.getInstance();

        startDateButton = (Button) findViewById(R.id.startDateButton);
        startDateButton.setText(calendarDate(cal));

        startTimeButton = (Button) findViewById(R.id.startTimeButton);
        startTimeButton.setText(calendarTime(cal));

        endDateButton = (Button) findViewById(R.id.endDateButton);
        endDateButton.setText(calendarDate(cal));

        endTimeButton = (Button) findViewById(R.id.endTimeButton);
        endTimeButton.setText(calendarTime(cal));

        if(cancelButton != null) {
            cancelButton.setBackgroundResource(R.drawable.cancel_icon);
            cancelButton.setOnClickListener(getExitListener());
        }
        if(saveButton != null) {
            saveButton.setBackgroundResource(R.drawable.save_button);
            saveButton.setOnClickListener(getSaveListener());
        }

        registerImages();
    }

    private String calendarDate(Calendar cal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
        return dateFormat.format(cal.getTime());
    }

    private String calendarTime(Calendar cal) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(cal.getTime());
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
                    resultData.putExtra("newEvent", getEnteredEvent());
                    setResult(RESULT_OK, resultData);
                    finish();
                }
            }
        };
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFrag = new DatePickerFragment();
        DatePickerFragment.setButton(v.getId());
        newFrag.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFrag = new TimePickerFragment();
        TimePickerFragment.setButton(v.getId());
        newFrag.show(getSupportFragmentManager(), "timePicker");
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
        Calendar startDateTime = getStartDateTime();
        Calendar endDateTime = getEndDateTime();
        String title = getEnteredTitle();

        result.setStartTime(startDateTime);
        result.setEndTime(endDateTime);
        result.setIcon(m_selectedIcon);
        result.setTitle(title);
        return result;
    }

    private Calendar getStartDateTime() {
        Calendar startDate = DatePickerFragment.getStartDate();
        Calendar startTime = TimePickerFragment.getStartTime();
        startDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
        startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
        startTime.set(Calendar.SECOND, 0);
        return startDate;
    }

    private Calendar getEndDateTime() {
        Calendar endDate = DatePickerFragment.getEndDate();
        Calendar endTime = TimePickerFragment.getEndTime();
        endDate.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
        endDate.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
        endDate.set(Calendar.SECOND, 0);
        return endDate;
    }

    private String getEnteredTitle() {
        EditText titleText = (EditText) findViewById(R.id.nameEntry);
        assert titleText != null;
        return titleText.getText().toString();
    }

    private void makeErrorToast(CharSequence msg) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    private void registerImages() {
        ImageView icon1 = (ImageView) findViewById(R.id.image1);
        assert icon1 != null;
        icon1.setOnClickListener(getHighlightClicker());
        images.add(icon1);

        ImageView icon2 = (ImageView) findViewById(R.id.image2);
        assert icon2 != null;
        icon2.setOnClickListener(getHighlightClicker());
        images.add(icon2);

        ImageView icon3 = (ImageView) findViewById(R.id.image3);
        assert icon3 != null;
        icon3.setOnClickListener(getHighlightClicker());
        images.add(icon3);

        ImageView icon4 = (ImageView) findViewById(R.id.image4);
        assert icon4 != null;
        icon4.setOnClickListener(getHighlightClicker());
        images.add(icon4);

        ImageView icon5 = (ImageView) findViewById(R.id.image5);
        assert icon5 != null;
        icon5.setOnClickListener(getHighlightClicker());
        images.add(icon5);
    }

    private View.OnClickListener getHighlightClicker() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView iv = (ImageView) v;
                iv.setColorFilter(Color.argb(175, 192, 192, 192));
                deselectAllOtherIcons(v);
                m_selectedIcon = idToString(v.getId());
            }
        };
    }

    private void deselectAllOtherIcons(View v) {
        for(ImageView iv : images) {
            if(iv != v) {
               iv.setColorFilter(Color.argb(0, 0, 0, 0));
            }
        }
    }

    private String idToString(int id) {
        if(id == R.id.image1) {
            return "Image1";
        } else if(id == R.id.image2) {
            return "Image2";
        } else if(id == R.id.image3) {
            return "Image3";
        } else if(id == R.id.image4) {
            return "Image4";
        } else if(id == R.id.image5) {
            return "Image5";
        } else {
            return "";
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private static Calendar m_selectedStartDate = Calendar.getInstance();
        private static Calendar m_selectedEndDate = Calendar.getInstance();
        private static int mappedButtonID = R.id.startTimeButton;

        @Override
        public Dialog onCreateDialog(Bundle savedInstance) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Button startDateButton = (Button) this.getActivity().findViewById(R.id.startDateButton);
            Button endDateButton = (Button) this.getActivity().findViewById(R.id.endDateButton);
            startDateButton.setText(calendarDate(m_selectedStartDate));
            endDateButton.setText(calendarDate(m_selectedEndDate));
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            switch(mappedButtonID) {
                case R.id.startDateButton:
                    m_selectedStartDate.set(Calendar.YEAR, year);
                    m_selectedStartDate.set(Calendar.MONTH, month);
                    m_selectedStartDate.set(Calendar.DAY_OF_MONTH, day);
                    Log.d("DATE SET", "startDateButton");
                    break;
                case R.id.endDateButton:
                    m_selectedEndDate.set(Calendar.YEAR, year);
                    m_selectedEndDate.set(Calendar.MONTH, month);
                    m_selectedEndDate.set(Calendar.DAY_OF_MONTH, day);
                    Log.d("DATE SET", "endDateButton");
                    break;
                default:
                    Log.d("WARNING", "Invalid ID: " + Integer.toString(view.getId()));
            }
        }

        public static Calendar getStartDate() {
            return m_selectedStartDate;
        }

        public static Calendar getEndDate() {
            return m_selectedEndDate;
        }

        public static void setButton(int id) {
            mappedButtonID = id;
        }

        private String calendarDate(Calendar cal) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            return dateFormat.format(cal.getTime());
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        private static Calendar c = Calendar.getInstance();

        private static Calendar m_selectedStartTime = Calendar.getInstance();
        private static Calendar m_selectedEndTime = Calendar.getInstance();
        private static Calendar m_selectedStartDate = Calendar.getInstance();
        private static Calendar m_selectedEndDate = Calendar.getInstance();

        private static int mappedButtonID = R.id.startTimeButton;

        @Override
        public Dialog onCreateDialog(Bundle savedInstance) {
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            Button startTimeButton = (Button) this.getActivity().findViewById(R.id.startTimeButton);
            Button endTimeButton = (Button) this.getActivity().findViewById(R.id.endTimeButton);
            startTimeButton.setText(calendarTime(m_selectedStartTime));
            endTimeButton.setText(calendarTime(m_selectedEndTime));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            switch(mappedButtonID) {
                case R.id.startTimeButton:
                    m_selectedStartTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    m_selectedStartTime.set(Calendar.MINUTE, minute);
                    Log.d("TIME SET", "startTime");
                    break;
                case R.id.endTimeButton:
                    m_selectedEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    m_selectedEndTime.set(Calendar.MINUTE, minute);
                    Log.d("TIME SET", "endTime");
                    break;
                default:
                    Log.d("TIME SET", "INVALID");
            }
        }


        public static Calendar getStartTime() {
            return m_selectedStartTime;
        }
        public static Calendar getEndTime() {
            return m_selectedEndTime;
        }
        public static Calendar getStartDate() {
            return m_selectedStartDate;
        }
        public static Calendar getEndDate() {
            return m_selectedEndDate;
        }

        public static void setButton(int id) {
            mappedButtonID = id;
        }

        private String calendarTime(Calendar cal) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
            return timeFormat.format(cal.getTime());
        }
    }

}
