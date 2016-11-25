package com.example.enid.myapplication.util;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;


import com.example.enid.myapplication.app.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ResourcesUtil
{
	// ----------------------resources

	public static Resources getResources()
	{
		return MyApplication.getInstance().getApplicationContext().getResources();
	}

	public static Drawable getDrawable(int resId)
	{
		return getResources().getDrawable(resId);
	}

	public static String getString(int resId)
	{
		return getResources().getString(resId);
	}

	public static int getColor(int resId)
	{
		return getResources().getColor(resId);
	}

	public static float getDimension(int resId)
	{
		return getResources().getDimension(resId);
	}

	public static int getDimensionPixelOffset(int resId)
	{
		return getResources().getDimensionPixelOffset(resId);
	}

	public static int getDimensionPixelSize(int resId)
	{
		return getResources().getDimensionPixelSize(resId);
	}

	public static boolean copyAssetsFileTo(String assetsFileName, String toFile)
	{
		boolean result = false;
		try
		{
			AssetManager manager = getAssetManager();
			InputStream is = manager.open(assetsFileName);

			File to = new File(toFile);
			if (to.exists())
			{
				to.delete();
			}
			to.createNewFile();

			FileOutputStream fos = new FileOutputStream(to);
			byte[] temp = new byte[1024];
			int len = 0;
			while ((len = is.read(temp)) > 0)
			{
				fos.write(temp, 0, len);
			}
			fos.close();
			is.close();
			result = true;
		} catch (Exception e)
		{
			result = false;
		}
		return result;
	}

	public static AssetManager getAssetManager()
	{
		return MyApplication.getInstance().getAssets();
	}

}
