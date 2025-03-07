package me.bubbles.murderder.games;

import java.util.HashSet;

public interface IGameHandler {

    // What message / string should the client wait for to say oh yeah hey btw this is the game being played rn
    HashSet<String> waitFor();

    // Same as above but for end of game
    HashSet<String> endAt();

    GameHandler newInstance();

}
