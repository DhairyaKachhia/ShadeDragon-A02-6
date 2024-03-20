package com.example.skylink;

import android.os.IBinder;
import android.view.WindowManager;
import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.RootMatchers;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        int type = root.getWindowLayoutParams().get().type;
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            IBinder windowToken = root.getDecorView().getWindowToken();
            IBinder appToken = root.getDecorView().getApplicationWindowToken();
            if (windowToken == appToken) {
                // Means this window isn't contained by any other windows.
                return true;
            }
        }
        return false;
    }

    public static ToastMatcher isToast() {
        return new ToastMatcher();
    }
}
