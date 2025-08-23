package com.backpack.securitybackpack.network;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;

public record OpenSecurityCaseC2SPayload() implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("securitybackpack", "open_security_case");
    public static final Type<OpenSecurityCaseC2SPayload> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenSecurityCaseC2SPayload> STREAM_CODEC =
            StreamCodec.unit(new OpenSecurityCaseC2SPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
