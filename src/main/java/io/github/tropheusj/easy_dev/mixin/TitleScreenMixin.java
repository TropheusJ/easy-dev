package io.github.tropheusj.easy_dev.mixin;

import io.github.tropheusj.easy_dev.ReloadButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component component) {
		super(component);
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void easyDev$addReloadButton(CallbackInfo ci) {
		addRenderableWidget(new ReloadButton(
				width / 2 + 104,
				height / 4 + 24,
				20,
				20
		));
	}
}
