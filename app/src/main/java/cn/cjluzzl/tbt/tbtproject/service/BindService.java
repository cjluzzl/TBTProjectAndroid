package cn.cjluzzl.tbt.tbtproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by
 * "cjluzzl"
 * on 2017/9/17 21:40.
 */

public class BindService extends Service {
    private int count;
    private boolean quit;
    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public int  getCount(){
            return count;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is onCreate");

        new Thread(){
            @Override
            public void run() {
                while(!quit){
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){

                    }
                    count ++;
                }
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is Unbinded");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
        System.out.println("Service is Destroyed");
    }
}
