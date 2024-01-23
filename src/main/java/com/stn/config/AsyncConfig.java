package com.stn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }

    /*
        AsyncConfig 클래스는 Spring에서 비동기 처리를 위한 설정을 정의하는 역할을 합니다.
        이 클래스에서는 @EnableAsync 어노테이션을 사용하여 비동기 처리를 활성화하고,
        ThreadPoolTaskExecutor를 빈으로 등록하여 비동기 처리를 수행할 스레드 풀을 설정합니다.

        여기서 주요한 설정은 ThreadPoolTaskExecutor의 설정입니다. 아래는 주요 설정들에 대한 설명입니다.

        @EnableAsync 어노테이션은 스프링에게 비동기 처리를 사용한다는 것을 알려주는 역할을 합니다.
        @Bean 메서드:

        asyncExecutor라는 이름으로 빈을 생성합니다.
        ThreadPoolTaskExecutor 클래스를 사용하여 스레드 풀을 구성합니다.
        Executor 설정:

        setMaxPoolSize(10): 최대 스레드 풀 크기를 10으로 설정합니다. 이는 최대 동시에 실행되는 비동기 작업의 수를 의미합니다.
        setThreadNamePrefix("AsyncThread-"): 생성되는 각 스레드의 이름에 "AsyncThread-"를 접두사로 붙입니다.
        initialize(): 스레드 풀을 초기화합니다.
        이렇게 구성된 스레드 풀을 사용하여 @Async 어노테이션이 붙은 메서드들이 비동기적으로 실행될 수 있습니다.
        설정에 따라서는 필요에 따라 스레드 풀의 크기나 다른 속성들을 조절할 수 있습니다.

     */
}
