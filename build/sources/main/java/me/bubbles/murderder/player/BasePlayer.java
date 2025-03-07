package me.bubbles.murderder.player;

import me.bubbles.murderder.Murderder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class BasePlayer implements IBetterPlayer {

    @Override
    public void sendMessage(String message) {
        return;
    }

    @Override
    public EntityPlayerSP getPlayer() {
        return null;
    }

    public static BasePlayer get(EntityPlayerSP player) {
        if(player==null) {
            return new BasePlayer();
        }
        return CompletePlayer.get(player);
    }

    public static BasePlayer get() {
        if(Minecraft.getMinecraft()==null) {
            return get(null);
        }
        return get(Murderder.INSTANCE.getMinecraft().thePlayer);
    }

}