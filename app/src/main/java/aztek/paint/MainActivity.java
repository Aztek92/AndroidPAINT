package aztek.paint;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onPause() {
       super.onPause();
        Surface surface = (Surface)findViewById(R.id.drawning);
        surface.continueDrawing();
    }

    public void reset(View v) {
        Surface surface =(Surface)findViewById(R.id.drawning);
        surface.reset();
    }

    public void setYellow(View v) {
        Surface surface =(Surface)findViewById(R.id.drawning);
        surface.setColor("Yellow");
    }
    public void setRed(View v) {
        Surface surface =(Surface)findViewById(R.id.drawning);
        surface.setColor("Red");
    }

    public void setBlue(View v) {
        Surface surface =(Surface)findViewById(R.id.drawning);
        surface.setColor("Blue");
    }

    public void setGreen(View v) {
        Surface surface =(Surface)findViewById(R.id.drawning);
        surface.setColor("Green");
    }
}
