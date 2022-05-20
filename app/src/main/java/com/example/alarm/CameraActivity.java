package com.example.alarm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.utils.FileUtil;
import com.example.alarm.utils.ImgUtil;
import com.example.alarm.utils.Util;
import com.example.alarm.widget.CameraPreview;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 相机预览
 */
public class CameraActivity extends AppCompatActivity {
    private static String TAG = CameraActivity.class.getSimpleName();
    @BindView(R.id.state)
    TextView state;
    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;
    @BindView(R.id.iv_enter_image)
    ImageView ivEnterImage;
    private static final int FRONT = 1;//前置摄像头标记
    private static final int BACK = 0;//后置摄像头标记
    @BindView(R.id.scanHorizontalLineImageView)
    ImageView scanHorizontalLineImageView;
    private int currentCameraType = 0;//当前打开的摄像头标记
    private Camera mCamera;
    private CameraPreview mPreview;
    private String sdPath, caremaPhotoPath;
    private int mDisplayOrientation;
    private boolean isThreadWorking = false;
    private FaceDetectThread detectThread;
    private boolean isMove = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        caremaPhotoPath = sdPath + "/temp.jpg";

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });
    }

    // 拍照回调
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (!isThreadWorking) {
                isThreadWorking = true;
                detectThread = new FaceDetectThread();
                detectThread.setData(data);
                detectThread.start();
            }
        }
    };

    private void updateResult(final String imgFilePath) throws IOException {
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        String path = sdPath + "/veryfy" + sdf.format(new Date()) + ".jpg";
        Bitmap bitmapZoom = ImgUtil.zoomPic(imgFilePath, 640, 480, Bitmap.Config.ARGB_8888);
        if (bitmapZoom == null) {
            makeToast("图片数据有问题");
            return;
        }
        ImgUtil.saveJPGE_After(bitmapZoom, path, 100);*/
        Log.e("==========", " 旋转的角度 " + ImgUtil.readPictureDegree(imgFilePath));


        ivEnterImage.post(new Runnable() {
            @Override
            public void run() {
                mCamera.stopPreview(); //解决预览停止问题
                Bitmap bitmap = ImgUtil.getBitmapByPath(imgFilePath);
                bitmap = ImgUtil.rotatingImage(mDisplayOrientation, bitmap);//旋转图片
                bitmap = drawTextToLeftBottom(CameraActivity.this,bitmap,"san",80, Color.BLUE,100,20);
                ivEnterImage.setImageBitmap(bitmap);
                mCamera.startPreview();//解决预览停止问题
            }
        });

        //FileUtil.deleteFile(new File(imgFilePath));
    }

    // 开始预览相机
    public void openCamera() {

        if (mCamera == null) {
            mCamera = getCameraInstance();
            int mDisplayRotation = Util.getDisplayRotation(CameraActivity.this);
            mDisplayOrientation = Util.getDisplayOrientation(mDisplayRotation, currentCameraType);
            if (mCamera != null){
                mCamera.setDisplayOrientation(mDisplayOrientation);
                Camera.Parameters params = mCamera.getParameters(); //解决预览框停止问题 无效
                setAutoFocus(params);
                //params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);//解决预览框停止问题 无效
                mCamera.setParameters(params);
            }
            openCaneraView();
        }
    }

    private void setAutoFocus(Camera.Parameters cameraParameters) {
        List<String> focusModes = cameraParameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
            cameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
    }

    private void openCaneraView() {
        if (mCamera != null) {
            mPreview = new CameraPreview(this, mCamera);
            cameraPreview.removeAllViews();
            cameraPreview.addView(mPreview);
            /*if(currentCameraType == FRONT && currentCameraType != -1){
                cameratype.setText("正在测试前置摄像头(点击此处切换)");
            }else if(currentCameraType == BACK && currentCameraType != -1){
                cameratype.setText("正在测试后置摄像头(点击此处切换)");
            }*/
        } else {
            makeToast("相机不可用");
        }
    }

    /*   // 获取相机
       public  Camera getCameraInstance() {
           Camera c = null;
           int id = getDefaultCameraId();
           Log.e("----",""+id);
           try {
               c = Camera.open(id);
           } catch (Exception e) {
               e.printStackTrace();
           }
           return c;
       }*/
    // 获取相机
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    private int getDefaultCameraId() {
        int defaultId = -1;
        int mNumberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                defaultId = i;
                currentCameraType = defaultId;
            } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultId = i;
                currentCameraType = defaultId;
            }
        }
        if (-1 == defaultId) {
            if (mNumberOfCameras > 0) {
                defaultId = 0;
            }
        }
        currentCameraType = defaultId;
        return defaultId;
    }

    private Camera openCamera(int type) {
        int frontIndex = -1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
            Camera.getCameraInfo(cameraIndex, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                frontIndex = cameraIndex;
            } else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                backIndex = cameraIndex;
            }
        }
        currentCameraType = type;
        if (type == FRONT && frontIndex != -1) {
            return Camera.open(frontIndex);
        } else if (type == BACK && backIndex != -1) {
            return Camera.open(backIndex);
        }
        return null;
    }

    // 判断相机是否支持
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启
        if (!checkCameraHardware(this)) {
            makeToast("相机不支持");
        } else {
            if (mCamera == null) {
                openCamera();
            }
        }
        cameraPreview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isMove) {
                    isMove = false;
                    int[] location = new int[2];
                    // getLocationInWindow方法要在onWindowFocusChanged方法里面调用
                    // 个人理解是onCreate时，View尚未被绘制，因此无法获得具体的坐标点
                    cameraPreview.getLocationInWindow(location);

                    // 模拟的mPreviewView的左右上下坐标坐标
                    int left = cameraPreview.getLeft();
                    int right = cameraPreview.getRight();
                    int top = cameraPreview.getTop();
                    int bottom = cameraPreview.getBottom();

                    // 从上到下的平移动画
                    TranslateAnimation verticalAnimation = new TranslateAnimation(left, left, top - 330, bottom - 330);
                    verticalAnimation.setDuration(3000); // 动画持续时间
                    verticalAnimation.setRepeatCount(Animation.INFINITE); // 无限循环

                    // 播放动画
                    scanHorizontalLineImageView.setAnimation(verticalAnimation);
                    verticalAnimation.startNow();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    // 释放相机
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * Do face detect in thread
     */
    private class FaceDetectThread extends Thread {
        private byte[] data = null;
        //private Context ctx;

        public FaceDetectThread() {
            //this.ctx = ctx;
        }


        public void setData(byte[] data) {
            this.data = data;
        }

        public void run() {
            FileUtil.writeByteArrayToFile(data, caremaPhotoPath);
            if (caremaPhotoPath != null) {
                try {
                    updateResult(caremaPhotoPath);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            /*if (caremaPhotoPath != null) {

                path = publicFilePath + "/temporary.jpg";
                Bitmap bitmapZoom = ImgUtil.zoomPic(caremaPhotoPath, 640, 480, Bitmap.Config.ARGB_8888);
                if(bitmapZoom != null)
                    ivFaceimage.post(new Runnable() {
                        @Override
                        public void run() {
                            ivFaceimage.setImageBitmap(bitmapZoom);
                        }
                    });
                ImgUtil.saveJPGE_After(bitmapZoom, path, 80);
            }*/
            isThreadWorking = false;
        }
    }

    /* 绘制文字到左下方
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @param paddingLeft
     * @param paddingBottom
     * @return
     */
    public static Bitmap drawTextToLeftBottom(Context context, Bitmap bitmap, String text, int size, int color, int paddingLeft, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                dp2px(context, paddingLeft),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text, Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }

}
