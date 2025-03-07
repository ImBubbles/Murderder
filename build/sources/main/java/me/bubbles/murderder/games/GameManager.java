package me.bubbles.murderder.games;

import me.bubbles.murderder.Murderder;
import me.bubbles.murderder.events.manager.EventHandler;
import me.bubbles.murderder.events.manager.EventManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;
import java.util.HashSet;

public class GameManager extends EventHandler {

    private final HashSet<GameHandler> handlers = new HashSet<>();
    private GameHandler currentGame;

    public GameManager() {
        super(ClientChatReceivedEvent.class);
        Murderder.INSTANCE.getEventManager().addEvent(this);
        registerHandler(
                new MurderMystery()
        );
    }

    @Override
    public void onEvent(Event event) {
        ClientChatReceivedEvent e = (ClientChatReceivedEvent) event;
        if(currentGame!=null) {
            if(currentGame.endAt().contains(e.message.getUnformattedText())) {
                setGame(null);
                return;
            }
        }
        for(GameHandler handler : handlers) {
            if(handler.waitFor().contains(e.message.getUnformattedText())) {
                setGame(handler);
            }
        }
    }

    public void setGame(GameHandler gameHandler) {
        EventManager eventManager = Murderder.INSTANCE.getEventManager();
        if(currentGame!=null) {
            eventManager.removeEvent(currentGame);
            //return;
        }
        currentGame=gameHandler==null ? null : gameHandler.newInstance();
        if(currentGame!=null) {
            eventManager.addEvent(currentGame);
        }
    }

    public void registerHandler(GameHandler... gameHandlers) {
        /*for(GameHandler gameHandler : gameHandlers) {
            Murderder.INSTANCE.getEventManager().addEvent(gameHandler);
            handlers.add(gameHandler);
        }*/
        this.handlers.addAll(Arrays.asList(gameHandlers));
    }

    /*enum Games {
        MURDER_MYSTERY(MurderMystery.class);

        private final Class<? extends GameHandler> handler;
        Games(Class<? extends GameHandler> handler) {
            this.handler=handler;
        }

        public Class<? extends GameHandler> getHandler() {
            return handler;
        }

    }*/

}
