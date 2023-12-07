package com.sdm.mgpa1;

public interface GenericSubscriber<T>
{
    public abstract void Tick(T t);
}
