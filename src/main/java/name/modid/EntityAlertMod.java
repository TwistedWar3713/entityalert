package com.example.entityalert;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EntityAlertMod implements ClientModInitializer {
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        EntityAlertConfig.load();
        EntityAlertHud.init();

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.entityalert.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.entityalert"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                EntityAlertHud.setEnabled(!EntityAlertHud.isEnabled());
                EntityAlertConfig.save();
            }
        });
    }
}