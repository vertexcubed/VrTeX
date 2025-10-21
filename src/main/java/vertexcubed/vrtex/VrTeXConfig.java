package vertexcubed.vrtex;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

public class VrTeXConfig {
    @EventBusSubscriber(modid = VrTeX.MOD_ID, value = Dist.CLIENT)
    public static class Client {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        private static final ModConfigSpec.DoubleValue GLOBAL_SCREENSHAKE_SCALE = BUILDER
                .comment("Global scale of all screenshake effects using VrTeX. Set to 0 to disable screenshakes.")
                .defineInRange("globalScreenshakeScale", 1.0, 0.0, 1.0);

        private static final ModConfigSpec SPEC = BUILDER.build();

        public static double globalScreenshakeScale;


        @SubscribeEvent
        public static void onLoad(final ModConfigEvent event) {
            globalScreenshakeScale = GLOBAL_SCREENSHAKE_SCALE.get();
        }

        public static void register(ModContainer container) {
            container.registerConfig(ModConfig.Type.CLIENT, SPEC);
        }
    }
}
