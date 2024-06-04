package model;

import java.util.Calendar;
import java.util.Date;


// Represents an event that can be logged.
// Event class adapted from UBC CPSC team's Event class
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    // EFFECTS: Creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    // EFFECTS: Produces the date and time at which this Event was created.
    public Date getDate() {
        return dateLogged;
    }

    // EFFECTS: Produces the description of this Event.
    public String getDescription() {
        return description;
    }

    // EFFECTS: Compares two Event instances by datetime and description.
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    // EFFECTS: Produces a hash that identifies this Event instance.
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    // EFFECTS: Produces a string that represents this Event instance.
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}