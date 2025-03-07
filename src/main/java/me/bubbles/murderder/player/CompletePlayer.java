package me.bubbles.murderder.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;

public class CompletePlayer extends BasePlayer {

    private final EntityPlayerSP player;

    private CompletePlayer(EntityPlayerSP player) {
        this.player=player;
    }

    public void sendMessage(String message) {

        player.addChatMessage(new ChatComponentText(message.replace("&","§")));

    }

    public static CompletePlayer get(EntityPlayerSP player) {
        return new CompletePlayer(player);
    }

}