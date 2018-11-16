package com.example.aprendizajeactivo.cristianparcial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class Resultados extends AppCompatActivity implements settingList {

    FirebaseAU au;
    ListaFirebase<Pelicula> listaFirebase;

    private ListView lv_lista_resultados;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        lv_lista_resultados = findViewById(R.id.lv_lista_resultados);


        au.getIntance();
        reference = au.getReferencia().child(BaseValue.PELICULAS);

        listaFirebase = new ListaFirebase<>(new ListaFirebase.getVariables<Pelicula>() {
            @Override
            public ListView getViewListas() {
                return lv_lista_resultados;
            }

            @Override
            public Query getUbicacionBase() {
                return reference;
            }

            @Override
            public Class getClaseModelo() {
                return Pelicula.class;
            }

            @Override
            public int getLayoutList() {
                return R.layout.puntuacion;

            }

            @Override
            public void populateView(@NonNull View v, @NonNull Pelicula model, final int position) {

                TextView tv_puntua_name = v.findViewById(R.id.tv_puntua_name);
                final TextView tv_puntua_valor = v.findViewById(R.id.tv_puntua_valor);

                tv_puntua_name.setText(model.nombre);

                au.readObjectRealTime(new FirebaseAU.DataObjectListener() {
                    @Override
                    public DatabaseReference getReferenceDataBase() {
                        return listaFirebase.obtenerObjetoAList(position).child("puntua");
                    }

                    @Override
                    public void getObjectReference(@NonNull DataSnapshot dataSnapshot) {
                        int puntuacion = 0;
                        for (DataSnapshot dato : dataSnapshot.getChildren()) {
                                puntuacion += valor(dato.getValue().toString());

                        }
                        float convertir = 0;

                        if(dataSnapshot.getChildrenCount() != 0) {

                            convertir = puntuacion / dataSnapshot.getChildrenCount();
                        }

                        tv_puntua_valor.setText(convertir + "");
                        // tv_puntua_valor.setText(d + "");
                    }
                });

                ;

            }
        });

    }

    public int valor(String v) {
        int val = 0;

        switch (v) {
            case "uno":
                val = 1;
                break;

            case "dos":
                val = 2;
                break;

            case "tres":
                val = 3;
                break;
            case "cuatro":
                val = 4;
                break;

            case "cinco":
                val = 5;
                break;

        }
        return val;
    }

    @Override
    public void onStart() {
        super.onStart();
        listaFirebase.startList();
    }

    @Override
    public void onStop() {
        super.onStop();
        listaFirebase.stopList();
    }
}
