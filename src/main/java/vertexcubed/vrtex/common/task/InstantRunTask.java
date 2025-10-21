package vertexcubed.vrtex.common.task;

import java.util.UUID;

/**
 * Simple task that runs a runnable upon execution, then completes itself.
 */
public class InstantRunTask extends AbstractTask {

    private final Runnable run;
    public InstantRunTask(Runnable run) {
        super(UUID.randomUUID());
        this.run = run;
    }

    @Override
    public void update(TaskManager context, long gameTime, float partialTick) {
        run.run();
        state = TaskState.COMPLETED;
    }

    @Override
    public void init(TaskManager context) {

    }

    @Override
    public void interrupt() {

    }
}
