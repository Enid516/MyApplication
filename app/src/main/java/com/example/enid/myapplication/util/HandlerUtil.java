package com.example.enid.myapplication.util;

import android.os.Handler;
import android.os.Looper;

public class HandlerUtil
{

	private static Handler mHandler = new Handler(Looper.getMainLooper());

	public synchronized static void runOnUiThread(Runnable r)
	{
		mHandler.post(r);
	}

	public synchronized static void runOnUiThreadFrontOfQueue(Runnable r)
	{
		mHandler.postAtFrontOfQueue(r);
	}

	public synchronized static void runOnUiThreadAtTime(Runnable r, long uptimeMillis)
	{
		mHandler.postAtTime(r, uptimeMillis);
	}

	public synchronized static void runOnUiThreadAtTime(Runnable r, Object msgObj, long uptimeMillis)
	{
		mHandler.postAtTime(r, msgObj, uptimeMillis);
	}

	public synchronized static void runOnUiThreadDelayed(Runnable r, long delayMillis)
	{
		mHandler.postDelayed(r, delayMillis);
	}

}
