package com.example.aprendizajeactivo.cristianparcial;

import android.support.annotation.NonNull;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAU {

    private static FirebaseAU firebaseAU;

    //Inicializacion de la autenticacion
    public static FirebaseAuth auth;

    //Inicializacion de la base de datos
    public static FirebaseDatabase database;

    //Incializacion de la clase y sus componentes principales
    public FirebaseAU(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public static void getIntance(){
        if(firebaseAU == null) {
            firebaseAU = new FirebaseAU();
        }
    }

    public static FirebaseUser getUser(){
        return auth.getCurrentUser();
    }

    public static String getUserUid(){
        return auth.getCurrentUser().getUid();
    }

    public static DatabaseReference getReferencia(){
        return database.getReference();
    }

    //Iniciar seccion con un usuario ya creado
    public static void loginWithEmailAndPassword(String email, String password, final DataUserValidation validation){

        if(email.equals("")&& email.contains("@")){

        }else if(password.equals("")){

        }else {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                validation.actionIsSuccessful(task);
                            } else {
                                validation.actionErrorException(task);
                            }
                        }
                    }
            );
        }
    }

    //Crear un nuevo usuario para iniciar la seccion
    public static void createWithEmailAndPassword(String email, String password, final DataUserValidation validation){

        if(email.equals("")&& email.contains("@")){

        }else if(password.equals("")){

        }else {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                validation.actionIsSuccessful(task);
                            } else {
                                validation.actionErrorException(task);
                            }
                        }
                    }
            );
        }
    }


    //Leer la base de datos por medio de un objeto de una ramma
    public static void readObjectReference(final DataObjectListener validation) {

        validation.getReferenceDataBase().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                validation.getObjectReference(dataSnapshot );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Leer la base de datos por medio de un objeto de una ramma
    public static void readObjectRealTime(final DataObjectListener validation) {

        validation.getReferenceDataBase().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                validation.getObjectReference(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Object convertObjectTo(@NonNull DataSnapshot dataSnapshot, Class clase){
        return dataSnapshot.getValue(clase);
    }


    public interface DataUserValidation{
        public void actionIsSuccessful(@NonNull Task<AuthResult> task);
        public void actionErrorException(@NonNull Task<AuthResult> task);
    }

    public interface DataObjectListener {
        public DatabaseReference getReferenceDataBase();
        public void getObjectReference(@NonNull DataSnapshot dataSnapshot);
    }


    /**
     * Metodos simplificados para mutiples acciones
     */



    //Crear un nuevo usuario para iniciar la seccion
    public static void createWithEmailAndPassword(EditText et_email, EditText et_password, final DataUserValidation validation){

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if(email.equals("")&& email.contains("@")){

        }else if(password.equals("")){

        }else {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                validation.actionIsSuccessful(task);
                            } else {
                                validation.actionErrorException(task);
                            }
                        }
                    }
            );
        }
    }


    //Iniciar seccion con un usuario ya creado
    public static void loginWithEmailAndPassword(EditText et_email, EditText et_password, final DataUserValidation validation){

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();


        if(email.equals("")&& email.contains("@")){

        }else if(password.equals("")){

        }else {

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                validation.actionIsSuccessful(task);
                            } else {
                                validation.actionErrorException(task);
                            }
                        }
                    }
            );
        }
    }

    public static void guardaObjeto(DatabaseReference ref, Object objeto){
        ref.push().setValue(objeto);
    }

    public static void guardaEnUidUsuario(DatabaseReference ref, Object objeto){
        ref.child(auth.getCurrentUser().getUid()).setValue(objeto);
    }

    public static int getCount(DataSnapshot dataSnapshot){
        return (int) dataSnapshot.getChildrenCount();
    }

    public static FirebaseAuth getAuth() {
        return auth;
    }

    public static FirebaseDatabase getDatabase() {
        return database;
    }
}
