package vertexcubed.vrtex.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertexcubed.vrtex.client.screen.VrTeXScreen;
import vertexcubed.vrtex.common.task.TaskManager;

@Mixin(Screen.class)
public class ScreenMixin implements VrTeXScreen {

    @Unique
    private TaskManager cybernetics$tasks;

    @Override
    public TaskManager vrtex$getTaskManager() {
        return cybernetics$tasks;
    }

    @Inject(method = "<init>", at=@At("TAIL"))
    public void onScreenConstructor(CallbackInfo ci) {
        Level level = Minecraft.getInstance().level;
        cybernetics$tasks = new TaskManager(level == null ? 0 : level.getGameTime());
    }


    @Inject(method = "removed", at=@At("TAIL"))
    public void cybernetics$onRemoved(CallbackInfo ci) {
        cybernetics$tasks.interruptAll();
        cybernetics$tasks.clear();
    }

    @Inject(method = "tick", at=@At("HEAD"))
    public void cybernetics$onTick(CallbackInfo ci) {
        if(Minecraft.getInstance().level == null) {
            return;
        }
        cybernetics$tasks.tick(Minecraft.getInstance().level.getGameTime());
    }

//    @Inject(method = "render", at=@At("HEAD"))
//    public void cybernetics$onRender(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
//        if(Minecraft.getInstance().level == null) {
//            return;
//        }
//        Cybernetics.LOGGER.info("Ticking frame for screens?");
//        cybernetics$tasks.tickFrame(Minecraft.getInstance().level.getGameTime(), partialTick);
//    }
}

