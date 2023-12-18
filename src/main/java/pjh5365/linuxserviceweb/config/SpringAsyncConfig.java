package pjh5365.linuxserviceweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Bean("threadPool")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);    // 기본 쓰레드 개수는 5
        executor.setMaxPoolSize(30);    // 최대 쓰레드 개수는 30
        executor.setQueueCapacity(100); // 내부적으로 core 사이즈만큼의 스레드에서 작업을 처리할 수 없을 경우 Queue 에서 대기하는데 해당 Queue 의 사이즈는 100
        return executor;
    }
}
