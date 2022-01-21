package io.github.tropheusj.easy_dev.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;

import net.minecraft.world.level.levelgen.WorldGenSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(WorldGenSettingsComponent.class)
public interface WorldGenSettingsComponentAccessor {
	@Invoker("updateSettings")
	void easyDev$updateSettings(WorldGenSettings settings);

	@Accessor("settings")
	WorldGenSettings easyDev$settings();
}
