package com.example.alarm;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static com.example.alarm.utils.FileUtils.readFile;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private TextView mNlpText;
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
    //https://www.jianshu.com/p/c53ab167c7a1?from=groupmessage@
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

        mNlpText = (TextView) findViewById(R.id.main_edit);
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
        mAIUIAgent.sendMessage(writeMsg);
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

}
