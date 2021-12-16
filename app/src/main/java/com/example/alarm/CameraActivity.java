package com.example.alarm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private int currentCameraType = 0;//当前打开的摄像头标记
    private Camera mCamera;
    private CameraPreview mPreview;
    private String sdPath,caremaPhotoPath;
    private int mDisplayOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        caremaPhotoPath = sdPath + "/temp.jpg";
        //开启
        if (!checkCameraHardware(this)) {
            makeToast("相机不支持");
        } else {
            if(mCamera ==null){
                openCamera();
            }
        }
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
            FileUtil.writeByteArrayToFile(data,caremaPhotoPath);
            if (caremaPhotoPath != null) {
                try {
                    updateResult(caremaPhotoPath);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };
    private void updateResult(String imgFilePath) throws IOException {
        /*SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
        String path = sdPath + "/veryfy" + sdf.format(new Date()) + ".jpg";
        Bitmap bitmapZoom = ImgUtil.zoomPic(imgFilePath, 640, 480, Bitmap.Config.ARGB_8888);
        if (bitmapZoom == null) {
            makeToast("图片数据有问题");
            return;
        }
        ImgUtil.saveJPGE_After(bitmapZoom, path, 100);*/
       Log.e("=========="," 旋转的角度 " + ImgUtil.readPictureDegree(imgFilePath));

        Bitmap bitmap = ImgUtil.getBitmapByPath(imgFilePath);
        bitmap = ImgUtil.rotatingImage(90,bitmap);
        ivEnterImage.setImageBitmap(bitmap);
        //FileUtil.deleteFile(new File(imgFilePath));
    }
    // 开始预览相机
    public void openCamera(){

        if(mCamera == null){
            mCamera = getCameraInstance();
            int mDisplayRotation = Util.getDisplayRotation(CameraActivity.this);
            mDisplayOrientation = Util.getDisplayOrientation(mDisplayRotation, currentCameraType);
            if (mCamera != null)
                mCamera.setDisplayOrientation(mDisplayOrientation);
            openCaneraView();
        }
    }
    private void openCaneraView(){
        if(mCamera!=null){
            mPreview = new CameraPreview(this, mCamera);
            cameraPreview.removeAllViews();
            cameraPreview.addView(mPreview);
            /*if(currentCameraType == FRONT && currentCameraType != -1){
                cameratype.setText("正在测试前置摄像头(点击此处切换)");
            }else if(currentCameraType == BACK && currentCameraType != -1){
                cameratype.setText("正在测试后置摄像头(点击此处切换)");
            }*/
        }else {
            makeToast("相机不可用");
        }
    }
    // 获取相机
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
    }
    private int getDefaultCameraId() {
        int defaultId = -1;
        int	mNumberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                defaultId = i;
                currentCameraType = defaultId;
            }else if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
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
    private Camera openCamera(int type){
        int frontIndex =-1;
        int backIndex = -1;
        int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for(int cameraIndex = 0; cameraIndex<cameraCount; cameraIndex++){
            Camera.getCameraInfo(cameraIndex, info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                frontIndex = cameraIndex;
            }else if(info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                backIndex = cameraIndex;
            }
        }
        currentCameraType = type;
        if(type == FRONT && frontIndex != -1){
            return Camera.open(frontIndex);
        }else if(type == BACK && backIndex != -1){
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

    public void	makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
