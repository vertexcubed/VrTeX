package vertexcubed.vrtex.client.screenshake;

import net.minecraft.client.Camera;
import net.minecraft.util.Mth;

/**
 * <p>
 *     Code largely lifted from <a href=https://github.com/LodestarMC/Lodestone/tree/main>Lodestone</a>, which is licensed under LGPL 3.
 * </p>
 * <p>
 *     Local screenshake effect that decreases in intensity exponentially.
 *     Clamps power to the range [0.0, 1.0] by default. For values greater than 1, override {@link LocalScreenshake#powerEquation(Camera, float)}
 * </p>
 */
public class LocalScreenshake extends ScreenshakeInstance {

    private final float xRotPower;
    private final float yRotPower;
    private final float zRotPower;

    /**
     * @param duration  The time in ticks this screenshake lasts
     * @param power     The power for all axes.
     */
    public LocalScreenshake(int duration, float power) {
        this(duration, power, power, power);
    }

    public LocalScreenshake(int duration, float xRotPower, float yRotPower, float zRotPower) {
        super(duration);
        this.xRotPower = xRotPower;
        this.yRotPower = yRotPower;
        this.zRotPower = zRotPower;
    }


    @Override
    public float getXRotPower(Camera camera) {
        return powerEquation(camera, xRotPower);
    }

    @Override
    public float getYRotPower(Camera camera) {
        return powerEquation(camera, yRotPower);
    }

    @Override
    public float getZRotPower(Camera camera) {
        return powerEquation(camera, zRotPower);
    }

    protected float powerEquation(Camera camera, float power) {
        power = Mth.clamp(power, 0.0f, 1.0f);
        float percent = 1.0f / duration;
        return (float) Math.pow(Math.max(power - (percent * progress), 0.0), 2);
    }
}
