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
import org.slf4j.Logger;
import vertexcubed.vrtex.client.screenshake.ScreenshakeHandler;

//@EventBusSubscriber(modid = VrTeX.MOD_ID)
@Mod(VrTeX.MOD_ID)
public class VrTeX {
    public static final String MOD_ID = "vrtex";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.parse(path);
    }

    public VrTeX(IEventBus modEventBus, ModContainer modContainer) {
        VrTeXConfig.Client.register(modContainer);
    }


    @EventBusSubscriber(modid = VrTeX.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ScreenshakeHandler.init();
        }
    }
}
