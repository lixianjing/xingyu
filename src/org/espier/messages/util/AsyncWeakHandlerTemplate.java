package org.espier.messages.util;

import java.lang.ref.WeakReference;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;

public class AsyncWeakHandlerTemplate<T> extends AsyncQueryHandler {
    public AsyncWeakHandlerTemplate(ContentResolver cr) {
        super(cr);
    }

    WeakReference<T> mObject;

    public void setContext(T object) {
        mObject = new WeakReference<T>(object);
    }

    public T getObject() {
        return mObject != null ? mObject.get() : null;
    }
}
