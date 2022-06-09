package com.example.alarm.constant;

/**
 * 测试常量
 */

public class TestConstant {
    /**
     * 是否完成
     */
    public static final int FINISH = 1;
    public static final int UNFINISHED = 0;
    public static final int FAIL = -1;//测试未通过
    public static final int TESTING = 2;//正在测试
    /**
     * 非显示的子项是否完成
     */
    public static final String PARAM_FINISH = "1";
    public static final String PARAM_UNFINISHED = "0";
    public static final String PARAM_FAIL = "-1";//测试未通过
    /**
     * 是否必要
     */
    public static final int NECESSARY = 1;//必要
    public static final int UNNECESSARY = 0;//不必要
    /**
     * 下一项，上一项标志
     */
    public static final int NEXT_TESTING_TAG = 1;//下一项标志
    public static final int LAST_TESTING_TAG = 0;//上一项标志
    public static final int TESTING_TAG = 2;//正在测试标志
    /**
     * 文件名
     */
    public static final String START_HARDWARE_MONITOR = "start_hardware_monitor.json";
    public static final String START_HARDWARE_MONITOR_TAG = "start_hardware_monitor_tag";
    /**
     * 综合测试
     */
    public static final String SYNTHESIZE_WORK = "综合测试";
    public static final String INTERNAL_STORAGE_SIZE = "internal_storage_size";//内存大小2GB
    public static final String INTERNAL_STORAGE_TOTAL_SIZE = "internal_storage_total_size";//内置存储总容量4GB
    public static final String FIRMWARE_VERSION = "firmware_version";//固件版本号
    public static final String CURRENT_TIME = "current_time";//固件版本号

    /**
     * wifi测试
     */
    public static final String WIFI_WORK = "wifi测试";//wifi测试
    public static final String WIFI_ADDRESS = "wifi_address";//wifi地址
    public static final String WIFI_ACCOUNT = "wifi_account";//wifi账号
    public static final String WIFI_PASSWORD = "wifi_password";//wifi密码

    /**
     * 串口测试
     */
    public static final String SERIAL_PORT_WORK = "串口测试";//串口测试
    public static final String SERIAL_PORT_ONE = "1";
    public static final String SERIAL_PORT_FOUR = "4";
    public static final String SERIAL_FOUR = "ttyS4";
    public static final String SERIAL_THREE = "ttyS3";
    public static final String SERIAL_ONE = "ttyS1";
    /**
     * TF卡测试
     */
    public static final String TF_CARD_WORK = "tf卡测试";//tf卡测试
    public static final String TF_SIZE = "tf_size";//tf合格内存
    public static final String TF_AVAILABLE_SIZE = "tf_available_size";//tf合格内存
    public static final String TF_FILE = "tf_file";//tf文件读写
    public static final String TF_MOUNTED_TAG = "tf卡挂载测试";
    public static final String TF_SIZE_TAG = "tf卡大小测试";
    public static final String TF_FILE_TAG = "tf卡文件读写测试";
    /**
     * 刷卡测试
     */
    public static final String SLOT_CARD_WORK = "刷卡测试";
    public static final String FIRST_SLOT_CARD_INFO = "first_slot_card_info"; //刷卡信息 ttyS3 & 19200 & 0123456789
    public static final String SECOND_SLOT_CARD_INFO = "second_slot_card_info"; //刷卡信息 ttyS4 & 19200 & 0123456789

    /**
     * hdmi测试
     */
    public static final String HDMI_WORK = "hdmi测试";//hdmi测试
    public static final String HDMI_RESOLUTION = "分辨率";
    public static final String HDMI_RESOLUTION_TAG = "分辨率测试";
    public static final String HDMI_FIGURED_SCREEN = "花屏测试";
    public static final String HDMI_COLOR = "颜色测试";
    public static final String HDMI_PLAY_MUSIC = "声音测试";
    /**
     * lvds
     */
    public static final String LVDS_WORK = "lvds测试";//lvds测试
    public static final String LVDS_RESOLUTION = "分辨率";
    public static final String LVDS_RESOLUTION_TAG = "分辨率测试";
    public static final String LVDS_FIGURED_SCREEN = "花屏测试";
    public static final String LVDS_COLOR = "颜色测试";
    /**
     * I2C
     */
    public static final String LIGHT_WORK = "灯条测试";//灯条测试
    public static final String LIGHT_BUS = "i2c_bus";//I2C总线
    public static final String LIGHT_ADDRESS = "i2c_address";//I2C测试
    public static final String LIGHT_COLOR_ANIMATION = "i2c_color_animation";
    public static final String LIGHT_COLOR = "颜色变化";
    public static final String LIGHT_INIT_TAG = "灯条初始化";


    /**
     * 功放测试
     */
    //TODO 子项参数根据需求自定义
    public static final String SOUND_PLAY_WORK = "功放测试";//功放测试
    public static final String SOUND_VOLUME = "音量";
    public static final String SOUND_RECYCLER = "sound_recycler";
    public static final String SOUND_LEFT_CHANNEL = "左声道";
    public static final String SOUND_RIGHT_CHANNEL = "右声道";
    public static final String SOUND_DOUBLE_CHANNEL = "双声道";
    /**
     * 录音测试
     */
    public static final String SOUND_RECORDING_PLAY_VOLUME = "音量";//播放的音量
    public static final String SOUND_RECORDING_PLAY_VOLUME_R = "声音清晰度";
    public static final String SOUND_RECORDING_WORK = "录音测试";//录音测试

    /**
     * usb测试
     */
    public static final String USB_WORK = "USB测试";//usb测试
    public static final String USB_SIZE_WORK = "U盘数目";//
    /**
     * 继电器（gpio）测试
     */
    public static final String GPIO_PE3 = "PE3";//gpio编号
    public static final String GPIO_WORK = "GPIO测试";//GPIO测试
    public static final String GPIO_ONE_HIGHT_TIME = "gpio_one_hight_time";//gpio高电平时间
    public static final String GPIO_PE2 = "PE2";//gpio编号

    /**
     * Ethermet测试
     */
    public static final String ETHERNET_WORK = "以太网测试";
    public static final String ETHERNET_CONNECT_TAG = "连接网络";
    public static final String ETHERMET_ADDRESS = "ethermet_address";
    public static final String ETHERMET_NETTING_TWINE = "连接网线";

    /**
     * 摄像头测试
     */
    public static final String CAMERA_WORK = "摄像头测试";//摄像机测试
    public static final String CAMERA_DELAY = "camera_delay";//摄像头延时拍摄的时间
    public static final String CAMERA_PX = "拍照";//照片的像素
    public static final String CAMERA_FACING_TAG = "camera_facing_tag";//摄像头前后置标识
    /**
     * 触摸测试
     */
    public static final String TOUCH_WORK = "触摸测试";//触摸测试
    public static final String TOUCH_POINT = "点位测试";
    public static final String TOUCH_ACCURACY = "精度测试";
    public static final String TOUCH_LINE = "划线测试";


    public static final String RESULT_WORK = "测试结果";//灯条测试


}
