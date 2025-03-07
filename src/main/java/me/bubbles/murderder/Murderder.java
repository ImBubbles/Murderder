package me.bubbles.murderder;

import me.bubbles.murderder.events.manager.EventManager;
import me.bubbles.murderder.games.GameManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Murderder.MODID, version = Murderder.VERSION)
public class Murderder {
    public static final String MODID = "murderder";
    public static final String VERSION = "1.0";

    public static Murderder INSTANCE;
    private final EventManager eventManager = new EventManager();
    private Minecraft lastClient;
    private GameManager gameManager;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        INSTANCE=this;
        gameManager=new GameManager();
        this.lastClient=Minecraft.getMinecraft();
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public Minecraft getMinecraft() {
        Minecraft result = Minecraft.getMinecraft();
        if(result!=null) {
            lastClient=result;
        }
        return result;
    }

    public Minecraft lastClient() {
        return lastClient;
    }

}
