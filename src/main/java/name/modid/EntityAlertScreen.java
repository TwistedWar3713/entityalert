package com.example.entityalert;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class EntityAlertScreen extends Screen {

    protected EntityAlertScreen() {
        super(Text.literal("Entity Alert Config"));
    }

    @Override
    protected void init() {
        addDrawableChild(ButtonWidget.builder(
                EntityAlertConfig.DATA.showAlert ? Text.of("Enabled") : Text.of("Disabled"),
                button -> {
                    EntityAlertConfig.DATA.showAlert = !EntityAlertConfig.DATA.showAlert;
                    button.setMessage(
                            EntityAlertConfig.DATA.showAlert ? Text.of("Enabled") : Text.of("Disabled")
                    );
                    EntityAlertConfig.save();
                }
        ).dimensions(width / 2 - 50, 80, 100, 20).build());

        addDrawableChild(new SliderWidget(width / 2 - 50, 120, 100, 20,
                Text.literal("Range"),
                EntityAlertConfig.DATA.alertRange / 64.0) {
            @Override
            protected void updateMessage() {
                setMessage(Text.literal("Range: " + (int) (value * 64)));
            }

            @Override
            protected void applyValue() {
                EntityAlertConfig.DATA.alertRange = value * 64;
                EntityAlertConfig.save();
            }
        });
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, textRenderer, "Entity Alert Config", width / 2, 40, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}