package io.github.tropheusj.easy_dev.mixin;

import io.github.tropheusj.easy_dev.QuickSuperflatButton;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;

import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin extends Screen {
	private QuickSuperflatButton easyDev$superflatButton;

	protected CreateWorldScreenMixin(Component component) {
		super(component);
	}

	// add superflat button
	@Inject(method = "init", at = @At("HEAD"))
	private void easyDev$init(CallbackInfo ci) {
		easyDev$superflatButton = new QuickSuperflatButton(width / 2 - 190, 60, 150, 20, (CreateWorldScreen) (Object) this);
		addRenderableWidget(easyDev$superflatButton);
	}

	// move name box
	@ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen$1;<init>(Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/network/chat/Component;)V"))
	private void easyDev$moveName(Args args) {
		int oldX = args.get(2);
		args.set(2, oldX + 80); // width / 2 - 20
	}

	// move 'World Name'
	@ModifyArgs(method = "render", at = @At(value = "INVOKE", ordinal = 2, target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
	private void easyDev$moveWorldName(Args args) {
		int oldX = args.get(3);
		args.set(3, oldX + 80); // width / 2 + 80
	}

	// move 'Will be saved in:'
	@ModifyArgs(method = "render", at = @At(value = "INVOKE", ordinal = 3, target = "Lnet/minecraft/client/gui/screens/worldselection/CreateWorldScreen;drawString(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
	private void easyDev$moveSaveLocation(Args args) {
		int oldX = args.get(3);
		args.set(3, oldX + 80); // width / 2 + 80
	}

	@Inject(at = @At("HEAD"), method = "setWorldGenSettingsVisible")
	private void easyDev$setSuperflatButtonVisibility(boolean worldGenSettingsVisible, CallbackInfo ci) {
		easyDev$superflatButton.visible = !worldGenSettingsVisible;
	}
}
