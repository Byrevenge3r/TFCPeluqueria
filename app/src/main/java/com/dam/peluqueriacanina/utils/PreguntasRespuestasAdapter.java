package com.dam.peluqueriacanina.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.peluqueriacanina.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PreguntasRespuestasAdapter extends BaseExpandableListAdapter {

    ArrayList<String> listGroup;
    HashMap<String,ArrayList<String>> listChild;

    public PreguntasRespuestasAdapter(ArrayList<String> listGroup, HashMap<String, ArrayList<String>> listChild) {
        this.listGroup = listGroup;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        view = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.expandable_lista_preguntas_respuesta,parent,false);

        TextView textView = view.findViewById(R.id.tvDatosPreguntaRespuesta);

        textView.setTextSize(20);

        String sGroup = String.valueOf(getGroup(groupPosition));

        textView.setText(sGroup);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v, ViewGroup parent) {
        v = (LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.expandable_lista_preguntas_respuesta,parent,false);

        TextView textView = v.findViewById(R.id.tvDatosPreguntaRespuesta);

        String sChild = String.valueOf(getChild(groupPosition,childPosition));

        textView.setText(sChild);

        textView.setPadding(30,0,20,0);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),groupPosition +" "+sChild, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
