package com.example.Galeria2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Toolbar fragment provides wide range of different drawing tools and enhances image editing functionality.
 * To be done:
 * <ol>
 *     <li>Colour picker with alpha channel</li>
 *     <li>Image filters (bitwise, sepia, black and white, etc.)</li>
 *     <li>Sample colours</li>
 *     <li>Eraser</li>
 *     <li>Different sizes of brushes</li>
 *     <li>Different brushes (optional)</li>
 *     <li>Fill tool (optional)</li>
 * </ol>
 * @author Ala
 * @since 09-05-2015
 */
public class ToolbarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbarfragment, container, false);
    }
}