package me.bubbles.murderder.games;

import me.bubbles.murderder.events.manager.EventHandler;

import java.util.List;

public abstract class GameHandler extends EventHandler implements IGameHandler {

    public GameHandler(List<Class> event) {
        super(event);
    }

    public GameHandler(Class event) {
        super(event);
    }

}
