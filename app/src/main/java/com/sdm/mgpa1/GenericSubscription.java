package com.sdm.mgpa1;

import java.util.ArrayList;
import java.util.List;

public class GenericSubscription<T>
{
    List<GenericSubscriber<T>> _subscribers;

    public GenericSubscription() {_subscribers = new ArrayList<>();}
    public void subscribe(GenericSubscriber<T> subscriber) {_subscribers.add(subscriber);}
    public void unsubscribe(GenericSubscriber<T> subscriber) {_subscribers.remove(subscriber);}
    public void invoke(T t)
    {
        for (int i = 0; i < _subscribers.size(); ++i)
            _subscribers.get(i).Tick(t);
    }
}
