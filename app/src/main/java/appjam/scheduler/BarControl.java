package appjam.scheduler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Calendar;

public class BarControl {
    private CalendarEvent m_ce;
    private double currentPerc = 1.0;
    private ImageView m_barImg;
    private TableLayout m_parent;

    public BarControl(CalendarEvent ce, TableLayout tl) {
        m_ce = ce;
        m_parent = tl;
        m_barImg = new ImageView(tl.getContext());
        m_barImg.setImageResource(R.drawable.hb1);
        m_barImg.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        drawInitial(tl);
    }

    public ImageView getFullImage() {
        return m_barImg;
    }

    public void update(TableLayout tl) {
        currentPerc = findPercentFull();
        Log.v("PERC", Double.toString(currentPerc));
        if(currentPerc > 0.0) {
            TableRow row = new TableRow(tl.getContext());
            LinearLayout ll = new LinearLayout(row.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            ll.addView(getIconOfEvent(ll.getContext()));
            ll.addView(getBarOfEvent(ll.getContext()));

            row.addView(ll);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            int density = (int) tl.getResources().getDisplayMetrics().density;
            int pad = 20 * density;

            row.setPadding(pad, pad, 0, 0);

            tl.addView(row);
        }
    }

    public boolean finished() {
        Calendar current = Calendar.getInstance();
        return current.after(m_ce.getEndTime());
    }

    public CalendarEvent getEvent() {
        return m_ce;
    }

    private void drawInitial(TableLayout tl) {
        TableRow row = new TableRow(tl.getContext());
        LinearLayout ll = new LinearLayout(row.getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(getIconOfEvent(ll.getContext()));
        ll.addView(m_barImg);

        row.addView(ll);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        setBarPadding(m_barImg, tl.getContext());
        tl.addView(row);
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
        return result;
    }


    private ImageView getIconOfEvent(Context context) {
        ImageView result = new ImageView(context);
        result.setImageResource(R.drawable.square);
        result.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return result;
    }
}
