package com.refat.restweather.app.retrofit;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.refat.restweather.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by refat on 17/01/2016.
 */
public class BarChartFragment extends DialogFragment {

    List<RetroMainFragment.RvData> rvList;
    BarChart chart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle args = getArguments();
         rvList= new ArrayList<RetroMainFragment.RvData>();
         rvList=args.getParcelableArrayList("arraylist");
        //String title = args.getString("title");

        View view = inflater.inflate(R.layout.bar_chart_fragment,null);//dialog is independent of activity so.don't need to pass container and false
        setCancelable(true);//can be cancelled by tapping outside
        //rvList = new ArrayList<RetroMainFragment.RvData>();
        //Log.i("BarChartFragOCV", String.valueOf(rvList.size()));

        //generate bar chart based on 5 days day temperature record
        chart = (HorizontalBarChart) view.findViewById(R.id.chart);
        BarData data = new BarData(getXAxisValues(rvList), getDataSet(rvList));
        chart.getAxisLeft().setStartAtZero(false);
        chart.setData(data);
        chart.setDescription("Forecast Chart");
        chart.animateXY(2000, 2000);//animate bar chart
        chart.invalidate();

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //dialog.getWindow().requestFeature(Window.);

        return dialog;
    }



    private List<BarDataSet> getDataSet(List<RetroMainFragment.RvData> rvList) {

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();

        for (int i = 0; i < rvList.size(); i++) {
            valueSet1.add(new BarEntry(Float.valueOf(rvList.get(i).getTemp()), i));
            Log.i("BarChart",String.valueOf(rvList.get(i).getTemp()));
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
        /*
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
        barDataSet2.setColor(Color.rgb(0, 155, 0));*/

        dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet1);
        return dataSets;

    }

    private List<String> getXAxisValues(List<RetroMainFragment.RvData> rvList) {
        List<String> xAxisValues = new ArrayList<String>();


        for (int i=0;i<rvList.size();i++) {
            xAxisValues.add(rvList.get(i).getDate());
        }
        return xAxisValues;
    }
    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        super.onResume();
    }
}
