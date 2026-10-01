package com.example.lazytntpeek;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class LazyTntPeekClient implements ClientModInitializer {
    /** Read by the mixin on the client thread. */
    public static boolean enabled = false;

    private static KeyMapping toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.lazytntpeek.toggle",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KeyMapping.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Drain every queued press; only act on those made while Ctrl is held.
            while (toggleKey.consumeClick()) {
                if (client.hasControlDown()) {
                    enabled = !enabled;
                    if (client.player != null) {
                        client.player.displayClientMessage(Component.translatable(
                                enabled ? "message.lazytntpeek.on" : "message.lazytntpeek.off"), true);
                    }
                }
            }
        });
    }
}
