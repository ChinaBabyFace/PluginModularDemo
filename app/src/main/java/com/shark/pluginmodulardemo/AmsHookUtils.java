package com.shark.pluginmodulardemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.shark.utils.library.core.SLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AmsHookUtils {
    private Class<?> proxyActivity;

    private Context context;

    public AmsHookUtils(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookAms() {

        //一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);


            //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");

            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject);

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);

            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class AmsInvocationHandler implements InvocationHandler {
        private Object iActivityManagerObject;

        public AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            SLog.e(this, "AmsInvocationHandler:" + method.getName());
            //换掉
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof Intent) {
                    //说明找到了startActivity的Intent参数
                    intent = (Intent) args[i];
                    //这个意图是不能被启动的，因为Acitivity没有在清单文件中注册
                    index = i;
                }
            }

            //伪造一个代理的Intent，代理Intent启动的是proxyActivity
            Intent proxyIntent = new Intent();
            ComponentName componentName = new ComponentName(context, proxyActivity);
            proxyIntent.setComponent(componentName);
            proxyIntent.putExtra("oldIntent", intent);
            args[index] = proxyIntent;
            return method.invoke(iActivityManagerObject, args);
        }
    }

}
