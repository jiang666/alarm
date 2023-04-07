package com.example.alarm.bean;

import java.util.List;

class TestJsonBean {

    /**
     * status : 45001
     * msg : 操作成功
     * data : {"config":{"beforeExamine":10,"endExamine":10,"startSign":10,"endSign":10,"noOption":10,"examinationNotice":"考场通知，全程戴口罩，自己写自己的别作弊"},"detailVoList":[{"courseNo":"202301568Md","courseName":"历史","startTime":1675330359872,"endTime":1675330359872,"lastUpdateTime":1675330359872,"stuList":[{"stuNo":"2022090812","name":"ellen.west","nfcId":"10","faceImageMd5":"h9zm7h"}],"invigilatorVoList":[{"name":"ellen.west","nfcId":"10","empNo":"zud882","faceImageMd5":"h9zm7h"}]}]}
     */

    private int status;
    private String msg;
    /**
     * config : {"beforeExamine":10,"endExamine":10,"startSign":10,"endSign":10,"noOption":10,"examinationNotice":"考场通知，全程戴口罩，自己写自己的别作弊"}
     * detailVoList : [{"courseNo":"202301568Md","courseName":"历史","startTime":1675330359872,"endTime":1675330359872,"lastUpdateTime":1675330359872,"stuList":[{"stuNo":"2022090812","name":"ellen.west","nfcId":"10","faceImageMd5":"h9zm7h"}],"invigilatorVoList":[{"name":"ellen.west","nfcId":"10","empNo":"zud882","faceImageMd5":"h9zm7h"}]}]
     */

    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * beforeExamine : 10
         * endExamine : 10
         * startSign : 10
         * endSign : 10
         * noOption : 10
         * examinationNotice : 考场通知，全程戴口罩，自己写自己的别作弊
         */

        private ConfigBean config;
        /**
         * courseNo : 202301568Md
         * courseName : 历史
         * startTime : 1675330359872
         * endTime : 1675330359872
         * lastUpdateTime : 1675330359872
         * stuList : [{"stuNo":"2022090812","name":"ellen.west","nfcId":"10","faceImageMd5":"h9zm7h"}]
         * invigilatorVoList : [{"name":"ellen.west","nfcId":"10","empNo":"zud882","faceImageMd5":"h9zm7h"}]
         */

        private List<DetailVoListBean> detailVoList;

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public List<DetailVoListBean> getDetailVoList() {
            return detailVoList;
        }

        public void setDetailVoList(List<DetailVoListBean> detailVoList) {
            this.detailVoList = detailVoList;
        }

        public static class ConfigBean {
            private int beforeExamine;
            private int endExamine;
            private int startSign;
            private int endSign;
            private int noOption;
            private String examinationNotice;

            public int getBeforeExamine() {
                return beforeExamine;
            }

            public void setBeforeExamine(int beforeExamine) {
                this.beforeExamine = beforeExamine;
            }

            public int getEndExamine() {
                return endExamine;
            }

            public void setEndExamine(int endExamine) {
                this.endExamine = endExamine;
            }

            public int getStartSign() {
                return startSign;
            }

            public void setStartSign(int startSign) {
                this.startSign = startSign;
            }

            public int getEndSign() {
                return endSign;
            }

            public void setEndSign(int endSign) {
                this.endSign = endSign;
            }

            public int getNoOption() {
                return noOption;
            }

            public void setNoOption(int noOption) {
                this.noOption = noOption;
            }

            public String getExaminationNotice() {
                return examinationNotice;
            }

            public void setExaminationNotice(String examinationNotice) {
                this.examinationNotice = examinationNotice;
            }
        }

        public static class DetailVoListBean {
            private String courseNo;
            private String courseName;
            private long startTime;
            private long endTime;
            private long lastUpdateTime;
            /**
             * stuNo : 2022090812
             * name : ellen.west
             * nfcId : 10
             * faceImageMd5 : h9zm7h
             */

            private List<StuListBean> stuList;
            /**
             * name : ellen.west
             * nfcId : 10
             * empNo : zud882
             * faceImageMd5 : h9zm7h
             */

            private List<InvigilatorVoListBean> invigilatorVoList;

            public String getCourseNo() {
                return courseNo;
            }

            public void setCourseNo(String courseNo) {
                this.courseNo = courseNo;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public long getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(long lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            public List<StuListBean> getStuList() {
                return stuList;
            }

            public void setStuList(List<StuListBean> stuList) {
                this.stuList = stuList;
            }

            public List<InvigilatorVoListBean> getInvigilatorVoList() {
                return invigilatorVoList;
            }

            public void setInvigilatorVoList(List<InvigilatorVoListBean> invigilatorVoList) {
                this.invigilatorVoList = invigilatorVoList;
            }

            public static class StuListBean {
                private String stuNo;
                private String name;
                private String nfcId;
                private String faceImageMd5;

                public String getStuNo() {
                    return stuNo;
                }

                public void setStuNo(String stuNo) {
                    this.stuNo = stuNo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNfcId() {
                    return nfcId;
                }

                public void setNfcId(String nfcId) {
                    this.nfcId = nfcId;
                }

                public String getFaceImageMd5() {
                    return faceImageMd5;
                }

                public void setFaceImageMd5(String faceImageMd5) {
                    this.faceImageMd5 = faceImageMd5;
                }
            }

            public static class InvigilatorVoListBean {
                private String name;
                private String nfcId;
                private String empNo;
                private String faceImageMd5;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNfcId() {
                    return nfcId;
                }

                public void setNfcId(String nfcId) {
                    this.nfcId = nfcId;
                }

                public String getEmpNo() {
                    return empNo;
                }

                public void setEmpNo(String empNo) {
                    this.empNo = empNo;
                }

                public String getFaceImageMd5() {
                    return faceImageMd5;
                }

                public void setFaceImageMd5(String faceImageMd5) {
                    this.faceImageMd5 = faceImageMd5;
                }
            }
        }
    }
}
