package com.example.alarm.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;

import java.io.FileOutputStream;
import java.util.List;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

/**
 * Created by Nguyen on 5/20/2016.
 */

public class Util {
    // Orientation hysteresis amount used in rounding, in degrees
    private static final int ORIENTATION_HYSTERESIS = 5;

    private static final String TAG = "Util";
    public static final double meanValueOfBlue = 103.939;
    public static final double meanValueOfGreen = 116.779;
    public static final double meanValueOfRed = 123.68;
    public static final int RESIZED_WIDTH = 160;
    public static final int RESIZED_HEIGHT = 160;

    /**
     * Gets the current display rotation in angles.
     *
     * @param activity
     * @return
     */
    public static int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        /*switch (rotation) {
            case Surface.ROTATION_0:
                return 180;
            case Surface.ROTATION_90:
                return 270;
            case Surface.ROTATION_180:
                return 0;
            case Surface.ROTATION_270:
                return 90;
        }*/
        return 180;
    }

    public static int getDisplayOrientation(int degrees, int cameraId) {
        // See android.hardware.Camera.setDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    public static void prepareMatrix(Matrix matrix, boolean mirror, int displayOrientation,
                                     int viewWidth, int viewHeight) {
        // Need mirror for front camera.
        matrix.setScale(mirror ? -1 : 1, 1);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate(displayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(viewWidth / 2000f, viewHeight / 2000f);
        matrix.postTranslate(viewWidth / 2f, viewHeight / 2f);
    }

    public static int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation = false;
        if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            dist = Math.min(dist, 360 - dist);
            changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
        }
        if (changeOrientation) {
            return ((orientation + 45) / 90 * 90) % 360;
        }
        return orientationHistory;
    }

    public static Size getOptimalPreviewSize(Activity currentActivity,
                                             List<Size> sizes, double targetRatio) {
        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.001;
        if (sizes == null) return null;
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        // Because of bugs of overlay and layout, we sometimes will try to
        // layout the viewfinder in the portrait orientation and thus get the
        // wrong size of preview surface. When we change the preview size, the
        // new overlay will be created before the old one closed, which causes
        // an exception. For now, just get the screen size.
        Point point = getDefaultDisplaySize(currentActivity, new Point());
        int targetHeight = Math.min(point.x, point.y);
        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio. This should not happen.
        // Ignore the requirement.
        if (optimalSize == null) {
            Log.w(TAG, "No preview size match the aspect ratio");
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    @SuppressWarnings("deprecation")
    private static Point getDefaultDisplaySize(Activity activity, Point size) {
        Display d = activity.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            d.getSize(size);
        } else {
            size.set(d.getWidth(), d.getHeight());
        }
        return size;
    }

    public static float[] getBitmapFloat(Bitmap bitmap1)//将bitMap类型的图片转换成float[]
    {
        double[] L1 = getmeanstd(bitmap1);
        int[] intValues = new int[bitmap1.getHeight() * bitmap1.getWidth()];
        float[] floatValues1 = new float[bitmap1.getHeight() * bitmap1.getWidth() * 3];
        bitmap1.getPixels(intValues, 0, bitmap1.getWidth(), 0, 0, bitmap1.getWidth(), bitmap1.getHeight());
        for (int i = 0; i < intValues.length; ++i) {
            final int val = intValues[i];
            floatValues1[i * 3 + 0] = (((val >> 16) & 0xFF) - 127.5f) / 128;
            floatValues1[i * 3 + 1] = (((val >> 8) & 0xFF) - 127.5f) / 128;
            floatValues1[i * 3 + 2] = (((val) & 0xFF) - 127.5f) / 128;
        }
        return floatValues1;
    }

    public static double[] getmeanstd(Bitmap bitmap)//得到图片的均值和方差
    {
        double mean = 0.0;
        double std = 0.0;
        int[] intValues2 = new int[bitmap.getHeight() * bitmap.getWidth()];
        final float[] floatValues2 = new float[bitmap.getHeight() * bitmap.getWidth() * 3];
        bitmap.getPixels(intValues2, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < intValues2.length; ++i) {
            final int val = intValues2[i];
            floatValues2[i * 3 + 0] = ((val >> 16) & 0xFF);
            floatValues2[i * 3 + 1] = ((val >> 8) & 0xFF);
            floatValues2[i * 3 + 2] = (val & 0xFF);
            mean = mean + floatValues2[i * 3 + 0] + floatValues2[i * 3 + 1] + floatValues2[i * 3 + 2];
        }
        mean = mean / (bitmap.getWidth() * bitmap.getHeight() * 3);
        for (int i = 0; i < floatValues2.length; i++) {
            std = std + Math.pow(floatValues2[i] - mean, 2);
        }
        std = Math.pow(std / (bitmap.getWidth() * bitmap.getHeight() * 3), 0.5);
        double[] L = {mean, std};
        return L;
    }

    public static float[] getPixel(Bitmap bitmap, int resizedWidth, int resizedHeight) {
        int channel = 3;
        float[] buff = new float[channel * resizedWidth * resizedHeight];

        int rIndex, gIndex, bIndex;
        int k = 0;
        for (int i = 0; i < resizedHeight; i++) {
            for (int j = 0; j < resizedWidth; j++) {
                bIndex = i * resizedWidth + j;
                gIndex = bIndex + resizedWidth * resizedHeight;
                rIndex = gIndex + resizedWidth * resizedHeight;

                int color = bitmap.getPixel(j, i);

                buff[rIndex] = (float) ((red(color) - meanValueOfRed)) / 255;
                buff[gIndex] = (float) ((green(color) - meanValueOfGreen)) / 255;
                buff[bIndex] = (float) ((blue(color) - meanValueOfBlue)) / 255;
            }
        }
        return buff;
    }

    //图片数据预处理
    public static Bitmap bitmapPreproccess(Bitmap bitmap) {
        Bitmap bitmapPreproccessed = bitmap.copy(Bitmap.Config.RGB_565, false);
        bitmapPreproccessed = Bitmap.createScaledBitmap(bitmapPreproccessed, 160, 160, true);
        return bitmapPreproccessed;
    }

    public static void saveBitmapToSDCard(Bitmap bitmap, String imagename) {
        String path = "/sdcard/test2/" + imagename + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
