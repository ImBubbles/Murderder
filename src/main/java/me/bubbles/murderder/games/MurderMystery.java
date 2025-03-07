package me.bubbles.murderder.games;

import me.bubbles.murderder.Murderder;
import me.bubbles.murderder.player.BasePlayer;
import me.bubbles.murderder.util.HashSetBuilder;
import me.bubbles.murderder.util.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class MurderMystery extends GameHandler {

    private UUID murderer = null;
    private UUID detective = null;
    private UUID localUUID;

    public MurderMystery() {
        super(
                Arrays.asList(
                        RenderWorldLastEvent.class,
                        TickEvent.ClientTickEvent.class
                )
        );
    }

    private MurderMystery initPlayer() {
        this.localUUID=Murderder.INSTANCE.lastClient().thePlayer.getUniqueID();
        return this;
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof TickEvent.ClientTickEvent) {
            /*if (murderer.isEmpty() && detective.isEmpty()) {
                return;
            }*/

            List<EntityPlayer> playerList = Minecraft.getMinecraft().theWorld.playerEntities;

            for (EntityPlayer player : playerList) {

                if(player.getUniqueID().equals(localUUID)) {
                    continue;
                }

                Role role = Role.fromItem(player.getHeldItem());
                if (role.equals(Role.MURDERER)) {
                    if (murderer!=player.getUniqueID()) {
                        murderer = player.getUniqueID();
                        BasePlayer.get().sendMessage(role.COLOR + player.getName() + "&fis the " + role.COLOR + "MURDERER&f.");
                    }
                }
                if (role.equals(Role.DETECTIVE)) {
                    if (detective!=player.getUniqueID()) {
                        detective = player.getUniqueID();
                        BasePlayer.get().sendMessage(role.COLOR + player.getName() + "&fis the " + role.COLOR + "DETECTIVE&f.");
                    }
                }
            }
        }

        if (event instanceof RenderWorldLastEvent) {

            RenderWorldLastEvent e = (RenderWorldLastEvent) event;

            Minecraft mc = Minecraft.getMinecraft();
            if(mc==null) {
                return;
            }
            WorldClient world = mc.theWorld;
            if(world==null) {
                return;
            }

            for(EntityPlayer player : world.playerEntities) {
                if(player==null) {
                    continue;
                }

                if(player.getUniqueID().equals(localUUID)) {
                    continue;
                }

                if(murderer!=null) {
                    if(player.getUniqueID().equals(murderer)) {
                        Render.xrayPlayer(player, Color.RED, e.partialTicks);
                        continue;
                    }
                }

                if(detective!=null) {
                    if(player.getUniqueID().equals(detective)) {
                        Render.xrayPlayer(player, Color.CYAN, e.partialTicks);
                    }
                }
            }

        }

    }

    enum Role {
        MURDERER("&c"),
        DETECTIVE("&b"),
        INNOCENT("&7");

        public final String COLOR;
        Role(String color) {
            this.COLOR=color;
        }

        public static Role fromItem(ItemStack itemStack) {

            if(itemStack==null) {
                return INNOCENT;
            }

            if(itemStack.getItem().equals(Items.map)) {
                return INNOCENT;
            }

            if(itemStack.getItem().equals(Items.armor_stand)) {
                return INNOCENT;
            }

            if(itemStack.getItem().equals(Items.gold_ingot)) {
                return INNOCENT;
            }

            if(!itemStack.hasTagCompound()) {
                return INNOCENT;
            }

            NBTTagCompound tagCompound = itemStack.getTagCompound();
            if(!tagCompound.hasKey("ExtraAttributes")) {
                return INNOCENT;
            }
            NBTTagCompound extraAttributes = tagCompound.getCompoundTag("ExtraAttributes");
            if(extraAttributes.hasKey("KNIFE")) {
                return MURDERER;
            }
            if(extraAttributes.hasKey("detectiveBow")) {
                return DETECTIVE;
            }

            return INNOCENT;

        }

    }

    @Override
    public HashSet<String> waitFor() {
        return new HashSetBuilder<String>().add(
                "The Murderer has received their sword!",
                "You have received your sword!")
                        .get();
    }

    @Override
    public HashSet<String> endAt() {
        return new HashSetBuilder<String>().add(
                        "Detective:",
                        "Teleporting you to the lobby in 3 seconds...")
                .get();
    }

    @Override
    public GameHandler newInstance() {
        return new MurderMystery().initPlayer();
    }
}