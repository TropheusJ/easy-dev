package io.github.tropheusj.easy_dev.mixin;

import com.mojang.datafixers.util.Function4;

import io.github.tropheusj.easy_dev.TestWorldGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.Minecraft;

import net.minecraft.client.Minecraft.ExperimentalDialogType;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.RegistryAccess.RegistryHolder;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraft.world.level.storage.WorldData;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftMixin implements TestWorldGenerator {
	@Shadow
	@Nullable
	private IntegratedServer singleplayerServer;
	@Unique
	private boolean easyDev$genningTestingWorld = false;

	@Override
	public void genningTestingWorld() {
		easyDev$genningTestingWorld = true;
	}

	@Inject(method = "doLoadLevel", at = @At(value = "FIELD", shift = Shift.AFTER, target = "Lnet/minecraft/client/Minecraft;isLocalServer:Z"))
	private void easyDev$onLoadLevel(String levelName, RegistryHolder dynamicRegistries, Function<LevelStorageAccess, DataPackConfig> levelSaveToDatapackFunction, Function4<LevelStorageAccess, RegistryHolder, ResourceManager, DataPackConfig, WorldData> quadFunction, boolean vanillaOnly, ExperimentalDialogType selectionType, CallbackInfo ci) {
		if (easyDev$genningTestingWorld) {
			((TestWorldGenerator) singleplayerServer).genningTestingWorld();
			easyDev$genningTestingWorld = false;
		}

	}
}
