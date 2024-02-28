package com.internal.nysxl.NysxlUtilities.Utility.ConsumerInterfaces;

public interface QuadConsumer <T, U, V, R>{
    void accept(T t, U u, V v, R r);
}
