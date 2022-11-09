package com.vocesdelolimpo.triogb.Juegos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vocesdelolimpo.triogb.R;

import java.util.ArrayList;
import java.util.Collections;


public class OrdenFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        ArrayList <Button> botones = new ArrayList<Button>();

        final ArrayList numeros = new ArrayList();



        return inflater.inflate(R.layout.fragment_orden, container, false);
    }

}