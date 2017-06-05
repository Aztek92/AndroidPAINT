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
        Surface surface = (Surface)findViewById(R.id.rysunek);
        surface.wznowRysowanie();
    }

    public void reset(View v) {
        Surface surface =(Surface)findViewById(R.id.rysunek);
        surface.reset();
    }

    public void ustawZolty(View v) {
        Surface surface =(Surface)findViewById(R.id.rysunek);
        surface.ustawKolor("Yellow");
    }
    public void ustawCzerwony(View v) {
        Surface surface =(Surface)findViewById(R.id.rysunek);
        surface.ustawKolor("Red");
    }

    public void ustawNiebieski(View v) {
        Surface surface =(Surface)findViewById(R.id.rysunek);
        surface.ustawKolor("Blue");
    }

    public void ustawZielony(View v) {
        Surface surface =(Surface)findViewById(R.id.rysunek);
        surface.ustawKolor("Green");
    }
}
