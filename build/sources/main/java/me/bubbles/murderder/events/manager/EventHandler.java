package me.bubbles.murderder.events.manager;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class EventHandler {

    private List<Class> event;

    public EventHandler(List<Class> event) {
        this.event=event;
    }

    public EventHandler(Class event) {
        List<Class> classes = new ArrayList<>();
        classes.add(event);
        this.event=classes;
    }

    public abstract void onEvent(Event event);

    public List<Class> getEvents() {
        return event;
    }

}
