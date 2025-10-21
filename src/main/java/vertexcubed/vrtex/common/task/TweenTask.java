package vertexcubed.vrtex.common.task;

import net.minecraft.util.Mth;
import vertexcubed.vrtex.common.math.Easing;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A task that tweens (or lerps) an arbitrary float value by calling {@link Mth#lerp}.
 * This is the most basic task for animation, and is very versatile.
 */
public class TweenTask extends AbstractTask{


    private final boolean maxOnInterrupt;
    private final Consumer<Float> setter;
    private final Supplier<Float> getter;
    private final Easing easing;
    private final Float oldValue;
    private final float newValue;
    private final int duration;
    private long startTime;
    private boolean isInterrupted = false;

    /**
     * @param getter A getter function for the float to tween.
     * @param setter A setter function to tween.
     * @param newValue The new value to tween to.
     * @param duration The duration.
     */
    public TweenTask(Supplier<Float> getter, Consumer<Float> setter, float newValue, int duration) {
        this(true, getter, setter, newValue, duration, Easing.LINEAR);
    }

    /**
     * @param getter A getter function for the float to tween.
     * @param setter A setter function to tween.
     * @param newValue The new value to tween to.
     * @param duration The duration.
     * @param easing An Easing.
     */
    public TweenTask(Supplier<Float> getter, Consumer<Float> setter, float newValue, int duration, Easing easing) {
        this(true, getter, setter, newValue, duration, easing);
    }

    /**
     * @param maxOnInterrupt Should the value be set to the max value if interrupted? T/F
     * @param getter A getter function for the float to tween.
     * @param setter A setter function to tween.
     * @param newValue The new value to tween to.
     * @param duration The duration.
     */
    public TweenTask(boolean maxOnInterrupt, Supplier<Float> getter, Consumer<Float> setter, float newValue, int duration) {
        this(maxOnInterrupt, getter, setter, newValue, duration, Easing.LINEAR);
    }

    /**
     * @param maxOnInterrupt Should the value be set to the max value if interrupted? T/F
     * @param getter A getter function for the float to tween.
     * @param setter A setter function to tween.
     * @param newValue The new value to tween to.
     * @param duration The duration.
     * @param easing An Easing.
     */
    public TweenTask(boolean maxOnInterrupt, Supplier<Float> getter, Consumer<Float> setter, float newValue, int duration, Easing easing) {
        super(UUID.randomUUID());
        this.maxOnInterrupt = maxOnInterrupt;
        this.setter = setter;
        this.getter = getter;
        this.oldValue = getter.get();
        this.newValue = newValue;
//        this.startTime = startTime;
        this.duration = duration;
        this.easing = easing;
    }

    @Override
    public void update(TaskManager context, long gameTime, float partialTick) {
        if(gameTime < startTime) {
            state = TaskState.PENDING;
            return;
        }
        if(isInterrupted) {
            state = TaskState.INTERRUPTED;
            return;
        }
        float normalizedTime = (gameTime - startTime) + partialTick;
        float percent = normalizedTime / duration;
        if(percent >= 1.0f) {
            state = TaskState.COMPLETED;
            return;
        }
        float easedPercent = easing.ease(percent);
        setter.accept(Mth.lerp(easedPercent, oldValue, newValue));
        state = TaskState.PENDING;
        return;
    }

    @Override
    public void init(TaskManager context) {
        this.startTime = context.gameTime();
    }

    @Override
    public void interrupt() {
        if(maxOnInterrupt) {
            setter.accept(newValue);
        }
        isInterrupted = true;
    }

}
