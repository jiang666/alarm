package com.example.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Calendar;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static com.example.alarm.FileUtils.readFile;
import static com.example.alarm.FileUtils.writeFile;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private PendingIntent pi;
    private static String TAG = MainActivity.class.getSimpleName();
    private AlarmManager alarmManager;
    private EditText mNlpText;
    private StringBuffer mNlpText1;
    private Toast mToast;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int count = msg.what;
            Toast.makeText(MainActivity.this,"=======",Toast.LENGTH_LONG).show();
        }
    };

    private int mAIUIState = AIUIConstant.STATE_IDLE;
    private AIUIListener mAIUIListener = new AIUIListener() {

        @Override
        public void onEvent(AIUIEvent event) {
            switch (event.eventType) {
                case AIUIConstant.EVENT_WAKEUP:
                    Log.i( TAG,  "on event: "+ event.eventType );
                    showTip( "进入识别状态" );
                    //mNlpText1.append("fff");
                    break;

                case AIUIConstant.EVENT_RESULT: {
                    //解析结果
                    try {
                        JSONObject bizParamJson = new JSONObject(event.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject params = data.getJSONObject("params");
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);

                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));

                            mNlpText.append( "\n" );
                            mNlpText.append(cntJson.toString());

                            String sub = params.optString("sub");
                            if ("nlp".equals(sub)) {
                                // 解析得到语义结果
                                String resultStr = cntJson.optString("intent");
                                Log.e( TAG, resultStr );
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                        mNlpText.append( "\n" );
                        mNlpText.append( e.getLocalizedMessage() );
                    }

                    mNlpText.append( "\n" );
                } break;

                case AIUIConstant.EVENT_ERROR: {
                    Log.i( TAG,  "on event: "+ event.eventType );
                    mNlpText.append( "\n" );
                    mNlpText.append( "错误: "+event.arg1+"\n"+event.info );
                } break;

                case AIUIConstant.EVENT_VAD: {
                    if (AIUIConstant.VAD_BOS == event.arg1) {
                        showTip("找到vad_bos");
                    } else if (AIUIConstant.VAD_EOS == event.arg1) {
                        showTip("找到vad_eos");
                    } else {
                        showTip("" + event.arg2);
                    }
                } break;

                case AIUIConstant.EVENT_START_RECORD: {
                    Log.i( TAG,  "on event: "+ event.eventType );
                    showTip("开始录音");
                } break;

                case AIUIConstant.EVENT_STOP_RECORD: {
                    Log.i( TAG,  "on event: "+ event.eventType );
                    showTip("停止录音");
                } break;

                case AIUIConstant.EVENT_STATE: {    // 状态事件
                    mAIUIState = event.arg1;

                    if (AIUIConstant.STATE_IDLE == mAIUIState) {
                        // 闲置状态，AIUI未开启
                        showTip("STATE_IDLE");
                    } else if (AIUIConstant.STATE_READY == mAIUIState) {
                        // AIUI已就绪，等待唤醒
                        showTip("STATE_READY");
                    } else if (AIUIConstant.STATE_WORKING == mAIUIState) {
                        // AIUI工作中，可进行交互
                        showTip("STATE_WORKING");
                    }
                } break;

                case AIUIConstant.EVENT_CMD_RETURN:{
                    if( AIUIConstant.CMD_UPLOAD_LEXICON == event.arg1 ){
                        showTip( "上传"+ (0==event.arg2?"成功":"失败") );
                    }
                } break;

                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readXML();
        takeNotice();
        try {
            creatXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
                //handler.sendEmptyMessage(100);
            }
        }).start();
        /*Intent intent = new Intent(MainActivity.this,AlarmService.class);
        intent.putExtra("msg","你该打酱油了");
        PendingIntent pi = PendingIntent.getService(MainActivity.this,0,intent,0);
        Log.d("MyTag", "======");
//AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

//设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
// 5秒后通过PendingIntent pi对象发送广播
        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),10*1000,pi);*/
        // ①获取AlarmManager对象:
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
        Intent intent = new Intent(MainActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //设置当前时间
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                // 根据用户选择的时间来设置Calendar对象
                c.set(Calendar.HOUR, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                Log.e("=====time====",c.getTimeInMillis()+"");
                // ②设置AlarmManager在Calendar对应的时间启动Activity
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        c.getTimeInMillis(), pi);
                // 提示闹钟设置完毕:
                Toast.makeText(MainActivity.this, "闹钟设置完毕",
                        Toast.LENGTH_SHORT).show();
            }
        },currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
        /*mNlpText = (EditText) findViewById(R.id.main_edit);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5a98b254");
        //创建AIUIAgent
        AIUIAgent mAIUIAgent = AIUIAgent.createAgent(this,getAIUIParams(),mAIUIListener);

//发送`CMD_START`消息，使AIUI处于工作状态
        AIUIMessage startMsg = new AIUIMessage(AIUIConstant.CMD_START, 0, 0, null, null);
        mAIUIAgent.sendMessage( startMsg );
// 先发送唤醒消息，改变AIUI内部状态，只有唤醒状态才能接收语音输入
        if( AIUIConstant.STATE_WORKING !=   this.mAIUIState ){
            AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
            mAIUIAgent.sendMessage(wakeupMsg);
        }

// 打开AIUI内部录音机，开始录音
        String params = "sample_rate=16000,data_type=audio";
        AIUIMessage writeMsg = new AIUIMessage( AIUIConstant.CMD_START_RECORD, 0, 0, params, null );
        mAIUIAgent.sendMessage(writeMsg);*/
    }

    @SuppressLint("NewApi")
    private void takeNotice() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent push =new Intent(MainActivity.this, RecycleViewActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //Intent push =new Intent(MainActivity.this,JavaCallJSActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
        //本程序用来响应点击通知的打开应用,第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
        PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,0,push,0);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name ="bunnytouch";
//         用户可以看到的通知渠道的描述
        String description = "bunnytouch notification";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(false);
        mChannel.setVibrationPattern(new long[]{0});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("").setContentText("灯控服务开启")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(contentIntent)
                .build();
        mNotificationManager.notify(0,notification);
        //start(1, notification);
    }

    private void readXML() {
        String requestStr = readFile("sdcard/touch/aaa.txt");
        inflate(requestStr);
        Document document = DomUtils.parseXmlString(requestStr);
        NodeList programNodeList = document.getElementsByTagName("PARAM");
        NamedNodeMap map = programNodeList.item(0).getAttributes();
        Node idNode = map.getNamedItem("NAME");
        Log.e("====0",requestStr + "  "+ programNodeList.getLength()+"  "
                + idNode.getNodeValue()
                + "  " + programNodeList.item(0).getNodeName()
                + "  " + programNodeList.item(0).getAttributes().getLength()
                + "  " + programNodeList.item(0).getAttributes().item(0).getNodeName()+"  "
                +programNodeList.item(0).getAttributes().item(0).getNodeValue()
                +"  " +  programNodeList.item(0).getTextContent());

        NodeList programNodeList1 = document.getElementsByTagName("REQUEST");
        Log.e("====1", programNodeList1.getLength()+"  "
                + programNodeList1.item(0).getNodeName()
                +"  " +  programNodeList1.item(0).getChildNodes().item(0).getTextContent());

         NodeList programNodeList2 = document.getElementsByTagName("VERSION");
        Log.e("====2",requestStr + "  "+ programNodeList2.getLength()+"  "
                + programNodeList2.item(0).getNodeName()
                +"  " +  programNodeList2.item(0).getTextContent());
    }
    public View inflate(String layoutContent) {
        if (layoutContent == null)
            return null;
        XmlPullParser parse;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parse = factory.newPullParser();
            parse.setInput(new StringReader(layoutContent));
            return inflate(parse);
        } catch (XmlPullParserException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    public View inflate(XmlPullParser parse)
            throws XmlPullParserException, IOException {

        Stack<StringBuffer> data = new Stack<>();
        int evt = parse.getEventType();
        View root = null;

        while (evt != XmlPullParser.END_DOCUMENT) {
            switch (evt) {
                case XmlPullParser.START_DOCUMENT:
                    data.clear();
                    break;
                case XmlPullParser.START_TAG:
                    data.push(new StringBuffer());
                    Log.e("=====30","name " + parse.getName());//节点名称);
                    AttributeSet attrs = Xml.asAttributeSet(parse);
                    for (int i = 0; i < attrs.getAttributeCount(); i++) {
                        Log.e("=====300","attrs " + attrs.getAttributeValue("","NAME") + "  "
                                +attrs.getAttributeValue(0));
                    }
                    break;
                case XmlPullParser.TEXT:
                    Log.e("=====4","text " + parse.getText());
                    data.peek().append(parse.getText());
                    break;
                case XmlPullParser.END_TAG:
                    Log.e("=====31","name " + parse.getName());//节点名称);
                    data.pop();

            }
            evt = parse.next();
        }
        return root;
    }
    private String getAIUIParams() {
        String params = "";
        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream ins = assetManager.open( "cfg/aiui_phone.cfg" );
            byte[] buffer = new byte[ins.available()];

            ins.read(buffer);
            ins.close();

            params = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return params;
    }

    private void showTip(final String str) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }
    private void creatXML() throws ParserConfigurationException, TransformerException {
        //实例化DocumentBuilderFactory对象
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //实例化DocumentBuilder对象
        final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //实例化Document对象
        final Document document = documentBuilder.newDocument();
        //创建多个节点
        final Element rootElement = document.createElement("ROOT");
        final Element requestElement = document.createElement("REQUEST");
        final Element paramElement = document.createElement("PARAM");
        final Element param2Element = document.createElement("PARAM");
        final Element baseElement = document.createElement("BASE");
        final Element appidElement = document.createElement("APPID");
        final Element versionElement = document.createElement("VERSION");
        final Element securityElement = document.createElement("SECURITY");

        //第一个PARAM节点添加属性和值
        paramElement.setAttribute("NAME","CHANNELNO");
        paramElement.setTextContent("channelNo");
        //第二个PARAM节点添加属性和值
        param2Element.setAttribute("NAME","CHECKCODE");
        param2Element.setTextContent("0000000");

        versionElement.setTextContent("0.222");
        //REQUEST节点添加两个PARAM节点，并按顺序添加
        requestElement.appendChild(paramElement);
        requestElement.appendChild(param2Element);

        requestElement.appendChild(versionElement);

        requestElement.insertBefore(paramElement,param2Element);

        //DOCUMENT生成格式
        document.appendChild(requestElement);

        //实例化TransformerFactory对象
        final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        //实例化Transformer对象
        final Transformer transformer = transformerFactory.newTransformer();

        //创建一个输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        //将DOCUMENT转换成输出流
        transformer.transform(new DOMSource(document),new StreamResult(bos));

        //将输出流转成String
        final String requestStr = bos.toString();
        writeFile("sdcard/touch/aaa.txt",requestStr,false);
        Log.e("=======",requestStr);
    }

}
