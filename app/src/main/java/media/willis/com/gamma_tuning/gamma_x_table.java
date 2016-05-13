package media.willis.com.gamma_tuning;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class gamma_x_table extends Activity {
    EditText[] mXTable = null;
    gamma_x_table_view mGammaXTableView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamma_x_table);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamma_x_table, menu);

        int[] xTable = Gamma.getInstance().getGammaTableX();
        mXTable = new EditText[] {
                (EditText)findViewById(R.id.x_table_text1),(EditText)findViewById(R.id.x_table_text2),(EditText)findViewById(R.id.x_table_text3),(EditText)findViewById(R.id.x_table_text4),
                (EditText)findViewById(R.id.x_table_text5),(EditText)findViewById(R.id.x_table_text6),(EditText)findViewById(R.id.x_table_text7),(EditText)findViewById(R.id.x_table_text8),
                (EditText)findViewById(R.id.x_table_text9),(EditText)findViewById(R.id.x_table_text10),(EditText)findViewById(R.id.x_table_text11),(EditText)findViewById(R.id.x_table_text12),
                (EditText)findViewById(R.id.x_table_text13),(EditText)findViewById(R.id.x_table_text14),(EditText)findViewById(R.id.x_table_text15),(EditText)findViewById(R.id.x_table_text16),
                (EditText)findViewById(R.id.x_table_text17),(EditText)findViewById(R.id.x_table_text18),(EditText)findViewById(R.id.x_table_text19),(EditText)findViewById(R.id.x_table_text20),
                (EditText)findViewById(R.id.x_table_text21),(EditText)findViewById(R.id.x_table_text22),(EditText)findViewById(R.id.x_table_text23),(EditText)findViewById(R.id.x_table_text24),
                (EditText)findViewById(R.id.x_table_text25),(EditText)findViewById(R.id.x_table_text26),(EditText)findViewById(R.id.x_table_text27),(EditText)findViewById(R.id.x_table_text28),
                (EditText)findViewById(R.id.x_table_text29),(EditText)findViewById(R.id.x_table_text30),(EditText)findViewById(R.id.x_table_text31),(EditText)findViewById(R.id.x_table_text32),
        };
        for (int i = 0; i < Gamma.GAMMA_X_TABLE_COUNT; ++i) {
            if (mXTable[i] != null) {
                mXTable[i].setText("" + Integer.toString(xTable[i]));
                mXTable[i].setSelectAllOnFocus(true);
            }
        }
        mGammaXTableView = (gamma_x_table_view) findViewById(R.id.gamma_x_table_view);
        mGammaXTableView.setXTable(xTable);
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
