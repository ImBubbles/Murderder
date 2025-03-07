package me.bubbles.murderder.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Render {

    public static void xrayPlayer(EntityPlayer player, Color color, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();

        // Save the current OpenGL state
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        // Disable lighting to prevent brightness issues
        GlStateManager.disableLighting();

        // Disable textures for solid color rendering
        GlStateManager.disableTexture2D();

        // Disable depth testing to see through walls
        GlStateManager.disableDepth();

        // Enable blending for transparency
        // these 2 lines below break a lot lmao so dont use these
        //GlStateManager.enableBlend();
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set the color for the outline
        GlStateManager.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                1.0F // Full opacity
        );

        // Disable shading (flat color)
        GL11.glShadeModel(GL11.GL_FLAT);

        // Enable wireframe rendering
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        // Set the line width for the outline
        GL11.glLineWidth(2.0F); // Adjust the thickness of the outline

        // Translate to the player's position
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);

        // Scale up the player model slightly to create the outline
        //GlStateManager.scale(1.1F, 1.1F, 1.1F);

        // Manually render the player model
        RenderPlayer renderer = getPlayerRenderer(player);
        if (renderer != null) {
            // Render the player model with wireframe mode
            renderer.getRenderManager().doRenderEntity(player, 0, 0, 0, 0, partialTicks, true);
            //renderer.doRender((AbstractClientPlayer) player, 0, 0, 0, 0, partialTicks);
        }

        // Disable wireframe rendering
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        // Re-enable depth testing
        GlStateManager.enableDepth();

        // Restore the OpenGL state
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    public static void xrayPlayerOther(EntityPlayer player, Color color, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();

        // Save the current OpenGL state
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();

        // Disable lighting to prevent brightness issues
        GlStateManager.disableLighting();

        // Disable textures for solid color rendering
        GlStateManager.disableTexture2D();

        // Disable depth testing to see through walls
        GlStateManager.disableDepth();

        // Enable blending for transparency
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set the color for the outline
        GlStateManager.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                1.0F // Full opacity
        );

        // Disable shading (flat color)
        GL11.glShadeModel(GL11.GL_FLAT);

        // Enable wireframe rendering
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

        // Set the line width for the outline
        GL11.glLineWidth(2.0F); // Adjust the thickness of the outline

        // Translate to the player's position
        double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);

        // Scale up the player model slightly to create the outline
        //GlStateManager.scale(1.1F, 1.1F, 1.1F);

        // Manually render the player model
        RenderPlayer renderer = getPlayerRenderer(player);
        if (renderer != null) {
            // Render the player model with wireframe mode
            renderer.doRender((AbstractClientPlayer) player, 0, 0, 0, 0, partialTicks);
            //renderer.doRender((AbstractClientPlayer) player, 0, 0, 0, 0, partialTicks);
        }

        // Disable wireframe rendering
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        // Re-enable depth testing
        GlStateManager.enableDepth();

        // Restore the OpenGL state
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }

    private static RenderPlayer getPlayerRenderer(EntityPlayer player) {
        Minecraft mc = Minecraft.getMinecraft();
        RenderManager renderManager = mc.getRenderManager();

        if (renderManager != null) {
            net.minecraft.client.renderer.entity.Render<?> render = renderManager.getEntityRenderObject(player);

            if (render instanceof RenderPlayer) {
                return (RenderPlayer) render;
            }

        }
        return null;
    }

/*    public static void xrayPlayer(EntityPlayer player, Color color) {

        // Get the player's bounding box
        AxisAlignedBB bb = player.getEntityBoundingBox();

        // Adjust the bounding box to fit the player better
        double expand = 0.5; // Expand the box slightly
        AxisAlignedBB expandedBB = bb.expand(expand, expand, expand);

        // Calculate the camera position
        double cameraX = Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double cameraY = Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double cameraZ = Minecraft.getMinecraft().getRenderManager().viewerPosZ;

        // Translate the bounding box to camera-relative space
        expandedBB = expandedBB.offset(-cameraX, -cameraY, -cameraZ);

        // Disable depth testing to see through walls
        GlStateManager.disableDepth();

        GlStateManager.color(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F
        );

        // Disable texture rendering
        GlStateManager.disableTexture2D();

        // Draw the outline
        drawOutlinedBoundingBox(expandedBB);

        // Re-enable texture rendering and depth testing
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();

    }

    private static void drawOutlinedBoundingBox(AxisAlignedBB bb) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        // Draw the bounding box edges
        worldRenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        worldRenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        worldRenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        worldRenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        worldRenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex();

        worldRenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        worldRenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        worldRenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        worldRenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        worldRenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex();

        tessellator.draw();
    }*/

}