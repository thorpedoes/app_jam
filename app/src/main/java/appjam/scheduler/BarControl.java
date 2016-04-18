package appjam.scheduler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        // hacky, later use ce more correctly
        return currentPerc <= .01;
    }

    private void drawInitial(TableLayout tl) {
        TableRow row = new TableRow(tl.getContext());
        LinearLayout ll = new LinearLayout(row.getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(getIconOfEvent(ll.getContext()));
        ll.addView(m_barImg);//getBarOfEvent(ll.getContext()));

        row.addView(ll);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        setBarPadding(m_barImg, tl.getContext());
        tl.addView(row);
    }

    private double findPercentFull() {
        Calendar c = Calendar.getInstance();
        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDateTime = new Date();
        // do stuff with m_ce, for now: hacky stuff
        currentPerc -= .01;
        return currentPerc;
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
