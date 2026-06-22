package com.sistema.coxinha.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EstoquePublisher {

    private final List<EstoqueObserver> observers = new ArrayList<>();

    public void subscribe(EstoqueObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(EstoqueObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String produto, Integer quantidade) {
        observers.forEach(o -> o.atualizar(produto, quantidade));
    }
}
