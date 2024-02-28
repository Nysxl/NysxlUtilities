package com.internal.nysxl.NysxlUtilities.Utility;

public interface TriConsumerReturn<T,U,V,R> {
    R apply(T t,U u,V v);
}
