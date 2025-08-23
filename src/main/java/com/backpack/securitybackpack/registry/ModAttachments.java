package com.backpack.securitybackpack.registry;

import com.backpack.securitybackpack.SecurityCaseConfig;
import com.backpack.securitybackpack.security.SecurityCaseInventory;
import com.backpack.securitybackpack.SecurityBackpack;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SecurityBackpack.MODID);

    public static final Supplier<AttachmentType<SecurityCaseInventory>> SECURITY_CASE = ATTACHMENT_TYPES.register(
            "security_case",
            () -> AttachmentType.serializable(() -> new SecurityCaseInventory(SecurityCaseConfig.SLOTS.get()))
                    .copyOnDeath()
                    .build()
    );
}
