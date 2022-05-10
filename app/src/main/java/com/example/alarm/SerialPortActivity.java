package com.example.alarm;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import net.codingpark.serialport.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;


/**
 * 串口读取
 */
public class SerialPortActivity extends AppCompatActivity {
    private static String TAG = SerialPortActivity.class.getSimpleName();
    private SerialPort m_SerialPort = null;
    private ReadCardThread readCardThread;
    private IreadCardNumber ireadCardNumber;
    private static final int TYPE_NFC = 0;
    private static final int TYPE_RF = 1;
    private static final int TYPE_NFC_RF = 2;
    private static final int TYPE_RCRB = 3;
    private int read_type = 3;
    private boolean read_reversed = false;
    /**
     * 写入包 头字节, 长度: 1个字节
     */
    private static final byte ONE = 0x00;
    /**
     * 写入包 尾字节, 长度: 1个字节
     */
    private static final byte TWO = 0x01;
    /**
     * 写入包 尾字节, 长度: 1个字节
     */
    private static final byte THREE = 0x02;
    /**
     * 写入包 标识字节, 长度: 1个字节
     */
    private static final byte SIGN = (byte) 0xFD;

    private InputStream mInputStream = null;
    private OutputStream mOutputStream = null;
    private TextView serisl;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port);
        serisl = (TextView)findViewById(R.id.tv_read_serial);
        String tty2;
        if (Build.VERSION.SDK_INT >= 23) {
            tty2 = "/dev/ttysWK3"; //+ Integer.toString(SystemInfo.getSerial_port_nfc2(this)-1);
        }else {
            tty2 = "/dev/ttyS2"; //+ Integer.toString((SystemInfo.getSerial_port_nfc2(this))-1);
        }
        initSerialPort(tty2);
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
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,serial + " 串口开启失败");
        }
    }
    /**
     * 卡上报数据监控线程
     */
    public class ReadCardThread extends Thread {

        // 读卡号，存储位置
        private byte[] readNumber = new byte[10];
        private byte[] readNumberfor = new byte[readNumberforlength];
        static final int readNumberforlength = 13;
        int tempLenth = 0;
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
                int count = 0;
                switch (read_type) {
                    case TYPE_NFC:
                        try {
                            count = mInputStream.read(icreadNumberfor);
                            byte[] firstResult = Arrays.copyOfRange(icreadNumberfor, 0, count);
                            Log.e(TAG, byteArrayToHexStr(firstResult) + "  " + count);
                            //Log.e(TAG, "readcard result: "+new String(firstResult,"UTF-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (count != 7) {
                            break;
                        }
                        byte[] tempResult = Arrays.copyOfRange(icreadNumberfor, 2, count - 1);
                        String cardNum;
                        Log.e("00000", read_reversed + "  ");
                        if (read_reversed) {
                            cardNum = new BigInteger(1, tempResult).toString(10);
                        } else {
                            byte[] finalResult = new byte[tempResult.length];
                            for (int i = 0; i < tempResult.length; i++) {
                                finalResult[i] = tempResult[tempResult.length - i - 1];
                            }
                            cardNum = new BigInteger(1, finalResult).toString(10);
                        }

                        if (cardNum.length() < 10) {
                            StringBuilder stringBuilder = new StringBuilder("0000000000");
                            stringBuilder.replace(stringBuilder.length() - cardNum.length(), stringBuilder.length(), cardNum);
                            cardNum = stringBuilder.toString();
                        }
                        ireadCardNumber.readNumber(cardNum, 0);
                        break;
                    case TYPE_RF:
                        while (count < readNumberforlength) {
                            try {
                                tempLenth = mInputStream.read(readNumberfor, count, readNumberforlength - count);
                                count += tempLenth;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        ireadCardNumber.readNumber(byteArrayToHexStr(Arrays.copyOfRange(readNumberfor, 4, 12)), 1);
                        break;
                    case TYPE_NFC_RF:
                        try {
                            count = mInputStream.read(icreadNumberfor);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //Log.e("-----",byteArrayToHexStr(icreadNumberfor)+"  "+count);
                        if (count >= 13) {
                            byte[] finalResult1 = deleDetectionSign(Arrays.copyOfRange(icreadNumberfor, 1, count - 1));
                            byte[] tempResult22 = Arrays.copyOfRange(finalResult1, 5, finalResult1.length - 1);
                            byte aa = tempResult22[0];
                            //Log.e("----aa",aa+"" );
                            byte[] tempResult_nfcrf = Arrays.copyOfRange(finalResult1, 7, finalResult1.length - 1);
                            String cardNum_nfcrf;
                            if (read_reversed) {
                                cardNum_nfcrf = new BigInteger(1, tempResult_nfcrf).toString(10);
                            } else {
                                byte[] finalResult_nfcrf = new byte[tempResult_nfcrf.length];
                                for (int i = 0; i < tempResult_nfcrf.length; i++) {
                                    finalResult_nfcrf[i] = tempResult_nfcrf[tempResult_nfcrf.length - i - 1];
                                }
                                cardNum_nfcrf = new BigInteger(1, finalResult_nfcrf).toString(10);
                            }

                            if (cardNum_nfcrf.length() < 10) {
                                StringBuilder stringBuilder = new StringBuilder("0000000000");
                                stringBuilder.replace(stringBuilder.length() - cardNum_nfcrf.length(), stringBuilder.length(), cardNum_nfcrf);
                                cardNum_nfcrf = stringBuilder.toString();
                            }
                            if (aa == 0X0F || aa == 0X0E) {
                                Log.d("bb", "nfc");
                                ireadCardNumber.readNumber(cardNum_nfcrf, 0);
                            } else if (aa == 0X06 || aa == 0X07) {
                                Log.d("bb", "2.4G");
                                ireadCardNumber.readNumber(cardNum_nfcrf, 1);
                            }
                        }
                        break;
                    case TYPE_RCRB:
                        /*try {
                            count = is.read(icreadNumberfor);
                            while(count < 15 && icreadNumberfor[0] == (byte)0xFF){
                                int lenth = is.read(icreadNumberfor, count , icreadNumberfor.length - count);
                                count += lenth;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        try {
                            count = mInputStream.read(icreadNumberfor);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.e("--icreadNumber--", byteArrayToHexStr(Arrays.copyOfRange(icreadNumberfor, 0, count)) + "  " + count);
                        if (count > 13)
                            //Log.e("--icreadNumber--",byteArrayToHexStr(Arrays.copyOfRange(icreadNumberfor,0,count))+"  "+count);
                            if (count > 13) {
                                //FF0001D5D6CEDB1E420000870E8CEE
                                byte[] finalResult1 = Arrays.copyOfRange(icreadNumberfor, 3, 9);
                                byte[] tempResult22 = Arrays.copyOfRange(finalResult1, 0, 2);
                                byte a1 = tempResult22[0];
                                byte a2 = tempResult22[1];
                                //Log.e("a1",a1+" "+a2 );
                                //Log.e("--finalResult1--",byteArrayToHexStr(finalResult1));
                                //Log.e("--tempResult22--",byteArrayToHexStr(tempResult22));
                                byte[] tempResult_nfcrf = Arrays.copyOfRange(finalResult1, 2, finalResult1.length);
                                String cardNum_nfcrf;
                                if (read_reversed) {
                                    byte[] finalResult_nfcrf = new byte[tempResult_nfcrf.length];
                                    for (int i = 0; i < tempResult_nfcrf.length; i++) {
                                        finalResult_nfcrf[i] = tempResult_nfcrf[tempResult_nfcrf.length - i - 1];
                                    }
                                    cardNum_nfcrf = new BigInteger(1, finalResult_nfcrf).toString(10);
                                } else {
                                    cardNum_nfcrf = new BigInteger(1, tempResult_nfcrf).toString(10);
                                }
                                if (cardNum_nfcrf.length() < 10) {
                                    StringBuilder stringBuilder = new StringBuilder("0000000000");
                                    stringBuilder.replace(stringBuilder.length() - cardNum_nfcrf.length(), stringBuilder.length(), cardNum_nfcrf);
                                    cardNum_nfcrf = stringBuilder.toString();
                                }
                                if (a1 == (byte) 0xA5 && a2 == (byte) 0xA6) {
                                    Log.d("bb", "nfc");
                                    final String nfc = cardNum_nfcrf;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            serisl.setText(nfc);
                                        }
                                    });
                                    //ireadCardNumber.readNumber(cardNum_nfcrf, 0);
                                    Log.e("bb_nfc", cardNum_nfcrf);
                                } else if (a1 == (byte) 0xD5 && a2 == (byte) 0xD6) {
                                    //Log.d("bb","2.4G");
                                    //ireadCardNumber.readNumber(cardNum_nfcrf, 1);
                                    //Log.e("bb_2.4G",cardNum_nfcrf);
                                } else {
                                    Log.e("cardnum", cardNum_nfcrf);
                                }
                            }
                        break;
                }
            }
        }
    }
    //指令下发函数
    void send_order() {
        //AA 00 01 C3 3D 08 01 01 01 07 02 00 05 00 00 01 01 01 BE 8F EE
        byte[] data = {(byte) 0xAA, (byte) 0x00, (byte) 0x01, (byte) 0xC3, (byte) 0x3D, (byte) 0x08, (byte) 0x01, (byte) 0x01,
                (byte) 0x01, (byte) 0x07, (byte) 0x02, (byte) 0x00, (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x01,
                (byte) 0x01, (byte) 0x01, (byte) 0xBE, (byte) 0x8F, (byte) 0xEF};//
        try {
            mOutputStream.write(data);
            Log.e("提示", "===串口下发指令成功===");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("提示", "===串口下发指令失败===");
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
     *  删除标示位
     */
    private byte[] deleDetectionSign(byte[] bytes) {
        int byteLen = (bytes == null) ? 0 : bytes.length;
        int signSize = 0;
        for (int i = 0; i < byteLen - 2; i++) {
            if (bytes[i] == SIGN) {
                signSize += 1;
            }
        }
        //Logger.d("delesignSize" + signSize);
        if (signSize == 0) return bytes;
        int finalLong = byteLen - signSize;
        int finalCorner = 1;
        byte[] finalResult = new byte[finalLong];
        finalResult[0] = bytes[0];
        finalResult[finalLong - 1] = bytes[byteLen - 1];
        for (int i = 0; i < byteLen - 2; i++) {
            if (bytes[i] == SIGN) {
                switch (bytes[i + 1]) {
                    case ONE:
                        finalResult[finalCorner] = (byte) 0xA5;
                        finalCorner += 1;
                        break;
                    case TWO:
                        finalResult[finalCorner] = (byte) 0x5A;
                        finalCorner += 1;
                        break;
                    case THREE:
                        finalResult[finalCorner] = (byte) 0xFD;
                        finalCorner += 1;
                        break;
                }
            } else {
                finalResult[finalCorner] = bytes[i];
                finalCorner += 1;
            }
        }
        //Logger.d("delesignSizee" + finalCorner);
        return finalResult;
    }

    /**
     * 提供给外部的接口回调
     * @param ireadCardNumber
     */
    public void setIreadCardNumber(IreadCardNumber ireadCardNumber) {
        this.ireadCardNumber = ireadCardNumber;
    }

    public void ireadCardNumberClone() {
        this.ireadCardNumber = null;
    }

    public interface IreadCardNumber {
        public void readNumber(String readNumber, int typenum);
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
}
