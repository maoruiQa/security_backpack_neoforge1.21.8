package com.backpack.securitybackpack;

import com.backpack.securitybackpack.client.screen.SecurityCaseScreen;
import com.backpack.securitybackpack.network.OpenSecurityCaseC2SPayload;
import com.backpack.securitybackpack.registry.ModMenus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = SecurityBackpack.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = SecurityBackpack.MODID, value = Dist.CLIENT)
public class SecurityBackpackClient {
    public SecurityBackpackClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Other client setup work here (logging, etc.)
        });

        SecurityBackpack.LOGGER.info("HELLO FROM CLIENT SETUP");
        SecurityBackpack.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }

    @SubscribeEvent
    static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.SECURITY_CASE_MENU.get(), SecurityCaseScreen::new);
    }

    @SubscribeEvent
    static void onScreenInit(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen screen) {
            // Approximate the vanilla inventory background position (176x166)
            int bgW = 176;
            int bgH = 166;
            int buttonWidth = 70;
            int x = (screen.width - bgW) / 2 - 22 - (int)(buttonWidth * 0.6f); // shift further left by ~60% of width
            int y = (screen.height - bgH) / 2 + 20;

            Button openCase = Button.builder(Component.translatable("gui.securitybackpack.open_case"), b -> {
                var conn = Minecraft.getInstance().getConnection();
                if (conn != null) conn.send(new OpenSecurityCaseC2SPayload());
            }).bounds(x, y, buttonWidth, 20).build();

            event.addListener(openCase);
        }
    }
}
