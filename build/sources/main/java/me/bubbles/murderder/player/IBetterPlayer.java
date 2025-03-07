package me.bubbles.murderder.player;

import net.minecraft.client.entity.EntityPlayerSP;

public interface IBetterPlayer {

    void sendMessage(String message);
    EntityPlayerSP getPlayer();

}