package com.yh.mohudaily.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.yh.mohudaily.R;
import com.yh.mohudaily.util.DimensionUtils;
import com.yh.mohudaily.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YH on 2016/12/7.
 */

public class MohuView extends View {
    //默认空间大小 内边距
    private int defaultSize;
    private int arcDistance;
    // 默认Padding值
    private final static int defaultPadding = 20;
    //  圆环起始角度
    private final static float mStartAngle = 165f;
    // 圆环结束角度
    private final static float mEndAngle = 210f;
    private Paint middleArcPaint;//中间圆圈画笔
    private Paint innerArcPaint; //内层圆环画笔
    private Paint mTextPaint;  //正中间字体画笔
    private Paint mCalibrationPaint; //圆环大刻度画笔
    private Paint mSmallCalibrationPaint; //圆环小刻度画笔
    private Paint mCalibrationTextPaint;
    private Paint mArcProgressPaint;
    private Paint mBitmapPaint;
    //小圆点
    private Bitmap bitmap;
    private float[] pos;
    private float[] tan;
    private Matrix matrix;
    private RectF mMiddleRect;
    private RectF mInnerRect;
    private RectF mMiddleProgressRect;
    // 最小数字
    private int mMinNum = 0;
    // 最大数字
    private int mMaxNum = 600;
    //信用等级
    private String sesameLevel = "";
    //评估时间
    private String evaluationTime = "";
    // 当前进度
    private float mCurrentAngle = 0f;
    //总进度
    private float mTotalAngle = 210f;
    private Paint mTextPaint2;


    public MohuView(Context context) {
        this(context, null);
    }


    public MohuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MohuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化绘制工具
     */
    private void init(Context context) {
        defaultSize = DimensionUtils.dip2px(context, 250);
        arcDistance = DimensionUtils.dip2px(context, 14);
        //中间圆圈画笔
        middleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        middleArcPaint.setStrokeWidth(8);
        middleArcPaint.setColor(Color.WHITE);
        middleArcPaint.setStyle(Paint.Style.STROKE);
        middleArcPaint.setAlpha(80);
        //内层圆环画笔
        innerArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerArcPaint.setStrokeWidth(30);
        innerArcPaint.setColor(Color.WHITE);
        innerArcPaint.setAlpha(80);
        innerArcPaint.setStyle(Paint.Style.STROKE);

        //圆环大刻度画笔
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setStrokeWidth(4);
        mCalibrationPaint.setStyle(Paint.Style.STROKE);
        mCalibrationPaint.setColor(Color.WHITE);
        mCalibrationPaint.setAlpha(120);
        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(30);
        mCalibrationTextPaint.setColor(Color.WHITE);
        //正中间字体画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //文本画笔
        mTextPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setTextSize(30);
        mTextPaint2.setColor(Color.WHITE);
        mTextPaint2.setTextAlign(Paint.Align.CENTER);


        //外层进度画笔
        mArcProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcProgressPaint.setStrokeWidth(8);
        mArcProgressPaint.setColor(Color.WHITE);
        mArcProgressPaint.setStyle(Paint.Style.STROKE);
        mArcProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        //外层圆环上小圆点Bitmap画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        //初始化小圆点图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.point_img);
        //当前点的实际位置
        pos = new float[2];
        //当前点的tangent值
        tan = new float[2];
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveMeasure(widthMeasureSpec, defaultSize), resolveMeasure(heightMeasureSpec, defaultSize));
        LogUtil.LogE("view onMeasure");
    }

    //处理measurespec 设置wrap_content时自定义View的默认值
    private int resolveMeasure(int measureSpec, int defaultSize) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                result = defaultSize;
                break;
            default:
                result = defaultSize;
        }
        return result;
    }

    private int width, height, radius;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.LogE("view onSizeChanged,width:"+w+"height:"+h);
        width = w;
        height = h;
        radius = width / 2;
        //外层圆环矩形
        mMiddleRect = new RectF(defaultPadding, defaultPadding, width - defaultPadding, height - defaultPadding);
        //内层圆环矩形
        mInnerRect = new RectF(defaultPadding + arcDistance, defaultPadding + arcDistance, width - defaultPadding - arcDistance, height - defaultPadding - arcDistance);
        // 外层进度矩形
        mMiddleProgressRect = new RectF(defaultPadding, defaultPadding, width - defaultPadding, height - defaultPadding);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.LogE("view onDraw");
        drawMiddleArc(canvas);
        drawInnerArc(canvas);
        drawSmallCalibration(canvas);
        drawCalibrationAndText(canvas);
        drawCenterText(canvas);
        drawRingProgress(canvas);
    }

    /**
     * 外层圆弧
     *
     * @param canvas
     */
    private void drawMiddleArc(Canvas canvas) {
        canvas.drawArc(mMiddleRect, mStartAngle, mEndAngle, false, middleArcPaint);
    }

    /**
     * 绘制内层圆环
     *
     * @param canvas
     */
    private void drawInnerArc(Canvas canvas) {
        canvas.drawArc(mInnerRect, mStartAngle, mEndAngle, false, innerArcPaint);
    }

    /**
     * 绘制内层小刻度
     *
     * @param canvas
     */
    private void drawSmallCalibration(Canvas canvas) {
        //保存画布状态
        canvas.save();
        //旋转画布
        canvas.rotate(-105, radius, radius);
//        //计算刻度线的起点结束点
        int startDst = (int) (defaultPadding + arcDistance - innerArcPaint.getStrokeWidth() / 2 - 1);
        int endDst = (int) (startDst + innerArcPaint.getStrokeWidth());

        for (int i = 0; i <= 35; i++) {
            //每旋转6度绘制一个小刻度
            canvas.drawLine(radius, startDst, radius, endDst, mSmallCalibrationPaint);
            canvas.rotate(6, radius, radius);
        }
        //返回未旋转前状态
        canvas.restore();
    }

    /**
     * 绘制刻度
     * 有指示的刻度值
     *
     * @param canvas
     */
    private void drawCalibrationAndText(Canvas canvas) {
        //旋转画布进行绘制对应的刻度
        canvas.save();
        canvas.rotate(-105, radius, radius);
        //计算刻度线的起点结束点
        int startDst = (int) (defaultPadding + arcDistance - innerArcPaint.getStrokeWidth() / 2 - 1);
        int endDst = (int) (startDst + innerArcPaint.getStrokeWidth());
        //刻度旋转的角度
        int rotateAngle = 210 / 10;
        for (int i = 1; i < 12; i++) {
            if (i % 2 != 0) {
                canvas.drawLine(radius, startDst, radius, endDst, mCalibrationPaint);
            }
            // 测量文本的长度
            float textLen = mCalibrationTextPaint.measureText(sesameStr[i - 1]);
            canvas.drawText(sesameStr[i - 1], radius - textLen / 2, endDst + 40, mCalibrationTextPaint);
            canvas.rotate(rotateAngle, radius, radius);
        }
        canvas.restore();
    }

    /**
     * 中心文字
     *
     * @param canvas
     */
    private void drawCenterText(Canvas canvas) {
        //绘制Logo
        mTextPaint2.setTextSize(30);
        canvas.drawText("BETA", radius, radius - 130, mTextPaint2);
        //绘制信用分数
        mTextPaint2.setTextSize(200);
        canvas.drawText(String.valueOf(mMinNum), radius, radius + 70, mTextPaint2);
        //绘制信用级别
        mTextPaint2.setTextSize(80);
        canvas.drawText(sesameLevel, radius, radius + 160, mTextPaint2);
        //绘制评估时间
        mTextPaint2.setTextSize(30);
        canvas.drawText(evaluationTime, radius, radius + 205, mTextPaint2);
    }

    /**
     * 核心方法  绘制小圆点进度
     *
     * @param canvas
     */
    private void drawRingProgress(Canvas canvas) {

        Path path = new Path();
        path.addArc(mMiddleProgressRect, mStartAngle, mCurrentAngle);
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength() * 1, pos, tan);
        matrix.reset();
        try {

            matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);
        } catch (Exception e) {
            LogUtil.LogE(e.toString());
        }
        canvas.drawPath(path, mArcProgressPaint);
        //起始角度不为0时候才进行绘制小圆点
        if (mCurrentAngle == 0)
            return;
        canvas.drawBitmap(bitmap, matrix, mBitmapPaint);
        mBitmapPaint.setColor(Color.WHITE);
        canvas.drawCircle(pos[0], pos[1], 8, mBitmapPaint);
    }

    /**
     * 开启绘制动画
     */
    public void startAnim() {
        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();

            }
        });
        mAngleAnim.start();
        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }

    // 圆环的信用等级文本
    String[] sesameStr = new String[]{
            "0", "图样",
            "200", "图森破",
            "250", "naive",
            "300", "载舟",
            "350", "赛艇",
            "400"
    };

    /**
     * 设置信用值
     *
     * @param values
     */

    public void setSesameValues(int values) {
        if (values <= 0) {
            mMaxNum = values;
            mTotalAngle = 0f;
            sesameLevel = "膜法学徒";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 200) {
            mMaxNum = values;
            mTotalAngle = values * 42 / 200f + 2;
            sesameLevel = "膜法师";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 300) {
            mMaxNum = values;
            mTotalAngle = (values - 200) * 82 / 100f + 43;
            sesameLevel = "膜导师";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 400) {
            mMaxNum = values;
            mTotalAngle = (values - 300) * 82 / 100f + 127;
            sesameLevel = "大膜导师";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else {
            mMaxNum = values;
            mTotalAngle = 210f;
            sesameLevel = "瞬发炎爆膜导师";
            evaluationTime = "评估时间:" + getCurrentTime();
        }
        startAnim();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getCurrentTime() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
        return format.format(new Date());
    }
}

