package me.bubbles.murderder.games;

import me.bubbles.murderder.util.HashSetBuilder;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;

public class Disasters extends GameHandler{

    public Disasters() {
        super(TickEvent.ClientTickEvent.class);
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public HashSet<String> waitFor() {
        return new HashSetBuilder<String>()
                .add("Endure an onslaught of natural and unnatural disasters!")
                .get();
    }

    @Override
    public HashSet<String> endAt() {
        return new HashSetBuilder<String>()
                .add("Winners (", "Nobody survived!")
                .get();
    }

    @Override
    public GameHandler newInstance() {
        return new Disasters();
    }

}
