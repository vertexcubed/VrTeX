package vertexcubed.vrtex.client.screenshake;

import net.minecraft.client.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.joml.Vector3f;


/**
 * <p>
 *     Code largely lifted from <a href=https://github.com/LodestarMC/Lodestone/tree/main>Lodestone</a>, which is licensed under LGPL 3.
 * </p>
 * <p>
 *     Creates a screenshake effect that falls off based on how far the camera is from the position.
 * </p>
 *
 */
public class PositionedScreenshake extends LocalScreenshake {

    public static final StreamCodec<FriendlyByteBuf, PositionedScreenshake> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, shake -> shake.duration,
            ByteBufCodecs.VECTOR3F, shake -> shake.pos,
            // StreamCodec#composite supports up to 6 args so we're packing these in a Vector3f
            ByteBufCodecs.VECTOR3F, shake -> new Vector3f(shake.xRotPower, shake.yRotPower, shake.zRotPower),
            ByteBufCodecs.FLOAT, shake -> shake.falloffDistance,
            ByteBufCodecs.FLOAT, shake -> shake.maxDistance,
            (duration1, pos1, power, falloffDistance, maxDistance) -> new PositionedScreenshake(duration1, pos1, power.x, power.y, power.z).setFalloffDistance(falloffDistance).setMaxDistance(maxDistance)
    );

    private float falloffDistance = 1.0f;
    private float maxDistance = 3.0f;
    private final Vector3f pos;

    public PositionedScreenshake(int duration, Vector3f pos, float power) {
        super(duration, power);
        this.pos = pos;
    }

    public PositionedScreenshake(int duration, Vector3f pos, float xPower, float yPower, float zPower) {
        super(duration, xPower, yPower, zPower);
        this.pos = pos;
    }

    /**
     * Sets the falloff distance for the screenshake, i.e. when the power starts to be scaled down by distance.
     */
    public PositionedScreenshake setFalloffDistance(float falloffDistance) {
        this.falloffDistance = falloffDistance;
        return this;
    }

    /**
     * Sets the max distance for the screenshake, i.e. when the power is equal to 0. Players outside of this won't get the screenshake sent to them.
     */
    public PositionedScreenshake setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }


    @Override
    protected float powerEquation(Camera camera, float power) {
        float original = super.powerEquation(camera, power);
//        if(true) {
//            return original;
//        }

        float distance = pos.distance(camera.getPosition().toVector3f());
        if (distance > maxDistance) {
            return 0;
        }
        float distanceMultiplier = 1;
        if (distance > falloffDistance) {
            float remaining = maxDistance - falloffDistance;
            float current = distance - falloffDistance;
            distanceMultiplier = 1 - current / remaining;
        }
        Vector3f lookDirection = camera.getLookVector();
        Vector3f directionToScreenshake = new Vector3f(pos).sub(camera.getPosition().toVector3f()).normalize();
        float angle = Math.max(0, lookDirection.dot(directionToScreenshake));
        return ((original * distanceMultiplier) + (original * angle)) * 0.5f;
    }
}
