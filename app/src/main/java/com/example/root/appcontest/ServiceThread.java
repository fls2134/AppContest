package com.example.root.appcontest;

import android.os.Message;
import android.os.Handler;

public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;

    public ServiceThread(Handler handler)
    {
        this.handler = handler;
    }

    public void stopForever()
    {
        synchronized (this)
        {
            this.isRun = false;
        }
    }

    @Override
    public void run() {
        //while(isRun)
        //{
        for (int i = 0; i < 10; i++) {
            handler.sendEmptyMessage(0);
            try{
                Thread.sleep(5000);
            }catch (Exception e){}
            
        }
    }
}
