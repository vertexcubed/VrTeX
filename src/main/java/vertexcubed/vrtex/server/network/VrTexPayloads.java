package vertexcubed.vrtex.server.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class VrTexPayloads {

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar main = event.registrar("1");

        ClientboundLocalScreenshakePayload.register(main);
        ClientboundPositionedScreenshakePayload.register(main);
    }

}
