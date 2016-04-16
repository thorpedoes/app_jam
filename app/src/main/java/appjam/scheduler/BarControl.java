package appjam.scheduler;

import android.content.Context;
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

    public BarControl(CalendarEvent ce) {
        m_ce = ce;
    }

    public void update(TableLayout tl) {
        Calendar c = Calendar.getInstance();
        DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDateTime = new Date();

        TableRow row = new TableRow(tl.getContext());
        LinearLayout ll = new LinearLayout(row.getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);

        ll.addView(getIconOfEvent(ll.getContext()));

        ll.addView(getBarOfEvent(ll.getContext()));

        row.addView(ll);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        int density = (int)tl.getResources().getDisplayMetrics().density;
        int pad = 20 * density;

        row.setPadding(pad, pad, 0, 0);
        tl.addView(row);
            //if(currentDateTime+difference > currentDateTime+ce.getDuration)
            //  change
        }

    private ImageView getBarOfEvent(Context context) {
        ImageView result = new ImageView(context);
        result.setImageResource(R.drawable.hb);
        result.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        int density = (int) context.getResources().getDisplayMetrics().density;
        int padding = 5 * density;
        result.setPadding(padding, 0, 0, 0);
        return result;
    }


    private ImageView getIconOfEvent(Context context) {
        ImageView result = new ImageView(context);
        result.setImageResource(R.drawable.square);
        result.setLayoutParams(new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        return result;
    }
}
