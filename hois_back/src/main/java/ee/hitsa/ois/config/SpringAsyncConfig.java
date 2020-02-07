package ee.hitsa.ois.config;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    
    /** Amount of active threads */
    @Value("${hois.async.corePoolSize}")
    private int CORE_POOL_SIZE;
    /**
     * Amount of maximum active threads with condition that an queue is full.
     * In case if the queue is full and new tasks are coming then
     * it will increase amount of active threads.
     */
    @Value("${hois.async.maxPoolSize}")
    private int MAX_POOL_SIZE;
    /** Size of queue */
    @Value("${hois.async.queueCapacity}")
    private int QUEUE_CAPACITY;
    private static final String THREADS_NAME = "Spring-Async-";
    
    static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix(THREADS_NAME);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
    
    public static class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            LOG.error("Unexpected asynchronous exception at : "
                    + method.getDeclaringClass().getName() + "." + method.getName(), ex);
        }
        
    }
}
