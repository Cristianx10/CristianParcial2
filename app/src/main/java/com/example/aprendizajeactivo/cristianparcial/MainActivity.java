package com.example.aprendizajeactivo.cristianparcial;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity implements settingList{

    FirebaseAU fire;

    ListaFirebase<Pelicula> listaFirebase;

    private ListView lv_lista_opciones;

    private EditText et_nombre_peli;
    private Button btn_publicar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseReference reference;

        fire.getIntance();
        reference = fire.getReferencia().child(BaseValue.PELICULAS);

        lv_lista_opciones = findViewById(R.id.lv_lista_opciones);
        et_nombre_peli = findViewById(R.id.et_nombre_peli);
        btn_publicar = findViewById(R.id.btn_publicar);

        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_nombre_peli.getText().toString();
                if(name.equals("")) {
                    et_nombre_peli.setError("Por favor ingrese algo");
                }else{
                    Pelicula p = new Pelicula();
                    p.nombre = et_nombre_peli.getText().toString();
                    p.puntuacion = "0";
                    fire.guardaObjeto(reference, p);
                }
                et_nombre_peli.setText("");
            }
        });



        listaFirebase = new ListaFirebase<Pelicula> (new ListaFirebase.getVariables<Pelicula> () {
            @Override
            public ListView getViewListas() {
                return lv_lista_opciones;
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
                return R.layout.peliculas;
            }

            @Override
            public void populateView(@NonNull View v, @NonNull Pelicula model, final int position) {

                TextView tv_nombre = v.findViewById(R.id.tv_name_peli);

                Button btn_puntua_1 = v.findViewById(R.id.btn_puntua_1);
                Button btn_puntua_2 = v.findViewById(R.id.btn_puntua_2);
                Button btn_puntua_3 = v.findViewById(R.id.btn_puntua_3);
                Button btn_puntua_4 = v.findViewById(R.id.btn_puntua_4);
                Button btn_puntua_5 = v.findViewById(R.id.btn_puntua_5);

                tv_nombre.setText(model.nombre);


                btn_puntua_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaFirebase.getAdapter().getRef(position).child("puntua").push().setValue("uno");
                        iraResultados();
                    }
                });

                btn_puntua_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaFirebase.getAdapter().getRef(position).child("puntua").push().setValue("dos");
                        iraResultados();
                    }
                });

                btn_puntua_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaFirebase.getAdapter().getRef(position).child("puntua").push().setValue("tres");
                        iraResultados();
                    }
                });

                btn_puntua_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaFirebase.getAdapter().getRef(position).child("puntua").push().setValue("cuatro");
                        iraResultados();
                    }
                });

                btn_puntua_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listaFirebase.getAdapter().getRef(position).child("puntua").push().setValue("cinco");
                        iraResultados();
                    }
                });

            }
        });



    }

    public void iraResultados(){
        Intent intent = new Intent(MainActivity.this, Resultados.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        listaFirebase.startList();
    }

    @Override
    public void onStop() {
        listaFirebase.stopList();
        super.onStop();
    }
}
