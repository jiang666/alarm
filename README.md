# alarm
知识点

FragmentActivity.java
   切换滑动
   viewPager.setCurrentItem(DATA_FRAGMENT);
   切换无动画
   viewPager.setCurrentItem(DATA_FRAGMENT,false);


version 1.1
添加字体和RecycleView S形显示。

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