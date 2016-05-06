package media.willis.com.gamma_tuning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class GammaActivity extends Activity {

    ListView mGammaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamma);
        mGammaList = (ListView)findViewById(R.id.listView);
        ListAdapter gammaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, Gamma.getInstance().getGammaTypeString());
        mGammaList.setAdapter(gammaAdapter);
        mGammaList.setOnItemClickListener(gammaItemClickListener);
    }

    private final AdapterView.OnItemClickListener gammaItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Toast toast = Toast.makeText(getApplicationContext(), "toast+" + i, Toast.LENGTH_SHORT);
            //toast.show();
            Intent gammaIntent = null;
            switch(i) {
                case gamma_type.GAMMA_X_TABLE:
                    gammaIntent = new Intent(GammaActivity.this, gamma_x_table.class);
                    break;
                case gamma_type.GAMMA_NORMAL:
                case gamma_type.GAMMA_INDOOR:
                case gamma_type.GAMMA_OUTDOOR:
                    gammaIntent = new Intent(GammaActivity.this, gamma_standard.class);
                    break;
                case gamma_type.GAMMA_S_SHAPE:
                    break;
                case gamma_type.GAMMA_COMPOSE:
                    break;
                default:
                    break;
            }
            if (gammaIntent != null) {
                gammaIntent.putExtra(gamma_type.GAMMA_TYPE, i);
                startActivity(gammaIntent);
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gamma, menu);
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
