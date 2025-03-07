package me.bubbles.murderder.events.manager;

import me.bubbles.murderder.Murderder;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Subscriptions {

    public Subscriptions() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Murderder.INSTANCE.getEventManager().onEvent(event);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        Murderder.INSTANCE.getEventManager().onEvent(event);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        Murderder.INSTANCE.getEventManager().onEvent(event);
    }

}