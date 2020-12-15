package misc;

import java.util.Calendar;

import UseCase.EventManager;

public class TimeClass {

    public TimeClass() {}

    /**
     * Helper method: Returns true iff the event's start time and end time don't clash and there
     * is no event overlap
     * @param startTime the start time of the event we want to compare to
     * @param endTime the end time of the event we want to compare to
     * @param event the name of the event we are comparing
     * @return true iff the above conditions are met
     */
    public boolean compareTime(Calendar startTime, Calendar endTime, String event) {
        return startTime.compareTo(EventManager.EventList.get(event).getStart_time()) != 0
                && endTime.compareTo(EventManager.EventList.get(event).getEnd_time()) != 0
                && (startTime.compareTo(EventManager.EventList.get(event).getStart_time()) <= 0 ||
                startTime.compareTo(EventManager.EventList.get(event).getEnd_time()) >= 0)
                && (endTime.compareTo(EventManager.EventList.get(event).getStart_time()) <= 0
                || endTime.compareTo(EventManager.EventList.get(event).getEnd_time()) >= 0);
    }

    /**
     * Returns an integer representing user input between min and max (inclusive)
     *
     * @param input the input for the time
     * @param start the start of the time segment
     * @param end   the end of the time segment
     * @return an integer of the time segment given in the parameters.
     */

    public int returnTimeSegment(String input, int start, int end) {
        return Integer.parseInt(input.substring(start, end));
    }

    /**
     * Returns a Calendar representing user input between min and max (inclusive)
     *
     * @param input is the string format of the time
     */
    public void getTime(String input, Calendar time) {

        time.set(returnTimeSegment(input, 0, 4),
                returnTimeSegment(input, 5, 7) - 1,
                returnTimeSegment(input, 8, 10));
        if (returnTimeSegment(input, 11, 13) == 12) {
            time.set(Calendar.HOUR, 0);
        } else {
            time.set(Calendar.HOUR, returnTimeSegment(input, 11, 13));
        }
        time.set(Calendar.MINUTE, returnTimeSegment(input, 14, 16));
        if (input.substring(17, 19).equals("am")) {
            time.set(Calendar.AM_PM, Calendar.AM);
        } else {
            time.set(Calendar.AM_PM, Calendar.PM);
        }
        time.set(Calendar.SECOND, 0);
        time.set(Calendar.MILLISECOND, 0);
    }
}
