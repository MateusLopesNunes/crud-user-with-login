package br.com.managementSystem.configuration.threads;

import br.com.managementSystem.exception.EmailNotFoundException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

import java.lang.reflect.Method;

@Configuration
public class AsyncConfiguraton extends AsyncConfigurerSupport {

    public AsyncUncaughtExceptionHandler asyncHandler() {
        return new AsyncUncaughtExceptionHandler() {

            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                System.out.println(throwable.getMessage());
                System.out.println(method.getName());
                System.out.println(objects.length);
            }
        };
    }
}
