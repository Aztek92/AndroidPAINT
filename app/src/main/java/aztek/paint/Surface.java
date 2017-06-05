package aztek.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by Aztek on 09.05.2017.
 */

public class Surface extends SurfaceView implements SurfaceHolder.Callback,Runnable
{
    private SurfaceHolder mHolder =null;
    private Thread mDrawningThread;
    private boolean mThreadStatus=false;
    private Object mBlock = new Object();

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private float x=1500;
    private float y=1500;
    private int thick =20;
    private boolean reset=false;

    private void createSurface()
    {
        mHolder =getHolder();
        mHolder.addCallback(this);
        mBitmap = Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawARGB(255, 255, 255, 255);
        mPaint =new Paint();
        mPaint.setColor(Color.RED);

        setFocusable(true);
    }

    public Surface(Context context)
    {
        super(context);
        createSurface();
    }

    public Surface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        createSurface();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if (reset)
        {
            reset = false;
            mCanvas.drawARGB(255,255,255,255);
        }
        mCanvas.drawCircle(x, y, thick, mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    public void continueDrawing() {
        mDrawningThread = new Thread(this);
        mThreadStatus =true;
        mDrawningThread.start();
    }

    public void pauseDrawning()
    {
        mThreadStatus =false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        x=event.getX();
        y=event.getY();
        performClick();
        Path mPath = null;
        mPath = new Path();
        synchronized (mBlock)
        {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    thick =20;
                    mPath.moveTo(event.getX(), event.getY());
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    thick =10;
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    thick =20;
                    mPath.lineTo(event.getX(), event.getY());
                    mCanvas.drawPath(mPath, mPaint);
                    break;
                }
            }
        }
        return true;
    }

    public boolean performClick()
    {
        return super.performClick();
    }

    @Override
    public void run()
    {
        while(mThreadStatus)
        {
            if(!mHolder.getSurface().isValid())
                continue;
            Canvas mCanvas=null;
            try
            {

                mCanvas = mHolder.lockCanvas();

                synchronized (mHolder)
                {
                    postInvalidate();
                }
            }
            finally {
                if (mCanvas != null) {
                    mHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        setWillNotDraw(false);
        continueDrawing();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        pauseDrawning();
    }

    public void setColor(String color)
    {   x=1500;
        y=1500;
        mPaint = new Paint();
        switch (color)
        {
            case "Red":
            {
                mPaint.setColor(Color.RED);
                break;
            }
            case "Green":
            {
                mPaint.setColor(Color.GREEN);
                break;
            }
            case "Blue":
            {
                mPaint.setColor(Color.BLUE);
                break;
            }
            case "Yellow":
            {
                mPaint.setColor(Color.YELLOW);
                break;
            }

        }

    }
    public void reset()
    {
        reset=true;
        x=1500;
        y=1500;
    }



}
