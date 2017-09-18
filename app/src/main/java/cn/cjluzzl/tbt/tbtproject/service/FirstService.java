package cn.cjluzzl.tbt.tbtproject.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/17 14:58.
 */

public class FirstService extends Service {

    //必须实现的方法
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //Service被创建时回调该方法
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service is Started");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service is Destroy");
    }
}
