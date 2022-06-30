package com.example.alarm.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alarm.R;
import com.example.alarm.bean.MergeViewBean;
import com.example.alarm.utils.ColorDrawableGenerator;
import com.example.alarm.utils.DeviceManager;
import com.example.alarm.utils.UIUtils;
import com.example.alarm.widget.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oreofish on 15/1/20.
 */
public class SoundSpaceRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final float FACTOR = 1.1f;
    private static final String TAG = SoundSpaceRecyclerAdapter.class.getName();
    public static int borderWidth;
    public final int ITEM_TYPE_GROUP = 0;
    public final int ITEM_TYPE_NODE = 1;
    public final int ITEM_TYPE_OLD = 2;
    public final int ITEM_TYPE_BUTTON = 3;
    private final LayoutInflater layoutInflater;
    private Context context;
    private RecyclerView recyclerView;
    private List<MergeViewBean> ssinfos;
    private boolean isJoining = false;
    private int scrollDistance = 0;
    private int grey;
    private int white;
    private int green;
    private int BTCALL_Color;

    public SoundSpaceRecyclerAdapter(Context context, List<MergeViewBean> items, RecyclerView recyclerView) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        ssinfos = items;
        grey = context.getResources().getColor(R.color.deeper_grey);
        white = context.getResources().getColor(R.color.white);
        green = context.getResources().getColor(R.color.irlearn_learned);
        borderWidth = 9; //UI.dp2px(3)
        BTCALL_Color = context.getResources().getColor(R.color.ring_old_color);
        this.recyclerView = recyclerView;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        final RecyclerView.ViewHolder holder;
        switch (viewType) {
            case ITEM_TYPE_GROUP:
                view = layoutInflater.inflate(R.layout.listitem_sound_space_group, parent, false);
                holder = new DeviceViewHolder(view);
                break;
            case ITEM_TYPE_OLD:
                view = layoutInflater.inflate(R.layout.listitem_sound_space_old, parent, false);
                holder = new DeviceViewHolder(view);
                break;
            case ITEM_TYPE_BUTTON:
                //boolean isVideoOpen = SystemConfigManager.getInstance().isVideoIntroducingOpen();
                view = layoutInflater.inflate(R.layout.listitem_sound_space_button, parent, false);
                RelativeLayout videobt = (RelativeLayout) view.findViewById(R.id.rl_soundspace);
                //if (!isVideoOpen) {
                    videobt.setVisibility(View.GONE);
                //}
                holder = new FootViewHolder(view);
                if (view != null) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Intent intent = new Intent();
                            intent.setClass(context, VideoIntroducingSoundspaceActivity.class);
                            intent.putExtra("videofrom", "soundspace");
                            // for result?
                            context.startActivity(intent);*/
                        }
                    });
                }
                return holder;
            case ITEM_TYPE_NODE:
            default:
                view = layoutInflater.inflate(R.layout.listitem_sound_space, parent, false);
                holder = new DeviceViewHolder(view);
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {
                        if (isJoining) {
                            return true;
                        }
                        final ClipData data = ClipData.newPlainText("ssinfo_id", "device");

                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", FACTOR);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", FACTOR);
                        AnimatorSet animSetXY = new AnimatorSet();
                        animSetXY.playTogether(scaleX, scaleY);
                        animSetXY.setDuration(200);
                        animSetXY.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                try {
                                    v.startDrag(data,  // the data to be dragged
                                            new MyDragShadowBuilder(v),  // the drag shadow builder
                                            v,
                                            0          // flags (not currently used, set to 0)
                                    );
                                    v.setScaleX(1f);
                                    v.setScaleY(1f);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        animSetXY.start();

                        return true;
                    }
                });

                break;
        }

        if (view != null) {
            view.setOnDragListener(new SoundSpaceOnDragListener());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if (viewholder instanceof FootViewHolder) {
            return;
        }
        final MergeViewBean device = ssinfos.get(position);
        DeviceViewHolder holder = (DeviceViewHolder) viewholder;
        holder.iv_logo.setTag(position);
        holder.device = device;
        if (device.getType().equals("LSSDPNodes")) {
            holder.tv_item.setText(device.getName().toUpperCase());
        } else if (device.getType().equals("LSSDPGroup")) {
            String name = device.getName().toUpperCase();
            int number = device.getSpeakerNum();
            holder.tv_item.setText(name);
            holder.tv_number.setText("& " + Integer.toString(number - 1));
        } else {
            holder.tv_item.setText(device.getName().toUpperCase());
        }

        // int color = device.getColor();
        int color = 0;
        switch (getItemViewType(position)) {
            case ITEM_TYPE_GROUP:
                //color = device.getColor();
                color = Color.BLACK;
                drawGroup(holder.rl_soundspace, device);
                setGroupRingColor(holder.rl_soundspace, color);
                /*if (device.getIsJoining()) {
                    holder.pb_waiting.setVisibility(View.VISIBLE);
                    holder.pb_waiting.spin();
                    isJoining = true;
                } else {*/
                    holder.pb_waiting.setVisibility(View.INVISIBLE);
                    holder.pb_waiting.stopSpinning();
                    isJoining = false;
                //}

                break;
            default:
                color = Color.RED;
                if ((int) holder.iv_logo.getTag() == position) {
                    //holder.iv_logo.setImageDrawable(device.getIcon());
                    holder.iv_logo.setImageDrawable(context.getResources().getDrawable(R.drawable.exclamationmark));
                }

                /*setRingColor(holder.rl_soundspace, color);*/
                break;
        }

        // set text color
        holder.tv_item.setTextColor(color);
        if (holder.tv_number != null) {
            holder.tv_number.setTextColor(color);
        }

        if (holder.tv_link != null) {
            holder.tv_link.setTextColor(color);
        }

        if (!device.isGroup()) {
            /*if (device.getModel().hasBattery()) {
                int batteryLevel = device.getBatteryLevelInt();
                int signalStrength = device.getSignalStrength();
                UpdateInfo info = device.getUpdateInfo();
                if (batteryLevel <= Constants.Device.BATTERY_LOW_POWER || signalStrength < Constants.Device.SIGNALSTRENGTH ||
                        (info != null && (info.isHasNewVersion() || info.getUpdateStatus() == FirmwareUpdateState.WAITING_FOR_UPDATE))) {
                    // GTLog.d(TAG, "battery=" + batteryLevel + " signal=" + signalStrength + " firmware=" + info.isHasNewVersion() + " firmware status=" + info.getUpdateStatus());
                    holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.exclamationmark));
                    holder.iv_status.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_status.setVisibility(View.GONE);
                }
            }

            boolean btcallstatus = device.getBtCallStatus(false);
            if (btcallstatus) {
                holder.iv_logo.setImageDrawable(ColorDrawableGenerator.coloredIcon(color, context.getResources().getDrawable(R.drawable.producticonincommingcall)));
                holder.iv_logo.setVisibility(View.VISIBLE);
                //TODO set ring color and set item can't click
                setRingColor(holder.rl_soundspace, BTCALL_Color);
                holder.view.setClickable(false);
                holder.view.setEnabled(false);
            } else {
                //TODO set ring color
                holder.iv_logo.setImageDrawable(device.getIcon());
                setRingColor(holder.rl_soundspace, color);
                holder.view.setClickable(true);
                holder.view.setEnabled(true);
            }

            if (device instanceof SpeakerDevice) {
                if (device.getModel() == SpeakerModel.UNKNOWN) {
                    setRingColor(holder.rl_soundspace, DeviceColor.SALTY_GREY.getMainColor());
                    holder.iv_logo.setImageDrawable(device.getIcon());
                    holder.tv_item.setTextColor(DeviceColor.SALTY_GREY.getMainColor());
                } else {
                    // old speaker color
                }
            }*/
        } else {
            /*List<AbstractSpeakerDevice> speakerIDList = ((LSSDPGroup) device).getSpeakers();
            if (speakerIDList.size() > 1) {
                for (int i = 0; i < speakerIDList.size(); i++) {
                    int batteryLevel = speakerIDList.get(i).getBatteryLevelInt();
                    int signalStrength = speakerIDList.get(i).getSignalStrength();
                    UpdateInfo info = speakerIDList.get(i).getUpdateInfo();
                    if (batteryLevel <= Constants.Device.BATTERY_LOW_POWER || signalStrength < Constants.Device.SIGNALSTRENGTH ||
                            (info.isHasNewVersion() || info.getUpdateStatus() == FirmwareUpdateState.WAITING_FOR_UPDATE)) {
                        // GTLog.d(TAG, "battery=" + batteryLevel + " signal=" + signalStrength + " firmware=" + info.isHasNewVersion() + " firmware status=" + info.getUpdateStatus());
                        holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.exclamationmark));
                        holder.iv_status.setVisibility(View.VISIBLE);
                       *//* break;*//*
                    } else {
                        holder.iv_status.setVisibility(View.GONE);
                    }
                    boolean btcallstatus = speakerIDList.get(i).getBtCallStatus(false);

                    if (btcallstatus) {
                        holder.iv_logo.setImageDrawable(ColorDrawableGenerator.coloredIcon(color, context.getResources().getDrawable(R.drawable.producticonincommingcall)));
                        holder.iv_logo.setVisibility(View.VISIBLE);
                        //TODO set ring color and set item can't click
                        holder.rl_soundspace.setBackground(context.getResources().getDrawable(R.drawable.stroke_gray_circle));
                      *//*  setRingColor( holder.rl_soundspace,BTCALL_Color);*//*
                        holder.view.setClickable(false);
                        holder.view.setEnabled(false);
                        break;
                    } else {
                        //TODO set ring color
                        color = device.getColor();
                        drawGroup(holder.rl_soundspace, (LSSDPGroup) device);
                        setGroupRingColor(holder.rl_soundspace, color);
                        holder.iv_logo.setVisibility(View.INVISIBLE);
                        *//*setRingColor( holder.rl_soundspace,color);*//*
                        holder.view.setClickable(true);
                        holder.view.setEnabled(true);
                        if (batteryLevel <= Constants.Device.BATTERY_LOW_POWER || signalStrength < Constants.Device.SIGNALSTRENGTH ||
                                (info.isHasNewVersion() || info.getUpdateStatus() == FirmwareUpdateState.WAITING_FOR_UPDATE)) {
                            // GTLog.d(TAG, "battery=" + batteryLevel + " signal=" + signalStrength + " firmware=" + info.isHasNewVersion() + " firmware status=" + info.getUpdateStatus());
                            holder.iv_status.setImageDrawable(context.getResources().getDrawable(R.drawable.exclamationmark));
                            holder.iv_status.setVisibility(View.VISIBLE);
                            break;
                        } else {
                            holder.iv_status.setVisibility(View.GONE);
                        }
                    }
                }
            }*/

        }
    }

    private void showBatteryOrUpdateOrSignal(String speakerid) {

    }

    private int sSize(int size) {
        float screenScale = UIUtils.dip2Px(1) / 4f;
        return (int) (size * screenScale);
    }

    public void drawGroup(View view, MergeViewBean group) {
        long startTime = System.nanoTime();
        Bitmap bmp = Bitmap.createBitmap(sSize(800), sSize(800), Bitmap.Config.ARGB_8888);

        List<MergeViewBean> speakersId = group.getMergeViewBeans();
        List<Bitmap> icons = new ArrayList<>();
        for (int i = 0; i < speakersId.size(); i++) {
            MergeViewBean device = speakersId.get(i);
            Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
            Bitmap icon = ((BitmapDrawable) d).getBitmap();
            icons.add(icon);
        }

        Paint paint = new Paint();
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);

        int grey = context.getResources().getColor(R.color.grey);
        Canvas canvas = new Canvas(bmp);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(sSize(400), sSize(400), sSize(385), paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(grey);
        canvas.drawLine(sSize(130), sSize(127), sSize(669), sSize(669), paint);
        Bitmap icon;
        int degree;
        float scale;
        int x;
        int y;
        int color;
        switch (group.getSpeakerNum()) {
            case 1:
                canvas.drawLine(sSize(477), sSize(477), sSize(740), sSize(218), paint);

                // icon master
                icon = icons.get(0);
                if (icon.getHeight() > icon.getWidth()) {
                    degree = -45;
                    scale = 1.7f;
                    x = sSize(160);
                    y = sSize(280);
                } else if (icon.getHeight() < icon.getWidth()) {
                    degree = 45;
                    scale = 1.6f;
                    x = sSize(430);
                    y = sSize(410);
                } else {
                    degree = 0;
                    scale = 1.4f;
                    x = sSize(270);
                    y = sSize(480);
                }

                canvas.save();
                canvas.rotate(degree, sSize(400), sSize(400));
                //color = speakersId.get(0).getColor();
                color = Color.GREEN;
                canvas.scale(scale, scale, icon.getWidth() * scale, icon.getHeight() * scale);
                canvas.drawBitmap(icon,
                        x,
                        y,
                        ColorDrawableGenerator.coloredPaint(color));
                canvas.restore();
                break;
            case 2:
                canvas.drawLine(sSize(477), sSize(477), sSize(740), sSize(218), paint);

                // icon master
                //icon = icons.get(0);
                Drawable d = context.getResources().getDrawable(R.drawable.ic_launcher);
                icon = ((BitmapDrawable) d).getBitmap();
                if (icon.getHeight() > icon.getWidth()) {
                    degree = -45;
                    scale = 1.7f;
                    x = sSize(160);
                    y = sSize(280);
                } else if (icon.getHeight() < icon.getWidth()) {
                    degree = 45;
                    scale = 1.6f;
                    x = sSize(430);
                    y = sSize(410);
                } else {
                    degree = 0;
                    scale = 1.4f;
                    x = sSize(270);
                    y = sSize(480);
                }

                canvas.save();
                canvas.rotate(degree, sSize(400), sSize(400));
                //color = DeviceManager.getInstance().getDevice(speakersId.get(0)).getColor();
                //color = group.getColor();
                color = Color.BLACK;
                canvas.scale(scale, scale, icon.getWidth() * scale, icon.getHeight() * scale);
                canvas.drawBitmap(icon,
                        x,
                        y,
                        ColorDrawableGenerator.coloredPaint(color));
                canvas.restore();

                // icon slave
                icon = icons.get(1);
                if (icon.getHeight() > icon.getWidth()) {
                    degree = -45;
                    scale = 1.6f;
                    x = sSize(400);
                    y = sSize(590);
                } else if (icon.getHeight() < icon.getWidth()) {
                    degree = 45;
                    scale = 1.6f;
                    x = sSize(720);
                    y = sSize(200);
                } else {
                    degree = 0;
                    scale = 1.4f;
                    x = sSize(650);
                    y = sSize(430);
                }

                canvas.save();
                canvas.rotate(degree, sSize(400), sSize(400));
                canvas.scale(scale, scale, icon.getWidth() * scale, icon.getHeight() * scale);
                canvas.drawBitmap(icon,
                        x - icon.getWidth() / 2,
                        y - icon.getHeight() / 2,
                        ColorDrawableGenerator.coloredPaint(grey));
                canvas.restore();
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                canvas.drawLine(sSize(477), sSize(477), sSize(740), sSize(218), paint);

                // icon master
                //Drawable d3 = group.getIcon();
                Drawable d3 = context.getResources().getDrawable(R.drawable.ic_launcher);
                icon = ((BitmapDrawable) d3).getBitmap();
                //icon = icons.get(0);
                if (icon.getHeight() > icon.getWidth()) {
                    degree = -45;
                    scale = 1.7f;
                    x = sSize(160);
                    y = sSize(280);
                } else if (icon.getHeight() < icon.getWidth()) {
                    degree = 45;
                    scale = 1.6f;
                    x = sSize(430);
                    y = sSize(410);
                } else {
                    degree = 0;
                    scale = 1.4f;
                    x = sSize(270);
                    y = sSize(480);
                }

                canvas.save();
                canvas.rotate(degree, sSize(400), sSize(400));
                //color = group.getColor();
                color = Color.BLACK;
                //color = DeviceManager.getInstance().getDevice(speakersId.get(0)).getColor();
                canvas.scale(scale, scale, icon.getWidth() * scale, icon.getHeight() * scale);
                canvas.drawBitmap(icon,
                        x,
                        y,
                        ColorDrawableGenerator.coloredPaint(color));
                canvas.restore();

                // icon slave
                icon = icons.get(1);
                if (icon.getHeight() > icon.getWidth()) {
                    degree = -45;
                    scale = 1.6f;
                    x = sSize(400);
                    y = sSize(590);
                } else if (icon.getHeight() < icon.getWidth()) {
                    degree = 45;
                    scale = 1.6f;
                    x = sSize(720);
                    y = sSize(200);
                } else {
                    degree = 0;
                    scale = 1.4f;
                    x = sSize(650);
                    y = sSize(430);
                }

                canvas.save();
                canvas.rotate(degree, sSize(400), sSize(400));
                canvas.scale(scale, scale, icon.getWidth() * scale, icon.getHeight() * scale);
                canvas.drawBitmap(icon,
                        x - icon.getWidth() / 2,
                        y - icon.getHeight() / 2,
                        ColorDrawableGenerator.coloredPaint(grey));
                canvas.restore();
                break;
        }

        //paint.setColor(group.getColor());
        paint.setColor(Color.BLACK);
        canvas.drawCircle(sSize(400), sSize(400), sSize(385), paint);

        // mask
        Bitmap output = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas maskcanvas = new Canvas(output);
        final int maskcolor = 0xff424242;
        final Paint maskpaint = new Paint();
        final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        maskpaint.setAntiAlias(true);
        maskcanvas.drawARGB(0, 0, 0, 0);
        maskpaint.setColor(maskcolor);
        maskcanvas.drawCircle(bmp.getWidth() / 2, bmp.getHeight() / 2, bmp.getWidth() / 2 - UIUtils.dip2Px(4), maskpaint);
        maskpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        maskcanvas.drawBitmap(bmp, rect, rect, maskpaint);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), output));
        } else {
            view.setBackground(new BitmapDrawable(context.getResources(), output));
        }

        long consumingTime = System.nanoTime();
        consumingTime -= startTime;
        /*GTLog.i(TAG, "group time " + consumingTime / 1000f / 1000f);*/
    }

    @Override
    public int getItemCount() {
        return ssinfos == null ? 0 : ssinfos.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_TYPE_BUTTON;
        } else {
            MergeViewBean device = ssinfos.get(position);
            if (device.getType().equals("LSSDPNodes")) {
                return ITEM_TYPE_NODE;
            } else if (device.getType().equals("LSSDPGroup")) {
                return ITEM_TYPE_GROUP;
            } else {
                return ITEM_TYPE_OLD;
            }
        }
    }


    public void setJoining(boolean isJoining) {
        this.isJoining = isJoining;
    }

    /**
     * *************
     * address drag and drop
     * **************
     */

    private void processDrop(MergeViewBean master, final MergeViewBean slave) {
        Log.i(TAG, "processDrop " + master.getName() + "|" + slave.getName());

        if (slave.isGroup()) {
            // illegal params
            return;
        }
        //TODO 添加设备
        DeviceManager.getInstance().joinGroup(master, slave);

        // de.greenrobot.event.EventBus.getDefault().post(new DeviceChangedEvent(slave.getId(), DeviceChangedEvent.OPERATION_REMOVE));
    }

    private void setGroupRingColor(View view, int color) {
        Bitmap bmp = ((BitmapDrawable) (view.getBackground())).getBitmap();

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(UIUtils.dip2Px(2));
        paint.setAntiAlias(true);

        Canvas canvas = new Canvas(bmp);
        paint.setColor(color);
        canvas.drawCircle(sSize(400), sSize(400), sSize(380), paint);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bmp));
        } else {
            view.setBackground(new BitmapDrawable(context.getResources(), bmp));
        }
    }

    private void setRingColor(View view, int color) {
        // GTLog.i(TAG, "setRingColor, " + view);
        color = Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
        GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
        if (gradientDrawable != null) {
            gradientDrawable.setStroke(UIUtils.dip2Px(2), color);
        }
    }

    private void setoutRing(View view, boolean visiable) {
        if (visiable) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
        private Point mScaleFactor;

        public MyDragShadowBuilder(View v) {
            super(v);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width;
            int height;

            // Sets the width of the shadow to half the width of the original View
            width = (int) (getView().getWidth() * FACTOR);

            // Sets the height of the shadow to half the height of the original View
            height = (int) (getView().getHeight() * FACTOR);

            // Sets the size parameter's width and height values. These get back to the system
            // through the size parameter.
            size.set(width, height);
            // Sets size parameter to member that will be used for scaling shadow image.
            mScaleFactor = size;

            // Sets the touch point's position to be in the middle of the drag shadow
            touch.set((int) (width * 0.5), (int) (height * 0.5));
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            // Draws the ColorDrawable in the Canvas passed in from the system.
            canvas.scale(mScaleFactor.x / (float) getView().getWidth(), mScaleFactor.y / (float) getView().getHeight());
            getView().draw(canvas);
        }

    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_soundspace;
        public TextView tv_link = null;
        public TextView tv_item;
        public TextView tv_number;
        public ImageView iv_logo;
        public ProgressWheel pb_waiting;
        public ImageView iv_status;
        public MergeViewBean device;
        public ImageView outring;
        public View view;

        DeviceViewHolder(View view) {
            super(view);
            this.view = view;
            outring = (ImageView) view.findViewById(R.id.outcircle);
            tv_link = (TextView) view.findViewById(R.id.tv_link);
            tv_item = (TextView) view.findViewById(R.id.tv_item);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            pb_waiting = (ProgressWheel) view.findViewById(R.id.pb_waiting);
            if (pb_waiting != null) {
                pb_waiting.setVisibility(View.INVISIBLE);
            }
            iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            iv_status.setVisibility(View.INVISIBLE);
            rl_soundspace = (RelativeLayout) view.findViewById(R.id.rl_soundspace);
            view.setTag(this);
        }
    }

    public class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }


    public class SoundSpaceOnDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View view, DragEvent event) {
            boolean result = false;
            // DragItem item = (DragItem)event.getLocalState();
            RelativeLayout localState = (RelativeLayout) event.getLocalState();
            DeviceViewHolder viewHolder = (DeviceViewHolder) localState.getTag();
            DeviceViewHolder dropHolder = null;
            MergeViewBean drag_info = viewHolder.device;
            MergeViewBean drop_info = null;
            scrollDistance = recyclerView.getScrollY();

            dropHolder = (DeviceViewHolder) view.getTag();
            drop_info = dropHolder.device;


            if (false) {
                if (dropHolder.outring.getVisibility() == View.INVISIBLE && event.getAction() != DragEvent.ACTION_DRAG_ENDED) {
                    setoutRing(dropHolder.outring, true);
                    dropHolder.outring.setAlpha(0.5f);
                    view.setAlpha(0.5f);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.95f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.95f);
                    AnimatorSet animSetXY = new AnimatorSet();
                    animSetXY.playTogether(scaleX, scaleY);
                    animSetXY.setDuration(200);
                    animSetXY.start();

                } else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                    setoutRing(dropHolder.outring, false);
                    view.setAlpha(1f);
                    ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
                    ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f);
                    AnimatorSet animSetXY = new AnimatorSet();
                    animSetXY.playTogether(scaleX, scaleY);
                    animSetXY.setDuration(200);
                    animSetXY.start();
                }
                return true;
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED: {
                    // claim to accept any dragged content
                    Log.i(TAG, "Drag started, " + drag_info.getId() + drop_info.getId());
                    if (drop_info.isGroup()) {
                        setGroupRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    } else {
                        setRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    }
                    if (drag_info.getId().equals(drop_info.getId())) {
                        view.setVisibility(View.INVISIBLE);
                    }

                    // cache whether we accept the drag to return for LOCATION events
                    result = true;
                }
                break;

                case DragEvent.ACTION_DRAG_ENDED: {
                    Log.i(TAG, "Drag ended." + drag_info.getId() + drop_info.getId());

                    if (drop_info.isGroup()) {
                        setGroupRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    } else {
                        setRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    }
                    setoutRing(dropHolder.outring, false);
                    if (drag_info.getId().equals(drop_info.getId())) {
                        view.setVisibility(View.VISIBLE);
                    }

/*
                    // useless, should disable long click when joining group
                    if (drag_info.getId().equals(drop_info.getId())) {
                        view.setEnabled(false); // disable until refresh
                        setRingColor(viewHolder.rl_soundspace, grey);
                        view.setVisibility(View.VISIBLE);
                    }
*/
                }
                break;

                case DragEvent.ACTION_DRAG_LOCATION: {
                    // we returned true to DRAG_STARTED, so return true here
                    // GTLog.i(TAG, "... seeing drag locations ...");
                    float vy = view.getY();
                    float ey = event.getY();
                    int y = Math.round(view.getY()) + Math.round(event.getY());
                    int translatedY = y - scrollDistance;
                    int screenHeight = UIUtils.dip2Px(640);
                    int threshold = UIUtils.dip2Px(80);
                    int toolbarHeight = UIUtils.dip2Px(78);
                    int move_dist = UIUtils.dip2Px(20);
                    // make a scrolling up due the y has passed the threshold
                    if (translatedY < UIUtils.dip2Px(150)) {
                        // make a scroll up by 30 px
                        recyclerView.smoothScrollBy(0, -move_dist);
                    }
                    // make a autoscrolling down due y has passed the 500 px border
                    if (translatedY + threshold > screenHeight - toolbarHeight) {
                        // make a scroll down by 30 px
                        recyclerView.smoothScrollBy(0, move_dist);
                    }
                 /*   GTLog.i(TAG, String.format("vy=%f, ey=%f, y=%d, scrollDistance=%d, translatedY=%d\n", vy, ey, y, scrollDistance, translatedY));*/
                }
                break;

                case DragEvent.ACTION_DROP: {
                   /* if(drop_info instanceof  SpeakerDevice){
                        setoutRing(dropHolder.outring,true);
                        view.setAlpha(0.5f);
                    }*/
                    Log.i(TAG, "Got a drop! " + drag_info.getId() + drop_info.getId());
                    if (drag_info.getId().equals(drop_info.getId())) {
                        view.setVisibility(View.VISIBLE);
                    } else if (!drag_info.isGroup()) {
                        isJoining = true;
                        processDrop(drop_info, drag_info);
                    }

                    result = true;
                }
                break;

                case DragEvent.ACTION_DRAG_ENTERED: {
                    Log.i(TAG, "Entered dot @ " + drag_info.getId() + drop_info.getId());

                    if (drop_info.isGroup()) {
                        setGroupRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    } else {
                        setRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    }
                    setoutRing(dropHolder.outring, true);
                }
                break;

                case DragEvent.ACTION_DRAG_EXITED: {
                    Log.i(TAG, "Exited dot @ " + drag_info.getId() + drop_info.getId());

                    if (drop_info.isGroup()) {
                        setGroupRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    } else {
                        setRingColor(dropHolder.rl_soundspace, drop_info.getColor());
                    }
                    setoutRing(dropHolder.outring, false);
                }
                break;

                default:
                    Log.i(TAG, "other drag event: " + event);
                    break;
            }

            return result;
        }
    }

}
