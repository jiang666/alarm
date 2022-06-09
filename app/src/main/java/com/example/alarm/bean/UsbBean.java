package com.example.alarm.bean;

import java.util.List;

/**
 * fqc,文件总对象
 */

public class UsbBean {
    private List<SingleTestBean> usb;
    private int index;


    public UsbBean(List<SingleTestBean> usb, int index) {
        this.usb = usb;
        this.index = index;
    }

    public List<SingleTestBean> getUsb() {
        return usb;
    }

    public void setUsb(List<SingleTestBean> usb) {
        this.usb = usb;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "UsbBean{" +
                "usb=" + usb +
                ", index=" + index +
                '}';
    }
}
