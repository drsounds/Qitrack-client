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
import com.aleros.tastybean.GetCallback;
import com.aleros.tastybean.TastyException;
import com.aleros.tastybean.TastyObject;
import com.aleros.tastybean.TastyQuery;
import com.aleros.tastybean.TastyResult;

import java.util.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AccountActivity extends ActionBarActivity {

    private ArrayAdapter<TastyObject> mAdapter;
    private ArrayList<TastyObject> objects = new ArrayList<TastyObject>();
    private String mAccountId;
    ListView listView;
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
                    v = inflater.inflate(R.layout.item_transaction, null);
                }
                TastyObject object = this.getItem(position);
                SimpleDateFormat sdf = new SimpleDateFormat();
                SimpleDateFormat outputDate = new SimpleDateFormat("yy-MM-dd");

                Date transaction_date = new Date();
                try {
                    transaction_date = sdf.parse(object.get("time").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ((TextView)v.findViewById(R.id.tv_description)).setText(object.get("text").toString());
                ((TextView)v.findViewById(R.id.tv_amount)).setText(NumberFormat.getInstance().format(Float.valueOf(object.get("amount").toString())));
                ((TextView)v.findViewById(R.id.tv_date)).setText(outputDate.format(transaction_date));
                return v;
            }
        };

        TastyQuery query1 = new TastyQuery("me/accounts/" + mAccountId);
        query1.getSingleBackground(new GetCallback() {

            @Override
            public void done(TastyObject result, TastyException e) {
                TextView balance = (TextView)findViewById(R.id.tv_balance);
                balance.setText(NumberFormat.getInstance().format(Float.valueOf(result.get("balance").toString())));
            }
        });

        TastyQuery query = new TastyQuery("me/accounts/" + mAccountId + "/transactions");
        query.getNextBackground(new FindCallback() {
            @Override
            public void done(TastyResult result, TastyException e) {
                objects.addAll(result.objects());
                mAdapter.notifyDataSetChanged();
            }
        });
        listView = (ListView)findViewById(R.id.listView);
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
