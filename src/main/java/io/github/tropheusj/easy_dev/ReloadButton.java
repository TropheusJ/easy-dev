package io.github.tropheusj.easy_dev;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import static io.github.tropheusj.easy_dev.EasyDev.id;

public class ReloadButton extends ImageButton {
	public static final Component DESCRIPTION = new TranslatableComponent("easy_dev.reload_button");
	public static final ResourceLocation LOCATION = id("textures/gui/reload_button.png");

	public ReloadButton(int x, int y, int width, int height) {
		super(x, y, width, height, 0, 0, 20, LOCATION, 20, 40,
				ReloadButton::onPress, ReloadButton::onTooltip, DESCRIPTION);
	}

	public static void onPress(Button b) {
		Minecraft.getInstance().reloadResourcePacks();
	}

	public static void onTooltip(Button button, PoseStack poseStack, int mouseX, int mouseY) {
		if (button.isActive()) {
			fillGradient(poseStack,
					button.x - 5,
					button.y - 20,
					button.x + button.getWidth() + 75,
					button.y - 2,
					0xFFFFAA00,
					0xFFFFAA00,
					button.getBlitOffset()
			);
			fillGradient(poseStack,
					button.x - 3,
					button.y - 18,
					button.x + button.getWidth() + 73,
					button.y - 4,
					0xAA000000,
					0xAA000000,
					button.getBlitOffset()
			);
			drawString(poseStack, Minecraft.getInstance().font, DESCRIPTION, button.x, button.y - 15, 0xFFFFFFFF);
		}
	}
}
