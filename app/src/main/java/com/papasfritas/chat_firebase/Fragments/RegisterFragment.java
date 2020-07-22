package com.papasfritas.chat_firebase.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.papasfritas.chat_firebase.MainActivity;
import com.papasfritas.chat_firebase.R;
import com.papasfritas.chat_firebase.Utilidades.Validaciones;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{
    TextInputLayout usuario, password, email;
    Button registro;

    FirebaseAuth auth;
    DatabaseReference reference;

    Validaciones validaciones = new Validaciones();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        init();
        return view;
    }


    public void init(){
        usuario = view.findViewById(R.id.userName);
        password = view.findViewById(R.id.password);
        email = view.findViewById(R.id.email);

        registro = view.findViewById(R.id.btnRegistro);
        registro.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegistro:
                if(validaciones.comprobarCampos(email,password,usuario)) {
                    registro(usuario.getEditText().getText().toString(), email.getEditText().getText().toString().toLowerCase().trim(), password.getEditText().getText().toString());
                }
                break;
        }

    }

    public void registro(final String usuario,final String email,final String contrasena){
        auth.createUserWithEmailAndPassword(email,contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            String userId = user.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("userName", usuario);
                            hashMap.put("imageUrl", "default");
                            hashMap.put("Email", email);
                            hashMap.put("search", usuario.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else{
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), "No es posible registrar éste correo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
