package vertexcubed.vrtex.client.screen.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

/**
 * Extension of AbstractWidget that lets you not play a sound when this widget is clicked.
 */
public abstract class VrTeXAbstractWidget extends AbstractWidget {

    protected boolean playSound = false;
    public VrTeXAbstractWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    /**
     * Identical to superclass except checks if playSound = true, allows widgets to not play the sound if they don't want to
     */
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!this.active || !this.visible || this.alpha <= 0.0f) return false;

        if (this.isValidClickButton(pButton)) {
            boolean flag = this.clicked(pMouseX, pMouseY);
            if (flag) {
                if(playSound) playClickSound();
                this.onClick(pMouseX, pMouseY, pButton);
                return true;
            }
        }

        return false;
    }

    public void playClickSound() {
        this.playDownSound(Minecraft.getInstance().getSoundManager());
    }
}
