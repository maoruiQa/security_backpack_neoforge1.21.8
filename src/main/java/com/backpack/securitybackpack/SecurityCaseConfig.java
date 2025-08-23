package com.backpack.securitybackpack;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SecurityCaseConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue SLOTS = BUILDER
            .comment(
                    "How many slots the Security Case provides per player.",
                    "This is a server-side only setting and requires a server restart to fully apply.",
                    "Minimum: 1, Recommended default: 2")
            .defineInRange("securityCaseSlots", 2, 1, 9 * 6);

    static final ModConfigSpec SPEC = BUILDER.build();
}
