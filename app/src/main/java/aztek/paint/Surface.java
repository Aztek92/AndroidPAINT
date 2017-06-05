package aztek.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by Aztek on 09.05.2017.
 */

public class Surface extends SurfaceView implements SurfaceHolder.Callback,Runnable
{
    // pozwala kontrolować i monitorować powierzchnię
    private SurfaceHolder mPojemnik=null;
    // wątek, który odświeża kanwę
    private Thread mWatekRysujacy;
    // flaga logiczna do kontrolowania pracy watku
    private boolean mWatekPracuje=false;
    // obiekt do tworzenia sekcji krytycznych
    private Object mBlokada= new Object();

    private Bitmap mBitmapa;
    private Canvas mKanwa;
    private Paint mFarba;
    private float x=1500;
    private float y=1500;
    private int grubosc=20;
    private boolean reset=false;

    private void PowierzchniaRysunku()
    {
        //Log.d("Kanwa", "inicjalizacja");
        mPojemnik=getHolder();
        mPojemnik.addCallback(this);
        mBitmapa= Bitmap.createBitmap(2000, 2000, Bitmap.Config.ARGB_8888);
        mKanwa= new Canvas(mBitmapa);
        mKanwa.drawARGB(255, 255, 255, 255);
        mFarba=new Paint();
        mFarba.setColor(Color.RED);

        setFocusable(true);
    }

    public Surface(Context context)
    {
        super(context);
        PowierzchniaRysunku();
    }

    public Surface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        PowierzchniaRysunku();
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        if (reset)
        {
            reset = false;
            mKanwa.drawARGB(255,255,255,255);
        }
        mKanwa.drawCircle(x, y, grubosc, mFarba);
        canvas.drawBitmap(mBitmapa, 0, 0, null);

    }

    public void wznowRysowanie() {
        mWatekRysujacy= new Thread(this);
        mWatekPracuje =true;
        mWatekRysujacy.start();
    }

    public void pauzujRysowanie()
    {
        mWatekPracuje=false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        x=event.getX();
        y=event.getY();
        performClick();
        Path mSciezka = null;
        mSciezka = new Path();
        synchronized (mBlokada)
        {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    grubosc=20;
                    mSciezka.moveTo(event.getX(), event.getY());
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                {
                    grubosc=10;
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    grubosc=20;
                    mSciezka.lineTo(event.getX(), event.getY());
                    mKanwa.drawPath(mSciezka, mFarba);
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
        while(mWatekPracuje)
        {
            if(!mPojemnik.getSurface().isValid())
                continue;
            Canvas kanwa=null;
            try
            {

                kanwa = mPojemnik.lockCanvas();

                synchronized (mPojemnik)
                {
                    postInvalidate();
                }
            }
            finally {
                if (kanwa != null) {
                    mPojemnik.unlockCanvasAndPost(kanwa);
                }
            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        setWillNotDraw(false);
        wznowRysowanie();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        pauzujRysowanie();
    }

    public void ustawKolor(String kolor)
    {   x=1500;
        y=1500;
        mFarba= new Paint();
        switch (kolor)
        {
            case "Red":
            {
                mFarba.setColor(Color.RED);
                break;
            }
            case "Green":
            {
                mFarba.setColor(Color.GREEN);
                break;
            }
            case "Blue":
            {
                mFarba.setColor(Color.BLUE);
                break;
            }
            case "Yellow":
            {
                mFarba.setColor(Color.YELLOW);
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
