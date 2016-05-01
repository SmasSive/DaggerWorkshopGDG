/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 */
package com.smassive.daggerworkshopgdg.app;

import com.smassive.daggerworkshopgdg.domain.executor.PostExecutionThread;

import android.os.Handler;
import android.os.Looper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * MainThread (UI Thread) implementation based on a Handler instantiated with the main
 * application Looper.
 */
@Singleton
public class UIThread implements PostExecutionThread {

    private final Handler handler;

    @Inject
    public UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the main thread.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
