package vertexcubed.vrtex.common.event;

import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.joml.Vector3f;
import vertexcubed.vrtex.VrTeX;
import vertexcubed.vrtex.client.screenshake.LocalScreenshake;
import vertexcubed.vrtex.client.screenshake.PositionedScreenshake;
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
}
