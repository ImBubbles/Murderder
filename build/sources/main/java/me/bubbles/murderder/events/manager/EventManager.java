package me.bubbles.murderder.events.manager;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class EventManager {

    private final HashSet<EventHandler> events = new HashSet<>();

    public EventManager() {
        addEvent(
                //new test()
        );
        new Subscriptions();
    }

    public void addEvent(EventHandler... events) {
        Collections.addAll(this.events, events);
    }

    public void removeEvent(EventHandler... events) {
        for(EventHandler event : events) {
            this.events.remove(event);
        }
    }

    public void onEvent(Event event) {
        events.stream()
                .filter(
                        eventHandler -> {
                            if(eventHandler.getEvents().isEmpty()) {
                                return true;
                            }
                            return eventHandler.getEvents().contains(event.getClass());
                        }
                )
                .collect(Collectors.toList())
                .forEach(eventObj -> eventObj.onEvent(event));
    }

    public HashSet<EventHandler> getEvents() {
        return events;
    }

}
