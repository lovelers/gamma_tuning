package media.willis.com.gamma_tuning;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class gamma_standard extends Activity {
    int mGammaType = -1;
    EditText mGammaExponent;
    EditText mGammaLowOffset;
    EditText mGammaHighOffset;
    Button mApplyBtn;
    CheckBox mSGammaCheckBox;
    gamma_view mGammaView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamma_standard);
        Intent intent = getIntent();
        mGammaType = intent.getIntExtra(gamma_type.GAMMA_TYPE, -1);
        if (false) {
            Toast toast = Toast.makeText(getApplicationContext(), "mGammaType = " + mGammaType, Toast.LENGTH_SHORT);
            toast.show();
            onDestroy();
        }
        this.setTitle(Gamma.getInstance().getGammaTypeString(mGammaType));
        mGammaView = (gamma_view) findViewById(R.id.gamma_view);

        mGammaView.setVisibility(View.VISIBLE);
        mGammaExponent = (EditText) findViewById(R.id.editText);
        mGammaLowOffset = (EditText) findViewById(R.id.editText2);
        mGammaHighOffset = (EditText) findViewById(R.id.editText3);
        mGammaExponent.setText(""+ Gamma.getInstance().getGammaExponent(mGammaType));
        mGammaLowOffset.setText("" + Gamma.getInstance().getGammaLowOffset(mGammaType));
        mGammaHighOffset.setText("" + Gamma.getInstance().getGammaHighOffset(mGammaType));
        mGammaView.setmGammaTableX(Gamma.getInstance().getGammaTableX());
        mGammaView.setInfo(Gamma.getInstance().getGammaExponent(mGammaType),
                Gamma.getInstance().getGammaLowOffset(mGammaType),
                Gamma.getInstance().getGammaHighOffset(mGammaType)
        );
        mApplyBtn = (Button) findViewById(R.id.button);
        mApplyBtn.setOnClickListener( new ApplyOnClickListener());

        mSGammaCheckBox = (CheckBox) findViewById(R.id.checkBox);
        if (mSGammaCheckBox != null) {
            mSGammaCheckBox.setChecked(false);
        }
    }

    class ApplyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Gamma.getInstance().setGammaExponent(mGammaType, Float.valueOf(mGammaExponent.getText().toString()));
            Gamma.getInstance().setGammaLowOffset(mGammaType, Integer.valueOf(mGammaLowOffset.getText().toString()));
            Gamma.getInstance().setGammaHighOffset(mGammaType, Integer.valueOf(mGammaHighOffset.getText().toString()));
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
            }
            if (mGammaView != null) {
                mGammaView.setInfo(Gamma.getInstance().getGammaExponent(mGammaType),
                        Gamma.getInstance().getGammaLowOffset(mGammaType),
                        Gamma.getInstance().getGammaHighOffset(mGammaType));
                if (mSGammaCheckBox != null) {
                    mGammaView.setSGammaInfo(mSGammaCheckBox.isChecked(), Gamma.getInstance().getSGamma());
                }
                mGammaView.invalidate();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamma_standard, menu);
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
