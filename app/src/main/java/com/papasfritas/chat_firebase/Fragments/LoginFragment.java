package com.papasfritas.chat_firebase.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.papasfritas.chat_firebase.MainActivity;
import com.papasfritas.chat_firebase.R;
import com.papasfritas.chat_firebase.Utilidades.Validaciones;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    TextInputLayout email,password;
    Button login;
    TextView recuperar;

    FirebaseAuth auth;

    Validaciones validaciones = new Validaciones();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login,container,false);
        init();
        return view;
    }

    public void init(){

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        recuperar = view.findViewById(R.id.recuperar);
        recuperar.setOnClickListener(this);

        login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                if(validaciones.comprobarCamposLogin(email,password)){
                    validaLogin(email.getEditText().getText().toString().toLowerCase().trim(), password.getEditText().getText().toString());
                }
                break;

            case R.id.recuperar:
                alertRecuperar();
                break;
        }
    }

    public void validaLogin(String email,String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Error de autenticación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void recuperarContrasena(String email){
        auth.setLanguageCode("es");

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(), "Correo enviado, revisa tu bandeja de entrada", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "El Correo no está registrado", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void alertRecuperar(){
        final android.app.AlertDialog alertContrasena;
        final android.app.AlertDialog.Builder correoRecuperar = new android.app.AlertDialog.Builder(getContext());


        View dialog = getLayoutInflater().inflate(R.layout.alert_rec_pass, null);

        final Button ok = dialog.findViewById(R.id.btnCerrar);
        final TextInputLayout correoRec = dialog.findViewById(R.id.email);
        correoRecuperar.setView(dialog);

        alertContrasena = correoRecuperar.create();
        alertContrasena.setCancelable(true);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!correoRec.getEditText().getText().toString().equals("")){
                    if(validaciones.comprobarCorreo(correoRec)){
                        alertContrasena.dismiss();
                        recuperarContrasena(correoRec.getEditText().getText().toString().toLowerCase().trim());
                    }
                }else{
                    correoRec.setError("El campo no puede estár vacío");
                }

            }
        });
        alertContrasena.show();
    }
}
