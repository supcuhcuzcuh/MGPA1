package com.sdm.mgpa1;

import java.util.ArrayList;
import java.util.List;

public class Subscription
{
    List<Subscriber> _subscribers;

    public Subscription() {_subscribers = new ArrayList<>();}
    public void subscribe(Subscriber subscriber) {_subscribers.add(subscriber);}
    public void unsubscribe(Subscriber subscriber) {_subscribers.remove(subscriber);}
    public void invoke()
    {
        for (int i = 0; i < _subscribers.size(); ++i)
            _subscribers.get(i).Tick();
    }
}
