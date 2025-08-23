package com.backpack.securitybackpack.registry;

import com.backpack.securitybackpack.SecurityBackpack;
import com.backpack.securitybackpack.menu.SecurityCaseMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, SecurityBackpack.MODID);

    public static final Supplier<MenuType<SecurityCaseMenu>> SECURITY_CASE_MENU = MENUS.register(
            "security_case",
            () -> IMenuTypeExtension.create(SecurityCaseMenu::new)
    );
}
