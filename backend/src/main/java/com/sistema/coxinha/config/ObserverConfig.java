package com.sistema.coxinha.config;

import com.sistema.coxinha.observer.EstoquePublisher;
import com.sistema.coxinha.observer.LogEstoqueObserver;
import com.sistema.coxinha.observer.NotificacaoObserver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ObserverConfig {

    private final EstoquePublisher publisher;
    private final LogEstoqueObserver logObserver;
    private final NotificacaoObserver notificacaoObserver;

    @PostConstruct
    public void setup() {
        publisher.subscribe(logObserver);
        publisher.subscribe(notificacaoObserver);
    }
}
