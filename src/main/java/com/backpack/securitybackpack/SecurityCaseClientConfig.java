package com.backpack.securitybackpack;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SecurityCaseClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue BUTTON_X = BUILDER
            .comment(
                    "Security Case button X position in screen coordinates.",
                    "Default places the button near the top-left corner.")
            .defineInRange("buttonX", 8, 0, 10000);

    public static final ModConfigSpec.IntValue BUTTON_Y = BUILDER
            .comment(
                    "Security Case button Y position in screen coordinates.",
                    "Default places the button near the top-left corner.")
            .defineInRange("buttonY", 8, 0, 10000);

    public static final ModConfigSpec.IntValue BUTTON_WIDTH = BUILDER
            .comment(
                    "Security Case button width in pixels.",
                    "Increase this to make the button easier to click or to fit longer text.")
            .defineInRange("buttonWidth", 88, 50, 200);

    static final ModConfigSpec SPEC = BUILDER.build();
}
