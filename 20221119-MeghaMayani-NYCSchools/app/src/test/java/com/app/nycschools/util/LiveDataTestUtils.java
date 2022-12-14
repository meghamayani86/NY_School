package com.app.nycschools.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtils {


    public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException{
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);

        // Don't wait indefinitely if the LiveData is not set.
        if(!latch.await(2, TimeUnit.SECONDS)){
            throw  new RuntimeException("LiveData value was never set.");
        }
        return (T) data[0];
    }

}
