package io.github.tropheusj.easy_dev.mixin;

import io.github.tropheusj.easy_dev.TestWorldGenerator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;

import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements TestWorldGenerator {
	@Shadow
	@Final
	private Map<ResourceKey<Level>, ServerLevel> levels;

	@Unique
	private boolean easyDev$genningTestingWorld = false;

	@Override
	public void genningTestingWorld() {
		easyDev$genningTestingWorld = true;
	}

	@Inject(method = "createLevels", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getDataStorage()Lnet/minecraft/world/level/storage/DimensionDataStorage;"))
	private void easyDev$createLevels(ChunkProgressListener worldGenerationProgressListener, CallbackInfo ci) {
		ServerLevel level = levels.get(Level.OVERWORLD);
		if (easyDev$genningTestingWorld) {
			level.setDayTime(6000);
			easyDev$genningTestingWorld = false;
		}
	}
}
