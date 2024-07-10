package net.pqteras.superenchants;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.pqteras.superenchants.screen.ModScreenHandlers;
import net.pqteras.superenchants.screen.UpgradeBenchScreen;

public class SuperEnchantsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.UPGRADE_BENCH, UpgradeBenchScreen::new);
    }
}
