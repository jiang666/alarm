package com.example.alarm.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.alarm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wu on 2015/10/3.
 * ͼ�������ؼ�
 */
public class LockPatterView extends View {
    private boolean isInit;
    private float offsetX,offsetY;//X��Y�����ϵ�ƫ����
    private Bitmap bitmap_normal,bitmap_pressed,bitmap_error,bitmap_line,bitmap_line_error;
    private Point[][] points = new Point[3][3];
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);//��������
    private  float moveX,moveY;//�ƶ�������
    private List<Point>pointList=new ArrayList<Point>();
    private boolean isSelect,isFinis,moveNoPoint;
    public static final int POINT_SIZE=5;//���ٻ��Ƶ���
    public Matrix matrix=new Matrix();
    private OnPatterChangeLister onPatterChangeLister;

    public LockPatterView(Context context) {
     this(context, null);
    }

    public LockPatterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public LockPatterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInit){
            initPoints();
        }
        points2Canvas(canvas);
        //����
        if(pointList.size()>0){
            Point a=pointList.get(0);
            for(int i=1;i<pointList.size();i++){
                Point b=pointList.get(i);
                line2Canvas(canvas,a,b);
                a=b;
            }
            if(moveNoPoint){
                line2Canvas(canvas,a,new Point(moveX,moveY));
            }
        }
    }
    /**���㻭�ڻ�����*/
    private void points2Canvas(Canvas canvas) {
        //�������
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                Point point=points[i][j];
                if(point.state==Point.STATE_PRESS){
                    canvas.drawBitmap(bitmap_pressed,point.x-bitmap_pressed.getWidth()/2,point.y-bitmap_pressed.getWidth()/2,paint);
                }else if(point.state==Point.STATE_ERROR){
                    canvas.drawBitmap(bitmap_error,point.x-bitmap_error.getWidth()/2,point.y-bitmap_error.getWidth()/2,paint);
                }else{
                    canvas.drawBitmap(bitmap_normal,point.x-bitmap_normal.getWidth()/2,point.y-bitmap_normal.getWidth()/2,paint);
                }
            }
        }
    }
    /**
     * ����
     * @param canvas
     * @param a
     * @param b
     */
    public void line2Canvas(Canvas canvas,Point a,Point b){
        //�ߵĳ���
        float linelength = (float) Point.distance(a, b);
        float degress = getDegrees(a,b);
        canvas.rotate(degress, a.x, a.y);

        if (a.state == Point.STATE_PRESS) {
            matrix.setScale(linelength / bitmap_line.getWidth(), 1);
            matrix.postTranslate(a.x - bitmap_line.getWidth() / 2 , a.y - bitmap_line.getHeight() /2);
            canvas.drawBitmap(bitmap_line, matrix, paint);
        }else{
            matrix.setScale(linelength / bitmap_line.getWidth(), 1);
            matrix.postTranslate(a.x - bitmap_line.getWidth() / 2 , a.y - bitmap_line.getHeight() /2);
            canvas.drawBitmap(bitmap_line_error, matrix, paint);
        }
        canvas.rotate(-degress, a.x, a.y);
    }
    /**��ʼ����*/
    private void initPoints() {
        //1.�õ����ֵĿ��
        int width=getWidth();
        int height=getHeight();
        //2.���ݺ�������ֱ����X��Y�����ƫ����
        if(width>height){//����
            offsetY=0;
            offsetX=(width-height)/2;
            width=height;//���ò��ֿ�߶�һ��
        }else{//����
            offsetX=0;
            offsetY=(height-width)/2;
            height=width;
        }
        //ͼƬ��Դ
        bitmap_normal = BitmapFactory.decodeResource(getResources(), R.drawable.ic_point_normal);
        bitmap_pressed = BitmapFactory.decodeResource(getResources(), R.drawable.ic_point_press);
        bitmap_error = BitmapFactory.decodeResource(getResources(),  R.drawable.ic_point_error);
        bitmap_line = BitmapFactory.decodeResource(getResources(),  R.drawable.ic_line_normal);
        bitmap_line_error = BitmapFactory.decodeResource(getResources(),  R.drawable.ic_line_error);
        //�������
        for(int i=0;i<points.length;i++){
            for(int j=0;j<points[i].length;j++){
                points[i][j]=new Point(width/4+j*width/4+offsetX,height/4+height/4*i+offsetY);
            }
        }
        //��������
        int index = 1;
        for(int i=0;i<points.length;i++){
            for(int j=0;j<points[i].length;j++){
                Point point=points[i][j];
                point.index = index;
                index++;
            }
        }
        isInit=true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX=event.getX();
        moveY=event.getY();
        isFinis=false;
        Point point=null;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                resetPoint();
               point=chechSelectPoint();
                if(point!=null){
                    isSelect=true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isSelect){
                    point=chechSelectPoint();
                    if(point==null){
                        moveNoPoint=true;//�����ƶ�ʱ����
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isFinis=true;
                isSelect=false;
                moveNoPoint=false;
                break;
        }
        //ѡ���ظ����
        if(!isFinis&&isSelect&&point!=null){
            //�жϸõ��Ƿ��ǽ����
            if(crossPoint(point)){
                moveNoPoint=true;
            }else{//�µ�
                point.state=Point.STATE_PRESS;
                pointList.add(point);
            }

        }

        //���ƽ���
        if(isFinis){
            if(pointList.size()==1){
                resetPoint();
            }else if(pointList.size()<POINT_SIZE&&pointList.size()>0){
                errorPoint();
                onPatterChangeLister.onPatterChange(null);
            }else{
                if(onPatterChangeLister!=null){
                    String passwordStr="";
                    for(int i=0;i<pointList.size();i++){
                    passwordStr+=pointList.get(i).index;
                }
                    onPatterChangeLister.onPatterChange(passwordStr);
                }
            }
        }
        postInvalidate();
        return true;
    }
    // ��ȡ�Ƕ�
    public float getDegrees(Point pointA, Point pointB) {
        return (float) Math.toDegrees(Math.atan2(pointB.y - pointA.y, pointB.x - pointA.x));
    }
    /**
     * �����
     * @param point
     * @return �Ƿ񽻲�
     */
    private boolean crossPoint(Point point){
        if (pointList.contains(point)) {
            return true;
        }else{
            return false;
        }
    }
    /**
     * ���»���
     */
    public void resetPoint(){
        for (int i = 0; i < pointList.size(); i++) {
            Point point = pointList.get(i);
            point.state = Point.STATE_NORMAL;
        }
        pointList.clear();
    }
    /**����ĵ�*/
    public void errorPoint(){
        for(Point point:pointList){
            point.state=Point.STATE_ERROR;
        }
    }
    /**����Ƿ�ѡ��*/
private  Point chechSelectPoint(){
    for(int i=0;i<points.length;i++){
        for(int j=0;j<points[i].length;j++){
            Point  point=points[i][j];
            if(Point.with(point.x,point.y,bitmap_normal.getWidth()/2,moveX,moveY)){
                return point;
            }
        }

    }
    return null;
}

    /**
     * �Զ����
     * */
    public  static class Point{
        //����
        public static int STATE_NORMAL=0;
        //ѡ��
        public static int STATE_PRESS=1;
        //����
        public static int STATE_ERROR=2;
        public float x,y;
        public int index=0,state=0;
        public Point(float x,float y){
            this.x=x;
            this.y=y;
        }
        /**
         * �ж��ƶ����Ƿ��ڷ�Χ��
         * @param pointX
         * @param pointY
         * @param r
         * @param moveX
         * @param moveY
         * @return
         */
        public static boolean with(float pointX,float pointY,float r,float moveX,float moveY){
            return Math.sqrt((pointX-moveX)*(pointX-moveX)+(pointY-moveY)*(pointY-moveY))<r;
        }
        /**
         * ����֮��ľ���
         * @param a
         * @param b
         * @return
         */
        public static double distance(Point a,Point b){
            return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x) + Math.abs(a.y - b.y) * Math.abs(a.y - b.y)) ;
        }
    }

    /**
     * ͼ��������
     */
    public static interface OnPatterChangeLister{
        void onPatterChange(String passwordStr);

        void onPatterStart(boolean isStart);
    }
    /**
     * ����ͼ��������
     * @param changeLister
     */
    public void SetOnPatterChangeLister(OnPatterChangeLister changeLister){
        if (changeLister != null) {
            this.onPatterChangeLister = changeLister;
        }
    }

}
