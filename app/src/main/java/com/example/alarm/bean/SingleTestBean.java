package com.example.alarm.bean;


import java.util.Map;

/**
 * fqc,单个测试项
 */

public class SingleTestBean {
    private String name;
    private Map<String, String> param;
    private int  necessity;//1为必要，0为不必要
    private int finish;//1为完成，0为未完成.,-1测试为通过


    public SingleTestBean(String name, Map<String, String> param, int necessity, int finish) {
        this.name = name;
        this.param = param;
        this.necessity = necessity;
        this.finish = finish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public int getNecessity() {
        return necessity;
    }

    public void setNecessity(int necessity) {
        this.necessity = necessity;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        return "SingleTestBean{" +
                "name='" + name + '\'' +
                ", param=" + param +
                ", necessity=" + necessity +
                ", finish=" + finish +
                '}';
    }
}
