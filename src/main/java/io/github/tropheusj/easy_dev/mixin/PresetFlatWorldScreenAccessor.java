package io.github.tropheusj.easy_dev.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen.PresetInfo;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(PresetFlatWorldScreen.class)
public interface PresetFlatWorldScreenAccessor {
	@Accessor("PRESETS")
	static List<PresetInfo> easyDev$PRESETS() {
		throw new RuntimeException("Mixin failed");
	}
}
