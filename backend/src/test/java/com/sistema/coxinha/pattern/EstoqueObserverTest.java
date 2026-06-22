package com.sistema.coxinha.pattern;

import com.sistema.coxinha.observer.EstoqueObserver;
import com.sistema.coxinha.observer.EstoquePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EstoqueObserverTest {

    private EstoquePublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new EstoquePublisher();
    }

    @Test
    void shouldNotifySubscribedObservers() {
        List<String> notifications = new ArrayList<>();
        EstoqueObserver observer = (produto, quantidade) ->
                notifications.add(produto + ":" + quantidade);

        publisher.subscribe(observer);
        publisher.notifyObservers("FRANGO", 5);

        assertThat(notifications).containsExactly("FRANGO:5");
    }

    @Test
    void shouldNotNotifyAfterUnsubscribe() {
        List<String> notifications = new ArrayList<>();
        EstoqueObserver observer = (produto, quantidade) ->
                notifications.add(produto + ":" + quantidade);

        publisher.subscribe(observer);
        publisher.unsubscribe(observer);
        publisher.notifyObservers("FRANGO", 5);

        assertThat(notifications).isEmpty();
    }

    @Test
    void shouldNotifyAllSubscribedObservers() {
        List<String> notifications = new ArrayList<>();
        EstoqueObserver observer1 = (produto, quantidade) ->
                notifications.add("obs1:" + produto + ":" + quantidade);
        EstoqueObserver observer2 = (produto, quantidade) ->
                notifications.add("obs2:" + produto + ":" + quantidade);

        publisher.subscribe(observer1);
        publisher.subscribe(observer2);
        publisher.notifyObservers("CATUPIRY", 3);

        assertThat(notifications).containsExactlyInAnyOrder(
                "obs1:CATUPIRY:3", "obs2:CATUPIRY:3"
        );
    }

    @Test
    void shouldHandleMultipleNotifications() {
        List<String> notifications = new ArrayList<>();
        EstoqueObserver observer = (produto, quantidade) ->
                notifications.add(produto + ":" + quantidade);

        publisher.subscribe(observer);
        publisher.notifyObservers("FRANGO", 10);
        publisher.notifyObservers("CATUPIRY", 5);
        publisher.notifyObservers("CALABRESA", 0);

        assertThat(notifications).containsExactly(
                "FRANGO:10", "CATUPIRY:5", "CALABRESA:0"
        );
    }
}
