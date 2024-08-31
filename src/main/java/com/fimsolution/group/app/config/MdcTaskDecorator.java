package com.fimsolution.group.app.config;

import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class MdcTaskDecorator implements TaskDecorator {


    @NotNull
    @Override
    public Runnable decorate(@NotNull Runnable runnable) {

        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            try {
                // Set the MDC context before execution
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                // Clear the MDC context after execution
                MDC.clear();
            }
        };
    }
}
