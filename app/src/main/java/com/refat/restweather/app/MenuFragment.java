package com.refat.restweather.app;

import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.refat.restweather.app.retrofit.RetroMainFragment;

/**
 * Created by refat on 26/12/2015.
 */
public class MenuFragment extends Fragment implements View.OnClickListener{
    Button bt_retrofit,bt_volley,bt_asynctask;
    FragmentManager fm;
    FragmentTransaction ft;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View view=inflater.inflate(R.layout.layout_mainfragment,container,false);

        bt_retrofit=(Button)view.findViewById(R.id.bt_retrofit);
        bt_volley=(Button)view.findViewById(R.id.bt_volley);
        bt_asynctask=(Button)view.findViewById(R.id.bt_asynctask);

        bt_retrofit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_retrofit){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container_body,new RetroMainFragment())
                    .addToBackStack(null)
                    .commit();
        }
        if(v.getId()==R.id.bt_volley){

        }
        if(v.getId()==R.id.bt_asynctask){

        }
    }
}
