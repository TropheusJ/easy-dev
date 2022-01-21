package io.github.tropheusj.easy_dev;

import com.mojang.blaze3d.vertex.PoseStack;

import io.github.tropheusj.easy_dev.mixin.CreateWorldScreenAccessor;
import io.github.tropheusj.easy_dev.mixin.PresetFlatWorldScreenAccessor;
import io.github.tropheusj.easy_dev.mixin.WorldGenSettingsComponentAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen.PresetInfo;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen.SelectedGameMode;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.world.Difficulty;

import net.minecraft.world.level.GameRules;

import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

@Environment(EnvType.CLIENT)
public class QuickSuperflatButton extends Button {
	public static final Component TITLE = new TranslatableComponent("easy_dev.dev_superflat.create").withStyle(ChatFormatting.GOLD);
	public static final Component DESCRIPTION_1 = new TranslatableComponent("easy_dev.dev_superflat.description.1");
	public static final Component DESCRIPTION_2 = new TranslatableComponent("easy_dev.dev_superflat.description.2");
	public static final Component DESCRIPTION_3 = new TranslatableComponent("easy_dev.dev_superflat.description.3");
	public static final Component DESCRIPTION_4 = new TranslatableComponent("easy_dev.dev_superflat.description.4");
	private final WeakReference<CreateWorldScreen> screen;

	public QuickSuperflatButton(int x, int y, int width, int height, CreateWorldScreen screen) {
		super(x, y, width, height, TITLE, QuickSuperflatButton::onPress, QuickSuperflatButton::onTooltip);
		this.screen = new WeakReference<>(screen);
	}

	public static void onPress(Button b) {
		if (b instanceof QuickSuperflatButton button) {
			CreateWorldScreen screen = button.getScreen();
			if (screen instanceof CreateWorldScreenAccessor access) {
				access.easyDev$difficulty(Difficulty.HARD);
				access.easyDev$setGameMode(SelectedGameMode.CREATIVE);

				GameRules rules = access.easyDev$gameRules();
				rules.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, null);
				rules.getRule(GameRules.RULE_DO_PATROL_SPAWNING).set(false, null);
				rules.getRule(GameRules.RULE_DO_TRADER_SPAWNING).set(false, null);
				rules.getRule(GameRules.RULE_DOINSOMNIA).set(false, null);

				rules.getRule(GameRules.RULE_DAYLIGHT).set(false, null);
				rules.getRule(GameRules.RULE_WEATHER_CYCLE).set(false, null);
				rules.getRule(GameRules.RULE_DOFIRETICK).set(false, null);

				rules.getRule(GameRules.RULE_KEEPINVENTORY).set(true, null);

				if (access.easyDev$worldGenSettingsComponent() instanceof WorldGenSettingsComponentAccessor settingsAccess) {
					WorldGenSettings base = settingsAccess.easyDev$settings();
					PresetInfo redstoneReady = PresetFlatWorldScreenAccessor.easyDev$PRESETS().get(7);
					RegistryAccess.RegistryHolder holder = screen.worldGenSettingsComponent.registryHolder();
					MappedRegistry<LevelStem> registry = WorldGenSettings.withOverworld(
							holder.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY),
							base.dimensions(),
							new FlatLevelSource(redstoneReady.settings.apply(holder.registryOrThrow(Registry.BIOME_REGISTRY)))
					);
					WorldGenSettings settings = new WorldGenSettings(base.seed(), false, false, registry);
					settingsAccess.easyDev$updateSettings(settings);
					access.easyDev$nameEdit().setValue("Testing World");
					((TestWorldGenerator) Minecraft.getInstance()).genningTestingWorld();
					access.easyDev$onCreate();
					return;
				}
			}
		}
		throw new RuntimeException("HOW");
	}

	@Nullable
	private CreateWorldScreen getScreen() {
		return this.screen.get();
	}

	public static void onTooltip(Button button, PoseStack poseStack, int relativeMouseX, int relativeMouseY) {
		if (button.isActive()) {
			fillGradient(poseStack,
					button.x - 5,
					button.y - 48,
					button.x + button.getWidth() + 75,
					button.y - 2,
					0xFFFFAA00,
					0xFFFFAA00,
					button.getBlitOffset()
			);
			fillGradient(poseStack,
					button.x - 3,
					button.y - 46,
					button.x + button.getWidth() + 73,
					button.y - 4,
					0xAA000000,
					0xAA000000,
					button.getBlitOffset()
			);
			drawString(poseStack, Minecraft.getInstance().font, DESCRIPTION_1, button.x - 2, button.y - 44, 0xFFFFFFFF);
			drawString(poseStack, Minecraft.getInstance().font, DESCRIPTION_2, button.x - 2, button.y - 34, 0xFFFFFFFF);
			drawString(poseStack, Minecraft.getInstance().font, DESCRIPTION_3, button.x - 2, button.y - 24, 0xFFFFFFFF);
			drawString(poseStack, Minecraft.getInstance().font, DESCRIPTION_4, button.x - 2, button.y - 14, 0xFFFFFFFF);
		}
	}
}
