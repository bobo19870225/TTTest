package cn.gz3create.tttest;

import android.app.Application;
import android.util.Log;

import com.ss.android.common.util.IResourceCallBack;
import com.ss.android.sdk.minusscreen.SsNewsApi;
import com.ss.android.sdk.minusscreen.common.ISsNewsSdkInitCallback;
import com.ss.android.sdk.minusscreen.common.model.CategoryItem;
import com.ss.android.sdk.minusscreen.detail.ISsNewsSdkCallback;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wanonce on 15/12/29.
 */
public class MinusScreenApplication extends Application {

    private static final String TAG = MinusScreenApplication.class.getSimpleName();
    String clientId = "lephone";

    private static boolean sSdkInited = false;

    public static boolean isSdkInited() {
        return sSdkInited;
    }

    private static Set<ISsNewsSdkInitCallback> sInitCallbacks = new HashSet<ISsNewsSdkInitCallback>();

    public static void registerSdkInitCallback(ISsNewsSdkInitCallback callback) {
        sInitCallbacks.add(callback);
    }

    public static void unRegisterSdkInitCallback(ISsNewsSdkInitCallback callback) {
        sInitCallbacks.remove(callback);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SsNewsApi.init(getApplicationContext(), clientId, new ISsNewsSdkCallback() {
            @Override
            public boolean needMultiCategory() {
                return true;
            }

            @Override
            public Application getApplication() {
                return MinusScreenApplication.this;
            }

            @Override
            public Map<String, CategoryItem> getCategoryMap() {
                return null;
            }

            @Override
            public IResourceCallBack getResourceCallBack() {
                return null;
            }

            @Override
            public void sdkInitComplete() {
                sSdkInited = true;
                if (sInitCallbacks != null) {
                    for (ISsNewsSdkInitCallback ssNewsSdkCallback : sInitCallbacks) {
                        if (ssNewsSdkCallback != null) {
                            ssNewsSdkCallback.sdkInitComplete();
                        }
                    }
                }
                Log.d("MinusScreenSdk", "sdkInitComplete");
            }
        }, true);

    }
}
