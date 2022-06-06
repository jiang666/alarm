package com.example.alarm;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

/**
 * 蓝牙服务端 接收数据（桌牌）
 */
public class BluetoothServerActivity extends AppCompatActivity {
    private static String TAG = BluetoothServerActivity.class.getSimpleName();
    private Button button1,button2;
    private TextView textView1;

    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_server);
        mHandler = new Handler();
        initView();
    }

    // use
    private BluetoothSocket socket = null;

    private class AcceptThread extends Thread {
        private final String UUID_STRING = "0000FFE0-0000-1000-8000-00805F9B34FB";
        private final UUID UUID_BR = UUID.fromString(UUID_STRING);
        private BluetoothServerSocket server = null;
        private int state = 3;// 3：失败或取消；1：listen完成，accept失败
        private boolean isSecure;

        public AcceptThread(boolean isSecure) {
            this.isSecure = isSecure;
        }

        @Override
        public void run() {
            Log.d(TAG, "BEGIN ACCEPT...");

            try {
                if (isSecure) {
                    server = bluetoothAdapter.listenUsingRfcommWithServiceRecord("secure-server", UUID_BR);
                } else {
                    server = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("secure-server", UUID_BR);
                }
                Log.d(TAG, "Server run...");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (server == null) {
                Log.e(TAG, "Server is null.");
            }

            state = 1;

            while (state != 3) {
                textView1.setText("SERVER RUN");
                try {
                    socket = server.accept();
                    Log.d(TAG, "Server accept.");
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                //textView1.setText("SERVER ACCEPT");
            }

            if (socket == null) {
                textView1.setText("SERVER ACCEPT FAILED");
            } else {
                textView1.setText("SERVER ACCEPT SUCCESS");
            }
        }

        public void cancel() {
            Log.e(TAG, "Connect cancel.");
            state = 3;
            if (server != null) {
                try {
                    server.close();
                    textView1.setText("Server cancel.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server = null;
            }
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
                        break;
                    }
                    byte[] buffer = new byte[512];
                    // 读取输入流
                    int bytes = inputStream.read(buffer);
                    byte[] finalResult = Arrays.copyOfRange(buffer,0,bytes);
                    Log.d(TAG, "Read end." + new String(finalResult));
                    textView1.setText("Read end." + new String(finalResult));
                    //Toast.makeText(BluetoothServerActivity.this,"Read end." + new String(finalResult),Toast.LENGTH_LONG).show();
                    //Log.d(TAG, "read length : " + readLength );
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
                AcceptThread acceptThread = new AcceptThread(false);
                acceptThread.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketThread socketThread = new SocketThread(socket);
                socketThread.start();
            }
        });
    }
}


