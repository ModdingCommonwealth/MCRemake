package net.Spasmanizer.MCRemake.mixin;

import net.Spasmanizer.MCRemake.MCRemakeMod;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(TitleScreen.class)
public class MCRemakeMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		MCRemakeMod.LOGGER.info("This is Minecraft!? No this is MCRemake!");
	}
}
