package com.example.entityalert;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;

public class EntityAlertHud {
    private static boolean enabled = true;

    public static void init() {
        HudRenderCallback.EVENT.register((matrices, tickDelta) -> {
            if (!enabled || !EntityAlertConfig.DATA.showAlert) return;

            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            double range = EntityAlertConfig.DATA.alertRange;
            Box box = new Box(
                    client.player.getX() - range, client.player.getY() - range, client.player.getZ() - range,
                    client.player.getX() + range, client.player.getY() + range, client.player.getZ() + range
            );

            long count = client.world.getEntities().stream()
                    .filter(entity -> entity != client.player)
                    .filter(entity -> entity.getBoundingBox().intersects(box))
                    .count();

            TextRenderer textRenderer = client.textRenderer;
            String msg = (count > 0 ? "§c⚠ " : "§7") + "Entities near: " + count;
            textRenderer.drawWithShadow(matrices, Text.of(msg), 10, 10, 0xFFFFFF);
        });
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isEnabled() {
        return enabled;
    }
}