package appjam.scheduler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BarControl {
    private CalendarEvent m_ce;
    private double currentPerc = 1.0;
    private ImageView m_barImg;
    private boolean m_finished = false;
    private Activity m_currentActivity;

    public BarControl(CalendarEvent ce, TableLayout tl, Activity activity) {
        m_ce = ce;
        m_barImg = getNewBar(tl);
        m_currentActivity = activity;
        update(tl, true);
    }

    public void update(TableLayout tl) {
        update(tl, false);
    }

    public void update(TableLayout tl, boolean initial) {
        currentPerc = findPercentFull();
        if(currentPerc > 0.01) {

            TableRow row = new TableRow(tl.getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            row.setOrientation(TableRow.HORIZONTAL);
            LinearLayout ll = new LinearLayout(row.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            ll.addView(getTrashCanOfEvent(row.getContext()));
            ll.addView(getIconOfEvent(ll.getContext()));

            if(!initial && m_barImg.getHeight() > 0 && m_barImg.getWidth() > 0) {
                ll.addView(getBarOfEvent(ll.getContext()));
            } else {
                m_barImg = getNewBar(tl);
                ll.addView(m_barImg);
            }

            row.addView(ll);

            int density = (int) tl.getResources().getDisplayMetrics().density;
            int pad = 20 * density;
            row.setPadding(pad, pad, 0, 0);

            tl.addView(row);
        } else {
            m_finished = true;
        }
    }

    public boolean finished() {
        Calendar current = Calendar.getInstance();
        return current.after(m_ce.getEndTime()) || m_finished;
    }

    public CalendarEvent getEvent() {
        return m_ce;
    }

    private double findPercentFull() {
        Calendar current = Calendar.getInstance();
        long duration = m_ce.getEndTime().getTimeInMillis() - m_ce.getStartTime().getTimeInMillis();
        long timeElapsed = current.getTimeInMillis() - m_ce.getStartTime().getTimeInMillis();

        if(duration == 0) {
            return 0.0;
        } else if(timeElapsed < 0){
            return 1;
        } else {
            return 1 - ((double)timeElapsed/duration);
        }
    }

   private void setBarPadding(ImageView image, Context context) {
       int density = (int) context.getResources().getDisplayMetrics().density;
       int padding = 5 * density;
       image.setPadding(padding, 0, 0, 0);
   }

    private ImageView getBarOfEvent(Context context) {
        Bitmap src = ((BitmapDrawable) m_barImg.getDrawable()).getBitmap();
        double percentFull = findPercentFull();
        int startX = 0;
        int startY = 0;
        int width = (int) (src.getWidth()* percentFull);
        int height = m_barImg.getHeight();
        Bitmap cropped = Bitmap.createBitmap(src, startX, startY, width, height, null, false);

        ImageView result = new ImageView(context);
        result.setImageBitmap(cropped);
        setBarPadding(result, context);
        result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return result;
    }


    private ImageView getIconOfEvent(Context context) {
        ImageView result = new ImageView(m_currentActivity.getApplicationContext());
        result.setImageResource(titleToIcon(m_ce.getIcon()));
        result.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int duration = Toast.LENGTH_SHORT;
                String msg = getEvent().getTitle() + ": " + findTimeLeft();
                Toast toast = Toast.makeText(m_currentActivity.getApplicationContext(), msg, duration);
                TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                if (tv != null) tv.setGravity(Gravity.CENTER);
                toast.show();
            }

            private String findTimeLeft() {
                String timeLeft = null;
                Calendar current = Calendar.getInstance();
                current.set(Calendar.SECOND, 0);
                long diff = getEvent().getEndTime().getTimeInMillis() - current.getTimeInMillis();
                if (diff > 86400000) {
                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy, h:mm a");
                    return "Ends " + sdf.format(getEvent().getEndTime().getTime());
                }

                long hour = (diff / 1000) / 60 / 60;
                long minute = (diff - (hour * 60 * 60 * 1000)) / 1000 / 60;

                timeLeft = "Ends in ";
                if (hour > 0) timeLeft += Integer.toString((int) hour) + "h, ";
                timeLeft += Integer.toString((int) minute) + "m";

                return timeLeft;
            }
        };
        result.setOnClickListener(clickListener);

        return result;
    }

    private ImageView getNewBar(TableLayout tl) {
        ImageView result = new ImageView(tl.getContext());
        result.setImageResource(R.drawable.hb1);
        result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return result;
    }

    private ImageView getTrashCanOfEvent(Context context) {
        ImageView result = new ImageView(context);
        result.setImageResource(R.drawable.remove_event_button);
        result.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_finished = true;
            }
        });
        return result;
    }

    private int titleToIcon(String iconString) {
        if(iconString.equals("Image1")) {
            return R.drawable.icon1;
        } else if(iconString.equals("Image2")) {
            return R.drawable.icon2;
        } else if(iconString.equals("Image3")) {
            return R.drawable.icon3;
        } else if(iconString.equals("Image4")) {
            return R.drawable.icon4;
        } else if(iconString.equals("Image5")) {
            return R.drawable.icon5;
        } else {
            return R.drawable.icon1;
        }
    }

}
