package vertexcubed.vrtex.server.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import vertexcubed.vrtex.client.screenshake.PositionedScreenshake;
import vertexcubed.vrtex.client.screenshake.ScreenshakeHandler;

import static vertexcubed.vrtex.VrTeX.modLoc;

public record ClientboundPositionedScreenshakePayload(PositionedScreenshake instance) implements CustomPacketPayload {

    public static final Type<ClientboundPositionedScreenshakePayload> TYPE =
            new Type<>(modLoc("positioned_screenshake"));

    public static final StreamCodec<FriendlyByteBuf, ClientboundPositionedScreenshakePayload> CODEC = StreamCodec.composite(
            PositionedScreenshake.CODEC, ClientboundPositionedScreenshakePayload::instance,
            ClientboundPositionedScreenshakePayload::new
    );



    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void register(final PayloadRegistrar registrar) {
        registrar.playToClient(TYPE, CODEC, ClientboundPositionedScreenshakePayload::handle);
    }

    public static void handle(final ClientboundPositionedScreenshakePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ScreenshakeHandler.addScreenshake(payload.instance);
        }).exceptionally(e -> {
            context.disconnect(Component.literal("Failed to handle payload " + TYPE.id() + ": " + e.getMessage()));
            return null;
        });
    }
}

