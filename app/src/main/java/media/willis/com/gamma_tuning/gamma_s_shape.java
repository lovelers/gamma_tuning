package media.willis.com.gamma_tuning;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class gamma_s_shape extends Activity {
    EditText[] mSShape = null;
    gamma_s_shape_view mGammaSShapeView = null;
    float[] mSGamma = null;
    float[] mNewSGamma = null;
    Button mApply = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamma_s_shape);
        mSShape = new EditText[] {
                (EditText)findViewById(R.id.s_shape_text1),(EditText)findViewById(R.id.s_shape_text2),(EditText)findViewById(R.id.s_shape_text3),(EditText)findViewById(R.id.s_shape_text4),
                (EditText)findViewById(R.id.s_shape_text5),(EditText)findViewById(R.id.s_shape_text6),(EditText)findViewById(R.id.s_shape_text7),(EditText)findViewById(R.id.s_shape_text8),
                (EditText)findViewById(R.id.s_shape_text9),(EditText)findViewById(R.id.s_shape_text10),(EditText)findViewById(R.id.s_shape_text11),(EditText)findViewById(R.id.s_shape_text12),
                (EditText)findViewById(R.id.s_shape_text13),(EditText)findViewById(R.id.s_shape_text14),(EditText)findViewById(R.id.s_shape_text15),(EditText)findViewById(R.id.s_shape_text16),
                (EditText)findViewById(R.id.s_shape_text17),(EditText)findViewById(R.id.s_shape_text18),(EditText)findViewById(R.id.s_shape_text19),(EditText)findViewById(R.id.s_shape_text20),
                (EditText)findViewById(R.id.s_shape_text21),(EditText)findViewById(R.id.s_shape_text22),(EditText)findViewById(R.id.s_shape_text23),(EditText)findViewById(R.id.s_shape_text24),
                (EditText)findViewById(R.id.s_shape_text25),(EditText)findViewById(R.id.s_shape_text26),(EditText)findViewById(R.id.s_shape_text27),(EditText)findViewById(R.id.s_shape_text28),
                (EditText)findViewById(R.id.s_shape_text29),(EditText)findViewById(R.id.s_shape_text30),(EditText)findViewById(R.id.s_shape_text31),(EditText)findViewById(R.id.s_shape_text32),
        };
        mSGamma = Gamma.getInstance().getSGamma();
        mNewSGamma = new float[Gamma.GAMMA_X_TABLE_COUNT];
        for (int i = 0; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            if (mSShape[i] != null) {
                mSShape[i].setText("" + Float.toString(mSGamma[i]));
                mSShape[i].setSelectAllOnFocus(true);
            }
            //mSShape[i].setOnClickListener(new MyOnClicklistener());
        }

        mGammaSShapeView = (gamma_s_shape_view) findViewById(R.id.gamma_s_shape_view);
        mGammaSShapeView.setXTable(Gamma.getInstance().getGammaTableX());
        mGammaSShapeView.setSGamma(mSGamma);
        mGammaSShapeView.setNewSGamma(mSGamma);

        mApply = (Button) findViewById(R.id.sshapeBtn);
        mApply.setOnClickListener(new ApplyOnClickListener());
    }
    class ApplyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
            }

            for (int i = 0; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
                mNewSGamma[i] = Float.parseFloat(mSShape[i].getText().toString());
                if (mNewSGamma[i] != mSGamma[i]) {
                    mSShape[i].setTextColor(Color.RED);
                }
            }
            if (mGammaSShapeView != null) {

                if (mGammaSShapeView != null) {
                    mGammaSShapeView.setNewSGamma(mNewSGamma);
                }
                mGammaSShapeView.invalidate();
            }

        }
    }
    class MyOnClicklistener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            view.setSelected(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamma_s_shape, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
