package io.webconnector.qitrack;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aleros.tastybean.FindCallback;
import com.aleros.tastybean.TastyException;
import com.aleros.tastybean.TastyObject;
import com.aleros.tastybean.TastyQuery;
import com.aleros.tastybean.TastyResult;

import java.util.ArrayList;


public class AccountActivity extends ActionBarActivity {

    private ArrayAdapter<TastyObject> mAdapter;
    private ArrayList<TastyObject> objects = new ArrayList<TastyObject>();
    private String mAccountId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAccountId = getIntent().getStringExtra("id");
        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<TastyObject>(this,
                R.layout.item_account, android.R.id.text1, objects)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater inflater =getLayoutInflater();
                    v = inflater.inflate(R.layout.item_account, null);
                }
                TastyObject object = this.getItem(position);
                ((TextView)v.findViewById(R.id.tv_account)).setText(object.get("name").toString());
                return v;
            }
        };

        TastyQuery query = new TastyQuery("me/accounts/" + mAccountId + "/transactions");
        query.getNextBackground(new FindCallback() {
            @Override
            public void done(TastyResult result, TastyException e) {
                objects.addAll(result.objects());
            }
        });
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
