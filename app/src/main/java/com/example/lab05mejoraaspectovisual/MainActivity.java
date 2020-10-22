package com.example.lab05mejoraaspectovisual;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private List<Futbolista> futbolistas;
    private String[] nombres;
    private String[] selecciones;
    private String[] edades;
    private String[] clubes;
    private String[] infos;
    private String[] links;
    private TypedArray imagenes;

    private void inicializarDatos(){
        futbolistas = new ArrayList<>();
        nombres = getResources().getStringArray(R.array.nombres);
        selecciones = getResources().getStringArray(R.array.selecciones);
        edades = getResources().getStringArray(R.array.edades);
        clubes = getResources().getStringArray(R.array.clubes);
        infos = getResources().getStringArray(R.array.infos);
        links = getResources().getStringArray(R.array.links);
        imagenes = getResources().obtainTypedArray(R.array.images_players);
        for (int i = 0; i < nombres.length; i++){
            futbolistas.add(new Futbolista(nombres[i],edades[i],clubes[i],selecciones[i],infos[i],links[i],imagenes.getResourceId(i,-1)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        inicializarDatos();
        RVAdapter rvAdapter = new RVAdapter(futbolistas,getApplicationContext());
        recyclerView.setAdapter(rvAdapter);
    }
}