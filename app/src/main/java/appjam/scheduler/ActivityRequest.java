package appjam.scheduler;

public enum ActivityRequest {
    GET_EVENT_INFO, INVALID;

    public static ActivityRequest fromInt(int x) {
        switch(x) {
            case 0:
                return GET_EVENT_INFO;
            default:
                return INVALID;
        }
    }
}
