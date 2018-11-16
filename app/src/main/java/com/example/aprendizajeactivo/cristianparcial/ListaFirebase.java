package com.example.aprendizajeactivo.cristianparcial;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * 0. Copiart la dependencia en el Gradable
 *
 * //------------------------------------------
 *     //Estas son del firebase
 *     implementation 'com.google.firebase:firebase-auth:16.0.4'
 *     implementation 'com.google.firebase:firebase-database:16.0.3'
 *
 *     //Esta es la de esta clase
 *     implementation 'com.firebaseui:firebase-ui-database:4.2.1'
 *
 * //------------------------------------------
 *
 * 1. Inicializa en tu actividad la variable ListaFirebase
 *
 *
 * 2. Inicializala con la misma instancia
 *            ListaFirebase<Usuario> nombreVariable = new ListaFirebase.getVariables<>()
 *
 * 3. Implementa la interfaz settingList
 *          corrige los errores colocados con el super
 *          Y agregar los metodos startList y stopList
 *
 * 4.
 *
 */


public class ListaFirebase<T>{



    FirebaseDatabase database;
    ListView lista;

    //1. Crear adaptador
    FirebaseListAdapter adapter;

    DatabaseReference reference;

    public ListaFirebase(final getVariables<T> variables){
        this.lista = variables.getViewListas();
        Query ref = variables.getUbicacionBase();
        reference = (DatabaseReference) ref;

        FirebaseListOptions options = new FirebaseListOptions.Builder()
                .setLayout(variables.getLayoutList())
                .setQuery(ref, variables.getClaseModelo())
                .build();



        adapter = new FirebaseListAdapter<T>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull T model, int position) {

                variables.populateView(v, model, position);

            }
        };

        lista.setAdapter(adapter);
    }

    public void agregarObjetoAList(Object o){
        reference.push().setValue(o);
    }

    public void eliminarObjetoAList(int posicion){
        adapter.getRef(posicion).removeValue();
    }

    public DatabaseReference obtenerObjetoAList(int posicion){
        return adapter.getRef(posicion);
    }





    public interface getVariables<T>{
        public ListView getViewListas();
        public Query getUbicacionBase();
        public Class getClaseModelo();
        public int getLayoutList();
        public void populateView(@NonNull View v, @NonNull T model, int position);
        /*

        Debe implementar la interfaz settings o escribir los metodos manualmente

         */
    }


    public void startList(){
        adapter.startListening();
    }
    public void stopList(){
        adapter.startListening();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public ListView getLista() {
        return lista;
    }

    public FirebaseListAdapter getAdapter() {
        return adapter;
    }

    public DatabaseReference getReference() {
        return reference;
    }
}




interface settingList{
    void onStart();
    void onStop();
}
