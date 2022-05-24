# alarm
知识点

FragmentActivity.java
   切换滑动
   viewPager.setCurrentItem(DATA_FRAGMENT);
   切换无动画
   viewPager.setCurrentItem(DATA_FRAGMENT,false);


version 1.1
添加字体和RecycleView S形显示。
---------------------------------------------------------------------------------------------------------------
时间戳转换
 SimpleDateFormat format =  new SimpleDateFormat("HH:mm:ss");
        String time="11:45:55";
        SimpleDateFormat YMDformat =  new SimpleDateFormat("yyyy:mm:dd");
        String YMDtime="2021:10:10";
        try {
            Date date = format.parse(time);
            Calendar calendar = dateToCalendar(date);
            Date ymddate = YMDformat.parse(YMDtime);
            Calendar ymdcalendar = dateToCalendar(ymddate);
            Calendar operationC = Calendar.getInstance();
            Log.e(TAG,"Format To times:"+date.getTime() + "  " + calendar.toString() + "\n"
                    + ymddate.getTime() + "  " + ymdcalendar.toString() + "\n"
            + operationC.toString());
        }catch (ParseException parseException){

        }

            public static Calendar dateToCalendar(Date date) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                return c;
            }
        /*Format To times:13555000  java.util.GregorianCalendar[time=13555000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1970,MONTH=0,WEEK_OF_YEAR=1,WEEK_OF_MONTH=1,DAY_OF_MONTH=1,DAY_OF_YEAR=1,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=11,HOUR_OF_DAY=11,MINUTE=45,SECOND=55,MILLISECOND=0,ZONE_OFFSET=28800000,DST_OFFSET=0]
        1610208600000  java.util.GregorianCalendar[time=1610208600000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=0,WEEK_OF_YEAR=3,WEEK_OF_MONTH=3,DAY_OF_MONTH=10,DAY_OF_YEAR=10,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=2,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=10,SECOND=0,MILLISECOND=0,ZONE_OFFSET=28800000,DST_OFFSET=0]
        java.util.GregorianCalendar[time=1639110378257,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=11,WEEK_OF_YEAR=50,WEEK_OF_MONTH=2,DAY_OF_MONTH=10,DAY_OF_YEAR=344,DAY_OF_WEEK=6,DAY_OF_WEEK_IN_MONTH=2,AM_PM=1,HOUR=0,HOUR_OF_DAY=12,MINUTE=26,SECOND=18,MILLISECOND=257,ZONE_OFFSET=28800000,DST_OFFSET=0]*/
        ------------------------------------------------------------------------------------------------------------

version2.2
添加字体和RecycleView点击item特殊显示
//点击条目变颜色
/*testAdapter.setOnItem(position);
tvShow.setText(list.get(position));
testAdapter.notifyDataSetChanged();

随机数
Random rand = new Random();
Log.e("=======","mainEdit click " + rand.nextInt(10));

ivArrow.measure(0, 0);
int textWidth = ivArrow.getMeasuredWidth();
Log.e("======"," textWidth " + textWidth);


DataqueryFragment 添加日历

XMLparseActivity 添加权限申请、文字可以滑动（setMovementMethod(ScrollingMovementMethod.getInstance());）
NestedScrollView + TextView
添加EventBus 使用 EvenbusActivity


CameraActivity  添加图片上覆盖文字

2022-04-28
底部按钮切换动画

2022-05-10 添加串口读取
SerialPortActivity

2022-05-20
ButtomTapAnimActivity  添加广播接收

XMLparseActivity 添加权限申请
