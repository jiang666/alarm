package com.example.alarm.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import com.example.alarm.R;

public class ColorDrawableGenerator {
    public static final int DP = 0;
    public static final int PIXEL = 1;

    public static Bitmap generateBackground(Context context, int color, int resId) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.full_room_floor);
        int h = bitmap.getHeight();
        int w = bitmap.getWidth();
        Bitmap target = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));

        canvas.drawBitmap(bitmap, 0, 0, paint);
        return target;
    }

    public static Bitmap generateBackground(Context context, int color, int resId, float headerCutoff) {
        Bitmap bitmap = generateBackground(context, color, resId);
        if (headerCutoff <= 0 || headerCutoff > 1) {
            return bitmap;
        } else {
            return Bitmap.createBitmap(bitmap, 0, (int) (bitmap.getHeight() * headerCutoff), bitmap.getWidth(), (int) (bitmap.getHeight() * (1 - headerCutoff)));
        }
    }

    public static Bitmap generateBackground(Context context, int color, int resId, int offsetX, int offsetY, int offsetType) {
        if (offsetType == DP) {
            final float scale = context.getResources().getDisplayMetrics().density;
            offsetX = (int) (offsetX * scale + 0.5f);
            offsetY = (int) (offsetY * scale + 0.5f);
        }
        Bitmap bitmap = generateBackground(context, color, resId);
        if (offsetX == 0 && offsetY == 0) {
            return bitmap;
        } else {
            return Bitmap.createBitmap(bitmap, offsetX, offsetY, bitmap.getWidth() - offsetX, bitmap.getHeight() - offsetY);
        }
    }

    // Generate color icon
    public static Bitmap generateColorIcon(Context context, int color, int resId) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        int h = bitmap.getHeight();
        int w = bitmap.getWidth();
        Bitmap target = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        canvas.drawBitmap(bitmap, 0, 0, paint);
        return target;
    }

    public static Drawable coloredIcon(int color, Drawable origIcon) {
        float red = Color.red(color) / 255f;
        float green = Color.green(color) / 255f;
        float blue = Color.blue(color) / 255f;
        float[] matrix = {red, red, red, red, red
                , green, green, green, green, green
                , blue, blue, blue, blue, blue
                , 1, 1, 1, 1, 1};
        ColorFilter colorFilter = new ColorMatrixColorFilter(matrix);

        if (origIcon == null) {
            return null;
        }

        Drawable colored_icon = origIcon.mutate();
        colored_icon.setColorFilter(colorFilter);
        return colored_icon;
    }

    public static Paint coloredPaint(int color) {
        Paint paint = new Paint();
        float red = Color.red(color) / 255f;
        float green = Color.green(color) / 255f;
        float blue = Color.blue(color) / 255f;
        float[] matrixcolor = {red, red, red, red, red
                , green, green, green, green, green
                , blue, blue, blue, blue, blue
                , 1, 1, 1, 1, 1};
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(matrixcolor);
        paint.setAntiAlias(true);
        paint.setColorFilter(new ColorMatrixColorFilter(matrixcolor));
        return paint;
    }
}

