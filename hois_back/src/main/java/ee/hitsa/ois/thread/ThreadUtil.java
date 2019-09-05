package ee.hitsa.ois.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    
    /**
     * Creates thread pool with scaling queue.
     * @param min core pool size
     * @param max maximum pool size
     * @param keepAliveTime keepalive in seconds
     * @return ExecutorService
     */
    public static ExecutorService newScalingThreadPool(int min, int max, long keepAliveTime) {
        ScalingQueue<Runnable> queue = new ScalingQueue<>();
        ThreadPoolExecutor executor = new ScalingThreadPoolExecutor(min, max, keepAliveTime, TimeUnit.SECONDS, queue);
        executor.setRejectedExecutionHandler(new ForceQueuePolicy());
        queue.setThreadPoolExecutor(executor);
        return executor;
    }

}
