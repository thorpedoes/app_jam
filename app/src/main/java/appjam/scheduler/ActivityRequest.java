package appjam.scheduler;


import android.support.v4.util.ArrayMap;

public class ActivityRequest {

    private ArrayMap<String, Integer> map = new ArrayMap<>();

    public ActivityRequest() {
        fillMap();
    }

    public int activityToInt(String activity) {
        return map.get(activity);
    }


    private void fillMap() {
        map.put("GET_EVENT_INFO", 0);
    }
}
