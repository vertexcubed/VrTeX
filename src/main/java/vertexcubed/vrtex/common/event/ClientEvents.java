package vertexcubed.vrtex.common.event;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import vertexcubed.vrtex.VrTeX;
import vertexcubed.vrtex.client.screen.ScreenHelper;
import vertexcubed.vrtex.client.screenshake.ScreenshakeHandler;

@EventBusSubscriber(modid = VrTeX.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTickStart(ClientTickEvent.Pre event) {
        ScreenshakeHandler.onClientTick(event);
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        ScreenshakeHandler.setupCamera(event);
    }

    @SubscribeEvent
    public static void renderScreenPre(ScreenEvent.Render.Pre event) {
        if(Minecraft.getInstance().level == null) {
            return;
        }
        ScreenHelper.getTaskManager(event.getScreen()).tickFrame(Minecraft.getInstance().level.getGameTime(), event.getPartialTick());
    }
}
