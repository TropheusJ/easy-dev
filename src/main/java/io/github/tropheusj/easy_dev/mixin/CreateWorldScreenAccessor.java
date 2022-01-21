package io.github.tropheusj.easy_dev.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;

import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen.SelectedGameMode;

import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.world.Difficulty;

import net.minecraft.world.level.GameRules;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public interface CreateWorldScreenAccessor {
	@Invoker("setGameMode")
	void easyDev$setGameMode(SelectedGameMode mode);

	@Invoker("onCreate")
	void easyDev$onCreate();

	@Accessor("difficulty")
	void easyDev$difficulty(Difficulty difficulty);

	@Accessor("gameRules")
	GameRules easyDev$gameRules();

	@Accessor("worldGenSettingsComponent")
	WorldGenSettingsComponent easyDev$worldGenSettingsComponent();

	@Accessor("nameEdit")
	EditBox easyDev$nameEdit();
}
