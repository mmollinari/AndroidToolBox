package fr.isen.mollinari.androidtoolbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;


public class LifeCycleFragment extends Fragment {


    public LifeCycleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogInActivity("Cycle de vie fragment : onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_life_cycle, container, false);
        showLogInActivity("Cycle de vie fragment : onCreateView");
        return fragView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showLogInActivity("Cycle de vie fragment : onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showLogInActivity("Cycle de vie fragment : onDetach");
    }

    public void onResume() {
        super.onResume();
        showLogInActivity("Cycle de vie fragment : onResume");
    }

    public void onPause() {
        super.onPause();
        showLogInActivity("Cycle de vie fragment : onPause");
    }

    public void onStop() {
        super.onStop();
        showLogInActivity("Cycle de vie fragment : onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        showLogInActivity("Cycle de vie fragment : onDestroy");
    }

    private void showLogInActivity(String logMessage) {
        ((LifeCycleActivity)getActivity()).showLog(logMessage);
    }
}
