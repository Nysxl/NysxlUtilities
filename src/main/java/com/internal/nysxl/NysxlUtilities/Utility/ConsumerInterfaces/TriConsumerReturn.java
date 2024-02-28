package com.internal.nysxl.NysxlUtilities.Utility.ConsumerInterfaces;

public interface TriConsumerReturn<T,U,V, R> {
    R apply(T t,U u,V v);
}
