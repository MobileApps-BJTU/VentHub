package com.vxpai.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by 俊成 on 2015/3/27.
 */
public class ImageUtil {

    public static int getImageViewWidth(ImageView imageView)
    {
        final DisplayMetrics displayMetrics = imageView.getContext()
                .getResources().getDisplayMetrics();
        final ViewGroup.LayoutParams params = imageView.getLayoutParams();

        int width = params.width == ViewGroup.LayoutParams.WRAP_CONTENT ? 0 : imageView
                .getWidth(); // Get actual image width
        if (width <= 0)
            width = params.width; // Get layout width parameter
        if (width <= 0)
            width = getImageViewFieldValue(imageView, "mMaxWidth"); // Check

        return width;

    }

    public static Bitmap createCircleImage(Bitmap source, int min)
    {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static Bitmap cutAndRotate(Bitmap src, int x, int y, int width, int height, int degree){
        Matrix matrix = new Matrix();
        matrix.setRotate(degree);

        if(src == null)
            return null;

        if(width > src.getWidth() || height > src.getHeight())
            return null;

        return Bitmap.createBitmap(src, x, y, width, height, matrix, true);
    }

    public static Bitmap cutAndScale(Bitmap src, int x, int y, int width, int height, float sx, float sy){
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy);

        if(src == null)
            return null;

        if(width > src.getWidth() || height > src.getHeight())
            return null;

        return Bitmap.createBitmap(src, x, y, width, height, matrix, true);
    }

    public static Bitmap createSuitableImg(String imgPath, int reqWidth, int reqHeight){
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bmp;
        try {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, options);
            // 调用上面定义的方法计算inSampleSize值
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);
            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false;

            bmp = BitmapFactory.decodeFile(imgPath, options);
        }catch(Exception ex){
            return null;
        }

        return bmp;
    }

    private static int getImageViewFieldValue(Object object, String fieldName)
    {
        int value = 0;
        try
        {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE)
            {
                value = fieldValue;

                Log.e("TAG", value + "");
            }
        } catch (Exception e)
        {
        }
        return value;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight)
    {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight)
        {
            // 计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }
        return inSampleSize;
    }
}
