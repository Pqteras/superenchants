package net.pqteras.superenchants.screen;

import net.minecraft.registry.Registry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.pqteras.superenchants.SuperEnchants;

public class ModScreenHandlers {

    public static final ScreenHandlerType<UpgradeBenchScreenHandler> UPGRADE_BENCH =
        Registry.register(Registries.SCREEN_HANDLER, new Identifier("superenchants", "upgrade_bench"),
        new ExtendedScreenHandlerType<>(UpgradeBenchScreenHandler::new));

    public static void registerAllScreenHandlers() {
        SuperEnchants.LOGGER.info("Registering screen handlers for SuperEnchants...");
    }
}