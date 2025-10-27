package vertexcubed.vrtex;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;
import vertexcubed.vrtex.client.screenshake.ScreenshakeHandler;
import vertexcubed.vrtex.server.network.VrTexPayloads;

@EventBusSubscriber(modid = VrTeX.MOD_ID)
@Mod(VrTeX.MOD_ID)
public class VrTeX {
    public static final String MOD_ID = "vrtex";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public VrTeX(IEventBus modEventBus, ModContainer modContainer) {
        VrTeXConfig.Client.register(modContainer);
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        VrTexPayloads.register(event);
    }


    @EventBusSubscriber(modid = VrTeX.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ScreenshakeHandler.init();
        }
    }
}
