package io.github.tropheusj.easy_dev;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

public class EasyDev implements ModInitializer {
	public static final String ID = "easy_dev";

	@Override
	public void onInitialize() {
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
