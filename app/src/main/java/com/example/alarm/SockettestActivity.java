package com.example.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import kotlin.jvm.internal.PropertyReference0Impl;

public class SockettestActivity extends Activity {
    /** Called when the activity is first created. */
	//IntelliJ IDEA 2021.3.1 (Community Edition
	//E:\kotlin\demo\src\main\java
	private InetAddress serverAddr; 
	 private Socket socket;
	 private String line;
	 
	 private static DataOutputStream   dos   =   null;
	 private static DataInputStream   dis   =   null;

	 private static Button button1;
	 private static EditText edittext1;
	 private Handler mHandler = new Handler();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sockettest);
        button1 = (Button) findViewById(R.id.button1);
        edittext1=(EditText)findViewById(R.id.et1);
		edittext1.setText("连接中*****");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//sock = new Socket(InetAddress.getByName("192.168.1.186"),8899);

					serverAddr = InetAddress.getByName("192.168.0.96");
					socket = new Socket(serverAddr, 50069);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							edittext1.setText("连接成功");
						}
					});

					receive();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

         //发送数据
        button1.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
					if (socket != null && socket.isConnected()) {
						//byte[] dyopen = {(byte)0x7b, (byte)0x01, (byte)0x01, (byte)0x22, (byte)0x33,(byte)0x22,(byte)0x36,(byte)0x7d};
					 new Thread(new Runnable() {
						 @Override
						 public void run() {
							 try {
								 dos =new  DataOutputStream(socket.getOutputStream());
								 dos.write("hello world".getBytes("UTF-8"));
								 Log.e("======","发送数据");
								 dos.close();//没有close 不发出值，服务端无接收打印
								 socket.shutdownOutput();
								 //Socket is closed  添加下面重连
								 socket = new Socket(serverAddr, 50069);
							 } catch (IOException e) {
								 e.printStackTrace();
							 }
						 }
					 }).start();
				   }
			}
		});     
    }
    
    //接收数据
    
   public void receive(){
			// TODO Auto-generated method stub
			new Thread() {
				@Override
				public void run() {

				try {
						if (socket != null && socket.isConnected()) {
						//String ss="";
						dis =new  DataInputStream(socket.getInputStream()); 	
						while(true){
						String ss="";
				        byte[] buffer = new byte[1024];	        
				        int length = dis.read(buffer);
				        String str = new String(buffer,0,buffer.length,"UFT-8");
				        Log.e("====",str);
				        for(int i=0;i<length;i++)
				        	{
				        		ss+=String.format("%02X", buffer[i]);			        		
				        	}
	                   System.out.println(ss);
				         handler.obtainMessage(0x11, ss).sendToTarget();			
				         }	
					
					   }	
						dis.close();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//handler.sendEmptyMessage(0);
				}
			}.start();
		}


    
    private Handler handler = new Handler() {
		@Override

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==0x11){
				String st =(String)msg.obj;

				edittext1.setText(st);
			}
		}
	};
  
}