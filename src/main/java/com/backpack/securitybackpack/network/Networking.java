package com.backpack.securitybackpack.network;

import com.backpack.securitybackpack.menu.SecurityCaseMenu;
import com.backpack.securitybackpack.registry.ModAttachments;
import com.backpack.securitybackpack.registry.ModMenus;
import com.backpack.securitybackpack.SecurityCaseConfig;
import com.backpack.securitybackpack.SecurityBackpack;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = SecurityBackpack.MODID)
public class Networking {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(OpenSecurityCaseC2SPayload.TYPE, OpenSecurityCaseC2SPayload.STREAM_CODEC,
                Networking::handleOpenSecurityCase);
    }

    private static void handleOpenSecurityCase(final OpenSecurityCaseC2SPayload payload, final IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            int slots = SecurityCaseConfig.SLOTS.get();
            // Ensure attachment exists and is sized
            var inv = player.getData(ModAttachments.SECURITY_CASE.get());
            if (inv.getSlots() != slots) {
                inv.resize(slots);
            }
            MenuProvider provider = new SimpleMenuProvider(
                    (containerId, playerInventory, playerEntity) -> SecurityCaseMenu.forPlayer(containerId, playerInventory, playerEntity),
                    Component.translatable("menu.securitybackpack.security_case")
            );
            player.openMenu(provider, buf -> buf.writeVarInt(slots));
        });
    }
}
