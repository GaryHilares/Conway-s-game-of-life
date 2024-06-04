package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a single instance of a log of events, which is shared in the whole program.
// EventLog class adapted from UBC CPSC team's EventLog class
public class EventLog implements Iterable<Event> {
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: Creates a new instance of an EventLog.
    private EventLog() {
        events = new ArrayList<>();
    }

    // EFFECTS: Produces the program-wide instance of EventLog. Creates it if it does not exist.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    // MODIFIES: this
    // EFFECTS: Adds an Event to the current EventLog.
    public void logEvent(Event e) {
        events.add(e);
    }

    // MODIFIES: this
    // EFFECTS: Clears the log of events.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: Produces an iterator over the events in this EventLog.
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}