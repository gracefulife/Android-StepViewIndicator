package xyz.gracefulife.stepindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class StepsViewIndicator extends View {
  private static final String TAG = StepsViewIndicator.class.getSimpleName();
  private static final int THUMB_SIZE = 40;

  private Paint paint = new Paint();
  private Paint selectedPaint = new Paint();
  private int mNumOfStep = 2;
  private float mLineHeight;
  private float mThumbRadius;
  private float mCircleRadius;
  private float mPadding;
  private int mProgressColor = Color.YELLOW;
  private int mBarColor = Color.BLACK;

  private float mCenterY;
  private float mLeftX;
  private float mLeftY;
  private float mRightX;
  private float mRightY;
  private float mDelta;
  private List<Float> mThumbContainerXPosition = new ArrayList<>();
  private List<Float> mCircleXPosition = new ArrayList<>();
  private int mCompletedPosition;
  private OnDrawListener mDrawListener;

  public StepsViewIndicator(Context context) {
    this(context, null);
  }

  public StepsViewIndicator(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.StepsViewIndicator);
    mNumOfStep = a.getInt(R.styleable.StepsViewIndicator_numOfSteps, 0);
    a.recycle();

    init();
  }

  private void init() {
    mLineHeight = 0.2f * THUMB_SIZE;
    mThumbRadius = 0.4f * THUMB_SIZE;
    mCircleRadius = 0.7f * mThumbRadius;
    mPadding = 0;
  }

  public void setStepSize(int size) {
    mNumOfStep = size;
    invalidate();
  }

  public void setDrawListener(OnDrawListener drawListener) {
    mDrawListener = drawListener;
  }

  public List<Float> getThumbContainerXPosition() {
    return mThumbContainerXPosition;
  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    mThumbContainerXPosition.clear();
    mCircleXPosition.clear();

    mCenterY = 0.5f * getHeight();
    mLeftX = mPadding;
    mLeftY = mCenterY - (mLineHeight / 2);
    mRightX = getWidth() - mPadding;
    mRightY = 0.5f * (getHeight() + mLineHeight);
    mDelta = (mRightX - mLeftX) / mNumOfStep;

    float tLeft = mLeftX;
    mThumbContainerXPosition.add(tLeft);
    for (int i = 0; i < mNumOfStep; i++, tLeft += mDelta) {
      mCircleXPosition.add(tLeft + (mDelta / 2));
      mThumbContainerXPosition.add(mCircleXPosition.get(i));
    }
    mThumbContainerXPosition.add(mRightX);
    mDrawListener.onReady();
  }

  @Override
  protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = 200;
    if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
      width = MeasureSpec.getSize(widthMeasureSpec);
    }
    int height = THUMB_SIZE + 20;
    if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
      height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
    }
    setMeasuredDimension(width, height);
  }

  public void setCompletedPosition(int position) {
    mCompletedPosition = position;
  }

  public void reset() {
    setCompletedPosition(0);
  }

  public void setProgressColor(int progressColor) {
    mProgressColor = progressColor;
  }

  public void setBarColor(int barColor) {
    mBarColor = barColor;
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mDrawListener.onReady();
    // Draw rect bounds
    paint.setAntiAlias(true);
    paint.setColor(mBarColor);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(2);

    selectedPaint.setAntiAlias(true);
    selectedPaint.setColor(mProgressColor);
    selectedPaint.setStyle(Paint.Style.STROKE);
    selectedPaint.setStrokeWidth(2);

    Log.i(TAG, "onDraw: color is " + mBarColor + " / " + mProgressColor);

    Log.i(TAG, "onDraw: mCompletedPosition = " + mCompletedPosition);
    // 마지막 경우엔
    // Draw rest of the circle'Bounds
    for (int i = 0; i < mCircleXPosition.size(); i++) {
      canvas.drawCircle(mCircleXPosition.get(i), mCenterY, mCircleRadius,
          (i < mCompletedPosition) ? selectedPaint : paint);
    }

    paint.setStyle(Paint.Style.FILL);
    selectedPaint.setStyle(Paint.Style.FILL);
    for (int i = 0; i < mThumbContainerXPosition.size() - 1; i++) {
      final float pos = mThumbContainerXPosition.get(i);
      final float pos2 = mThumbContainerXPosition.get(i + 1);
      canvas.drawRect(pos, mLeftY, pos2, mRightY,
          (i < mCompletedPosition) ? selectedPaint : paint);
    }

    // if all completed, just draw all steps
    if (mCompletedPosition == mNumOfStep) {
      canvas.drawRect(mLeftX, mLeftY, mRightX, mRightY, selectedPaint);
    }

    // Draw rest of circle
    for (int i = 0; i < mCircleXPosition.size(); i++) {
      final float pos = mCircleXPosition.get(i);
      canvas.drawCircle(pos, mCenterY, mCircleRadius,
          (i < mCompletedPosition) ? selectedPaint : paint);
    }
  }

  public interface OnDrawListener {
    void onReady();
  }
}
