package com.example.alarm;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alarm.utils.ImgUtil;

import net.codingpark.serialport.SerialPort;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;


/**
 * 串口读取
 */
public class SerialPortSentImageActivity extends AppCompatActivity {
    private static String TAG = SerialPortSentImageActivity.class.getSimpleName();
    private SerialPort m_SerialPort = null;
    private ReadCardThread readCardThread;
    private static byte[] IMAGE_BYTE = { 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, (byte)0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20,(byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x93, 0x20, (byte)0x87, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x27, 0x20, 0x23, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03, 0x20, 0x03};
    private InputStream mInputStream = null;
    private OutputStream mOutputStream = null;
    private TextView serisl;
    //下发包计数
    private int sendindex = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //sendindex = 1 第一次调用无需判断是否需要添加
            if(sendindex != 1 && num_sensor != sendindex-1) sendindex = sendindex -1;
            if(sendindex < total+1){
                byte[] bytes = toBytes(sendindex);
                Log.e("======="," send " + byteArrayToHexStr(bytes));
                sendindex = sendindex + 1;
                send_order(bytes);
            }

        }
    };
    private ImageView iv_send;
    private byte[] mac;
    private int num_sensor;
    private int total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port);
        serisl = (TextView)findViewById(R.id.tv_read_serial);
        iv_send = (ImageView)findViewById(R.id.iv_send);
        String tty2 = "/dev/ttyACM0";
        serisl.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sendindex = 1;
                mHandler.sendEmptyMessage(0);
                return false;
            }
        });
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"test_zhuopai.png";
        Log.e(TAG, filepath);
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bitmap = ImgUtil.getBitmapByPath(filepath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iv_send.setImageBitmap(bitmap);
                            initSerialPort(tty2);
                        }
                    });
                }
            }).start();

        }

    }

    private void initSerialPort(String serial) {
        try {
            m_SerialPort = new SerialPort(new File(serial), 115200, 0);
            mInputStream = m_SerialPort.getInputStream();
            mOutputStream = m_SerialPort.getOutputStream();
           /* if (read_type == TYPE_RCRB)
                send_order();*/
            Log.e(TAG,serial + " 串口开启成功");
            readCardThread = new ReadCardThread();
            readCardThread.start();
            send_order(getMac());
            if(IMAGE_BYTE.length%20 ==0){
                total = IMAGE_BYTE.length/20;
            }else {
                total = IMAGE_BYTE.length/20 +1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,serial + " 串口开启失败");
        }
    }
    /**
     * 卡上报数据监控线程
     */
    public class ReadCardThread extends Thread {

        static final int icreadNumberforlength = 100;
        private byte[] icreadNumberfor = new byte[icreadNumberforlength];

        public ReadCardThread() {
        }
        private volatile boolean flag = true;
        public void stopTask() {
            flag = false;
        }

        @Override
        public void run() {
            super.run();
            while (flag) {
                int count;
                try {
                    count = mInputStream.read(icreadNumberfor);
                    byte[] firstResult = Arrays.copyOfRange(icreadNumberfor, 0, count);
                    Log.e("========", " receive " + byteArrayToHexStr(firstResult));
                    mac = Arrays.copyOfRange(icreadNumberfor, 1, 6);
                    byte[] respondAck = Arrays.copyOfRange(icreadNumberfor, 27, 29);
                    byte[] b = new byte[2];
                    b[1] = respondAck[0];
                    b[0] = respondAck[1];
                    num_sensor = Integer.valueOf(byteArrayToHexStr(b), 16);
                    //Log.e("========"," sendindex = " + sendindex +"  " + IMAGE_BYTE.length + "   " + (IMAGE_BYTE.length/20 +1));
                    mHandler.sendEmptyMessageDelayed(0,30);
                    //Log.e(TAG, byteArrayToHexStr(firstResult) + "  " + count + " imagelength = " + IMAGE_BYTE.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 指令下发函数
     */
    void send_order(String order) {

        try {
            mOutputStream.write(order.getBytes());
            Log.e("提示", "===串口下发指令成功===");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("提示", "===串口下发指令失败===");
        }
    }

    /**
     * 指令下发函数
     */
    void send_order(byte[] order) {

        try {
            mOutputStream.write(order);
            Log.e("提示", "===串口下发指令成功===");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("提示", "===串口下发指令失败===");
        }
    }
    /**
     * 下发图片
     * 方 法 名 : toBytes(int size)
     * 功    能： 封装下发数据数组
     * @param size : 第几个包
     * @return byte[] 返回所有字段的字节数组
     */
    public byte[] toBytes(int size) {
        byte[] buffer = new byte[29];
        buffer[0] = (byte) 0xFF;
        buffer[1] = (byte) 0x02;
        buffer[2] = (byte) (total & 0xFF);
        buffer[3] = (byte) ((total >> 8) & 0xFF);
        for (int i = 2; i < 5; i++) {
            buffer[i+2] = mac[i];
        }
        //System.arraycopy(mac, 0, buffer, 2, mac.length);
        for (int i = 0; i < 20; i++) {
            int index = (size-1)*20 + i;
            if(index >= IMAGE_BYTE.length){
                buffer[i+7] = (byte)0x00;
            }else {
                buffer[i+7] = IMAGE_BYTE[(size-1)*20 + i];
            }
        }
        buffer[27] =(byte) (size & 0xFF);
        buffer[28] =(byte) ((size >> 8) & 0xFF);
        return addCRC(buffer);
    }

    /**
     * 方 法 名 : toBytes(byte[] data)
     * 功    能： 封装下发数据数组
     * @return byte[] 返回所有字段的字节数组
     */
    public byte[] getMac() {
        byte[] buffer = new byte[29];
        // 1. Header
        buffer[0] = (byte) 0xFF;

        buffer[1] = (byte) 0x01;
        for (int i = 2; i < 27; i++) {
            buffer[i] = (byte) 0x00;
        }
        buffer[27] = (byte) 0x01;
        buffer[28] = (byte) 0x00;
        return addCRC(buffer);
    }

    /**
     * byte 转 16进制字符串
     * @param byteArray
     * @return
     */
    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 关闭串口
     */
    public void close() {
        if(m_SerialPort != null) m_SerialPort.close();
        if(readCardThread != null)readCardThread.stopTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    /**
     * 添加字节(添加CRC校验位)
     */
    private static byte[] addCRC(byte[] bytes){
        byte[] check = toNewHH(calcCrc16(bytes));//获取crc校验值  toNewHH高低位调换方法（看协议是否需要）
        byte[] new_byte = new byte[bytes.length +3];
        System.arraycopy(bytes, 0, new_byte, 0, bytes.length);
        new_byte[new_byte.length -3] = check[0];
        new_byte[new_byte.length -2] = check[1];
        new_byte[new_byte.length -1] = (byte) 0xEE;
        return new_byte;
    }
    //高低位调换
    public static byte[] toNewHH(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    /**
     * 计算CRC16校验
     *
     * @param data
     *            需要计算的数组
     * @return CRC16校验值
     */
    public static int calcCrc16(byte[] data) {
        return calcCrc16(data, 0, data.length, 0xffff);
    }

    /**
     * 计算CRC16校验
     *
     * @param data
     *            需要计算的数组
     * @param offset
     *            起始位置
     * @param len
     *            长度
     * @param preval
     *            之前的校验值
     * @return CRC16校验值
     */
    public static int calcCrc16(byte[] data, int offset, int len, int preval) {
        byte[] crc16_tab_h = {
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1,
                (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1,
                (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,

                (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1,
                (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,
                (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01,(byte) 0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0,(byte) 0x80, (byte)0x41,

                (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81,(byte) 0x40, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40,(byte) 0x00, (byte)0xC1, (byte)0x81, (byte)0x40,
                (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1,
                (byte)0x81, (byte)0x40, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41,
                (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40, (byte)0x01, (byte)0xC0, (byte)0x80, (byte)0x41, (byte)0x01, (byte)0xC0,
                (byte)0x80, (byte)0x41, (byte)0x00, (byte)0xC1, (byte)0x81, (byte)0x40
        } ;
        byte[] crc16_tab_l = {
                (byte)0x00, (byte)0xC0, (byte)0xC1, (byte)0x01, (byte)0xC3, (byte)0x03, (byte)0x02, (byte)0xC2, (byte)0xC6, (byte)0x06,
                (byte)0x07, (byte)0xC7, (byte)0x05, (byte)0xC5, (byte)0xC4, (byte)0x04, (byte)0xCC, (byte)0x0C, (byte)0x0D, (byte)0xCD,
                (byte)0x0F, (byte)0xCF, (byte)0xCE, (byte)0x0E, (byte)0x0A, (byte)0xCA, (byte)0xCB, (byte)0x0B, (byte)0xC9, (byte)0x09,
                (byte)0x08, (byte)0xC8, (byte)0xD8, (byte)0x18, (byte)0x19, (byte)0xD9, (byte)0x1B, (byte)0xDB, (byte)0xDA, (byte)0x1A,
                (byte)0x1E, (byte)0xDE, (byte)0xDF, (byte)0x1F, (byte)0xDD, (byte)0x1D, (byte)0x1C, (byte)0xDC, (byte)0x14, (byte)0xD4,
                (byte)0xD5, (byte)0x15, (byte)0xD7, (byte)0x17, (byte)0x16, (byte)0xD6, (byte)0xD2, (byte)0x12, (byte)0x13, (byte)0xD3,
                (byte)0x11, (byte)0xD1, (byte)0xD0, (byte)0x10, (byte)0xF0, (byte)0x30, (byte)0x31, (byte)0xF1, (byte)0x33, (byte)0xF3,
                (byte)0xF2, (byte)0x32, (byte)0x36, (byte)0xF6, (byte)0xF7, (byte)0x37, (byte)0xF5, (byte)0x35, (byte)0x34, (byte)0xF4,
                (byte)0x3C, (byte)0xFC, (byte)0xFD, (byte)0x3D, (byte)0xFF, (byte)0x3F, (byte)0x3E, (byte)0xFE, (byte)0xFA, (byte)0x3A,
                (byte)0x3B, (byte)0xFB, (byte)0x39, (byte)0xF9, (byte)0xF8, (byte)0x38, (byte)0x28, (byte)0xE8, (byte)0xE9, (byte)0x29,

                (byte)0xEB, (byte)0x2B, (byte)0x2A, (byte)0xEA, (byte)0xEE, (byte)0x2E, (byte)0x2F, (byte)0xEF, (byte)0x2D, (byte)0xED,
                (byte)0xEC, (byte)0x2C, (byte)0xE4, (byte)0x24, (byte)0x25, (byte)0xE5, (byte)0x27, (byte)0xE7, (byte)0xE6, (byte)0x26,
                (byte)0x22, (byte)0xE2, (byte)0xE3, (byte)0x23, (byte)0xE1, (byte)0x21, (byte)0x20, (byte)0xE0, (byte)0xA0, (byte)0x60,
                (byte)0x61, (byte)0xA1, (byte)0x63, (byte)0xA3, (byte)0xA2, (byte)0x62, (byte)0x66, (byte)0xA6, (byte)0xA7, (byte)0x67,
                (byte)0xA5, (byte)0x65, (byte)0x64, (byte)0xA4, (byte)0x6C, (byte)0xAC, (byte)0xAD, (byte)0x6D, (byte)0xAF, (byte)0x6F,
                (byte)0x6E, (byte)0xAE, (byte)0xAA, (byte)0x6A, (byte)0x6B, (byte)0xAB, (byte)0x69, (byte)0xA9, (byte)0xA8, (byte)0x68,
                (byte)0x78, (byte)0xB8, (byte)0xB9, (byte)0x79, (byte)0xBB, (byte)0x7B, (byte)0x7A, (byte)0xBA, (byte)0xBE, (byte)0x7E,
                (byte)0x7F, (byte)0xBF, (byte)0x7D, (byte)0xBD, (byte)0xBC, (byte)0x7C, (byte)0xB4, (byte)0x74, (byte)0x75, (byte)0xB5,
                (byte)0x77, (byte)0xB7, (byte)0xB6, (byte)0x76, (byte)0x72, (byte)0xB2, (byte)0xB3, (byte)0x73, (byte)0xB1, (byte)0x71,
                (byte)0x70, (byte)0xB0, (byte)0x50, (byte)0x90, (byte)0x91, (byte)0x51, (byte)0x93, (byte)0x53, (byte)0x52, (byte)0x92,

                (byte)0x96, (byte)0x56, (byte)0x57, (byte)0x97, (byte)0x55, (byte)0x95, (byte)0x94, (byte)0x54, (byte)0x9C, (byte)0x5C,
                (byte)0x5D, (byte)0x9D, (byte)0x5F, (byte)0x9F, (byte)0x9E, (byte)0x5E, (byte)0x5A, (byte)0x9A, (byte)0x9B, (byte)0x5B,
                (byte)0x99, (byte)0x59, (byte)0x58, (byte)0x98, (byte)0x88, (byte)0x48, (byte)0x49, (byte)0x89, (byte)0x4B, (byte)0x8B,
                (byte)0x8A, (byte)0x4A, (byte)0x4E, (byte)0x8E, (byte)0x8F, (byte)0x4F, (byte)0x8D, (byte)0x4D, (byte)0x4C, (byte)0x8C,
                (byte)0x44, (byte)0x84, (byte)0x85, (byte)0x45, (byte)0x87, (byte)0x47, (byte)0x46, (byte)0x86, (byte)0x82, (byte)0x42,

                (byte)0x43, (byte)0x83, (byte)0x41, (byte)0x81, (byte)0x80, (byte)0x40
        } ;
        int ucCRCHi = (preval & 0xff00) >> 8;
        int ucCRCLo = preval & 0x00ff;
        int iIndex;
        for (int i = 0; i < len; ++i) {
            iIndex = (ucCRCLo ^ data[offset + i]) & 0x00ff;
            ucCRCLo = ucCRCHi ^ crc16_tab_h[iIndex];
            ucCRCHi = crc16_tab_l[iIndex];
        }
        return ((ucCRCHi & 0x00ff) << 8) | (ucCRCLo & 0x00ff) & 0xffff;
    }
}
