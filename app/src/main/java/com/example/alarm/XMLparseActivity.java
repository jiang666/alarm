package com.example.alarm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.example.alarm.utils.DomUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.alarm.utils.FileUtils.readFile;
import static com.example.alarm.utils.FileUtils.writeFile;

/**
 * xml解析
 * textview 滚动文字
 * 权限申请
 * https://github.com/getActivity/XXPermissions
 */

public class XMLparseActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    @BindView(R.id.main_edit)
    TextView mainEdit;
    @BindView(R.id.main_button)
    TextView mainButton;
    private ArrayList<Object> testBeans;
    private HashMap<String, String> mapBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainEdit.setMovementMethod(ScrollingMovementMethod.getInstance());
        testBeans = new ArrayList<>();
        //
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPerssion();
            }
        });
        mapBeans = new HashMap<String, String>();
        try {
            creatXML();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        readXML();
    }
    /**
     * @des 权限组的获取
     */
    private void openPerssion() {
        verifyStoragePermissions(XMLparseActivity.this);
    }
    /**
     * @des 权限组的获取
     * @author DELL
     * @time 10:44
     */
    public void verifyStoragePermissions(Activity activity) {

        try {
//检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.e("====", "true");
// 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);

            }else {
                Log.e("====", "false");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    private void readXML() {
        String requestStr = readFile("sdcard/touch/aaa.txt");
        inflate(requestStr);
        Document document = DomUtils.parseXmlString(requestStr);
        NodeList programNodeList = document.getElementsByTagName("PARAM");
        NamedNodeMap map = programNodeList.item(0).getAttributes();
        Node idNode = map.getNamedItem("NAME");
        Log.e("====0", requestStr + "  " + programNodeList.getLength() + "  "
                + idNode.getNodeValue()
                + "  " + programNodeList.item(0).getNodeName()
                + "  " + programNodeList.item(0).getAttributes().getLength()
                + "  " + programNodeList.item(0).getAttributes().item(0).getNodeName() + "  "
                + programNodeList.item(0).getAttributes().item(0).getNodeValue()
                + "  " + programNodeList.item(0).getTextContent());

        NodeList programNodeList1 = document.getElementsByTagName("REQUEST");
        Log.e("====1", programNodeList1.getLength() + "  "
                + programNodeList1.item(0).getNodeName()
                + "  " + programNodeList1.item(0).getChildNodes().item(0).getTextContent());

        NodeList programNodeList2 = document.getElementsByTagName("VERSION");
        Log.e("====2", requestStr + "  " + programNodeList2.getLength() + "  "
                + programNodeList2.item(0).getNodeName()
                + "  " + programNodeList2.item(0).getTextContent());
        //读取bean
        String path = "sdcard/touch/bbb.txt";
        TestBean testBean = new Gson().fromJson(readFile(path), TestBean.class);
        Log.e("==date time==", testBean.getDatetime());
        //读取list
        Type listType = new TypeToken<List<TestBean>>() {
        }.getType();
        String listpath = "sdcard/touch/ccc.txt";
        String timetableList = readFile(listpath);

        try {
            List<TestBean> list = new Gson().fromJson(timetableList, listType);
            Log.e("==date time1==", list.get(0).getDatetime());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        //读取map
        String mappath = "sdcard/touch/ddd.txt";
        String jsonContent = readFile(mappath);
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map2 = new Gson().fromJson(jsonContent, type);
        Log.e("==date time1==", map2.get("111"));
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
                    Log.e("=====30", "name " + parse.getName());//节点名称);
                    AttributeSet attrs = Xml.asAttributeSet(parse);
                    for (int i = 0; i < attrs.getAttributeCount(); i++) {
                        Log.e("=====300", "attrs " + attrs.getAttributeValue("", "NAME") + "  "
                                + attrs.getAttributeValue(0));
                    }
                    break;
                case XmlPullParser.TEXT:
                    Log.e("=====4", "text " + parse.getText());
                    data.peek().append(parse.getText());
                    break;
                case XmlPullParser.END_TAG:
                    Log.e("=====31", "name " + parse.getName());//节点名称);
                    data.pop();

            }
            evt = parse.next();
        }
        return root;
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
        paramElement.setAttribute("NAME", "CHANNELNO");
        paramElement.setTextContent("channelNo");
        //第二个PARAM节点添加属性和值
        param2Element.setAttribute("NAME", "CHECKCODE");
        param2Element.setTextContent("0000000");

        versionElement.setTextContent("0.222");
        //REQUEST节点添加两个PARAM节点，并按顺序添加
        requestElement.appendChild(paramElement);
        requestElement.appendChild(param2Element);

        requestElement.appendChild(versionElement);

        requestElement.insertBefore(paramElement, param2Element);

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
        transformer.transform(new DOMSource(document), new StreamResult(bos));

        //将输出流转成String
        final String requestStr = bos.toString();
        writeFile("sdcard/touch/aaa.txt", requestStr, false);
        Log.e("=======", requestStr);
        TestBean testBean = new TestBean();
        testBean.setDatetime("000000000");
        writeFile("sdcard/touch/bbb.txt", new Gson().toJson(testBean), false);
        testBeans.add(testBean);
        writeFile("sdcard/touch/ccc.txt", new Gson().toJson(testBeans), false);
        mapBeans.put("000", "000000");
        mapBeans.put("111", "111111");
        writeFile("sdcard/touch/ddd.txt", new Gson().toJson(mapBeans), false);
    }
}
