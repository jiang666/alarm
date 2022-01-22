package com.example.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 蓝牙客户端 发送数据 (班牌)
 */


public class BluetoothClientActivity extends AppCompatActivity {
    private static final String TAG = "BluetoothClientActivity";

    private Button button1, button2;
    private TextView textView1;

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);
        initView();
    }

    // use
    private BluetoothSocket client;

    private class ConnectThread extends Thread {
        private final String UUID_STRING = "0000FFE0-0000-1000-8000-00805F9B34FB";
        private final UUID UUID_BR = UUID.fromString(UUID_STRING);

        private final boolean isSecure;

        public ConnectThread(BluetoothDevice device, boolean isSecure) {
            this.isSecure = isSecure;
            BluetoothSocket tmp = null;
            try {
                if (this.isSecure) {
                    tmp = device.createRfcommSocketToServiceRecord(UUID_BR);
                    Log.d(TAG, "isSecure:true");
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(UUID_BR);
                    Log.d(TAG, "isSecure:false");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            client = tmp;
        }

        @Override
        public void run() {
            Log.d(TAG, "BEGIN CONNECT...");
            if (client == null) {
                Log.e(TAG, "Connect failed.");
                return;
            }

            bluetoothAdapter.cancelDiscovery();

            try {
                client.connect();
                textView1.setText("Connect success.");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    client.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        }

        public void cancel() {
            Log.e(TAG, "Connect cancel.");
            if (client != null) {
                try {
                    client.close();
                    textView1.setText("Connect cancel.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getPort() {
            int port = -1;
            Class cls = client.getClass();
            try {
                Method getPortMethod = cls.getDeclaredMethod("getPort");
                getPortMethod.setAccessible(true);
                port = (int) getPortMethod.invoke(client);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return port;
        }

    }

    private class SocketThread extends Thread {
        byte[] bytes = new byte[1024];
        private BluetoothSocket socket;

        public SocketThread(BluetoothSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            int readLength;
            try {
                while (this.socket != null) {
                    InputStream inputStream = socket.getInputStream();
                    if (inputStream == null || (readLength = inputStream.read()) == -1) {
                        Log.d(TAG, "Read end.");
                        break;
                    }
                    Log.d(TAG, "read length : " + readLength);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean send(BluetoothSocket socket, byte[] data) {
        OutputStream outputStream = null;
        if (socket != null && data != null) {
            try {
                if ((outputStream = socket.getOutputStream()) != null) {
                    data = "3333333".getBytes();
                    outputStream.write(data);
                    outputStream.flush();
                    Log.d(TAG, "Send ok and length : " + data.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void initView() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        textView1 = findViewById(R.id.textView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothDevice remoteDevice = getBluetoothDevice();
                ConnectThread connectThread = new ConnectThread(remoteDevice, false);
                connectThread.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] data = new byte[]{1, 2, 3};
                send(client, data);
            }
        });
    }

    private BluetoothDevice getBluetoothDevice() {
        String remoteMac = "B0:EB:57:05:B0:8F";   //huawei "B0:EB:57:05:B0:8F"   realme 10:f6:05:65:25:64
        if (!BluetoothAdapter.checkBluetoothAddress(remoteMac)) {
            Log.e(TAG, "RemoteMac is invalid.");
            return null;
        }
        return bluetoothAdapter.getRemoteDevice(remoteMac);
    }
}


