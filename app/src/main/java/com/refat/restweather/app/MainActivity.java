package com.refat.restweather.app;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.refat.restweather.app.retrofit.BarChartFragment;
import com.refat.restweather.app.retrofit.RetroMainFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RetroMainFragment.Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_body,new RetroMainFragment())
                .commit();
    }



    @Override
    public void sendForecastData(ArrayList<RetroMainFragment.RvData> rvList) {
        //Toast.makeText(this, "sending to barchart", Toast.LENGTH_LONG).show();
        Log.i("MainActivity", String.valueOf(rvList.size()));
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("arraylist",  rvList);
        BarChartFragment barChartFragment=new BarChartFragment();
        barChartFragment.setArguments(bundle);
        FragmentManager manager=getSupportFragmentManager();
        barChartFragment.show(manager,"cahrtdialog");
        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_body,barChartFragment)
                .addToBackStack(null)
                .commit();*/
       // barChartFragment.setGraphData(rvList);

    }
}
