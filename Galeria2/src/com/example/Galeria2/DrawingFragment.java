package com.example.Galeria2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Fragment containing {@link DrawingView}.
 * @author Ala
 * @since 09-05-2015
 * */

 public class DrawingFragment extends Fragment {

    private DrawingView drawingView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.drawingfragment, container, false);

        drawingView = (DrawingView) myView.findViewById(R.id.view);

        return myView;
    }

    public void passBackButton(){
        drawingView.backButtonPressed();
    }
}