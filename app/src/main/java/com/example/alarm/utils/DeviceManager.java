package com.example.alarm.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.alarm.bean.MergeViewBean;
import com.example.alarm.bean.ResearchEvent;

import org.greenrobot.eventbus.EventBus;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by rick on 15/6/30.
 */
public class DeviceManager {
    public static final int MAX_DEVICE_NUM = 16;
    private static DeviceManager instance;
    private static TreeMap<String, MergeViewBean> mdeviceTree = new TreeMap<String, MergeViewBean>();
    public final String TAG = getClass().getName();
    private String current_ip = "";

    private DeviceManager() {

    }

    public static DeviceManager getInstance() {
        if (instance == null) {
            synchronized (DeviceManager.class) {
                if (instance == null) {
                    instance = new DeviceManager();
                    for (int i = 0; i < 5; i++) {
                        MergeViewBean mergeViewBean = new MergeViewBean(""+i,"item"+i,"LSSDPNodes",0,false, Color.RED,null);
                        mdeviceTree.put(""+i,mergeViewBean);
                    }
                    MergeViewBean mergeViewBeans =  new MergeViewBean("0000","grop","LSSDPGroup",5,true, Color.RED,null);
                    List<MergeViewBean> mergeViewBeans1 = new ArrayList<>();
                    for (int i = 10; i < 15; i++) {
                        MergeViewBean mergeViewBean = new MergeViewBean(""+i,"item"+i,"LSSDPNodes",0,false, Color.RED,null);
                        mergeViewBeans1.add(mergeViewBean);
                    }
                    mergeViewBeans.setMergeViewBeans(mergeViewBeans1);
                    mdeviceTree.put("0000",mergeViewBeans);
                }
            }
        }
        return instance;
    }



    public List<MergeViewBean> getGroupsAndSpeakers() {
        List<MergeViewBean> nodes = new ArrayList<>();
        nodes.addAll(getGroups());
        nodes.addAll(getNoGroupSpeakers());
        return nodes;
    }


    public synchronized List<MergeViewBean> getAllSpeakers() {
        return new ArrayList<>(mdeviceTree.values());
    }

    public List<MergeViewBean> getGroups() {
        Map<String, MergeViewBean> groupmap = new HashMap<>();
        List<MergeViewBean> speakers = getAllSpeakers();
        List<MergeViewBean> speakersG = new ArrayList<>();
        // find groups
        for (int i = 0; i < speakers.size(); i++) {
            if (!(speakers.get(i).getType().equals("LSSDPNodes"))) {
                continue;
            }

            MergeViewBean speaker = (MergeViewBean) speakers.get(i);

            if (!speaker.isGroup()) {
                continue;
            }
            speakersG.add(speaker);
           /* String zoneId = speaker.getZoneID();
            if (groupmap.containsKey(zoneId)) {
                MergeViewBean group = (MergeViewBean) groupmap.get(zoneId);
                group.addNewSpeaker(speaker.getId());
            } else {
                MergeViewBean group = new MergeViewBean(zoneId);
                group.addNewSpeaker(speaker.getId());
                groupmap.put(zoneId, group);
            }*/
        }

        return new ArrayList<>(groupmap.values());
    }

    public List<MergeViewBean> getNoGroupSpeakers() {
        List<MergeViewBean> nogroupSpeakers = new ArrayList<>();
        List<MergeViewBean> speakers = getAllSpeakers();

        // find groups
        for (int i = 0; i < speakers.size(); i++) {
            if (!(speakers.get(i).getType().equals("LSSDPNodes"))) {
                nogroupSpeakers.add(speakers.get(i));
                continue;
            }

            MergeViewBean speaker = (MergeViewBean) speakers.get(i);
            if (!speaker.isGroup()) {
                nogroupSpeakers.add(speakers.get(i));
            }
        }

        return nogroupSpeakers;
    }




/*    public boolean checkSingleSoundSpaceAndRemove(LSSDPNodes node) {
        boolean result = true;

        if (getGroup(node.getZoneID()) != null) {
            int num = getGroup(node.getZoneID()).getSpeakerNum();
            *//*
            if (num == 1) {

                //remove group and release the only one left
                String onlyoneId = getGroup(node.getZoneID()).getSpeakers().get(0);
                //the left one not same as this update one
                if (onlyoneId.trim().equals(node.getKey().trim())) {
                    return result;
                }
                mSoundSpaceTree.remove(node.getZoneID());
                LSSDPNodes leftDevice = (LSSDPNodes) getDevice(onlyoneId);

                leftDevice.setZoneID("");
                leftDevice.setDeviceState("F");
                addNewDevicetoSoundSpace(leftDevice);
            }
            *//*
*//*            if(num == 0){
                mSoundSpaceTree.remove(node.getZoneID());
            }*//*
        }
        return result;
    }*/


    public MergeViewBean getDevice(String key) {

        if (key == null) {
            return null;
        }

        if (!mdeviceTree.containsKey(key)) {
            // GTLog.e(TAG, String.format("Device with key %s is not in collection", key));
            return null;
        }
        return mdeviceTree.get(key);
    }

    public MergeViewBean getDeviceBySN(String sn) {
        if (sn == null) {
            return null;
        }

        List<MergeViewBean> speakers = getAllSpeakers();
        for (int i = 0; i < speakers.size(); i++) {
            if (sn.equals(speakers.get(i).getSpeakerNum())) {
                return speakers.get(i);
            }
        }

        return null;
    }


    public TreeMap<String, MergeViewBean> getDeviceTree() {
        return mdeviceTree;
    }


    public TreeMap<String, MergeViewBean> getMdeviceTree() {
        return mdeviceTree;
    }


    public void joinGroup(MergeViewBean master, MergeViewBean slave) {
        if (master.isGroup()) {
            // add node to group
            MergeViewBean device_slave = (MergeViewBean) DeviceManager.getInstance().getDevice(slave.getId());
            mdeviceTree.remove(device_slave.getId());
            EventBus.getDefault().post(new ResearchEvent());
        } else {
        }

    }



}
