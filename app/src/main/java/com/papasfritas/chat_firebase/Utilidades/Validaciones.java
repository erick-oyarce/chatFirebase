package com.papasfritas.chat_firebase.Utilidades;

import android.util.Patterns;

import com.google.android.material.textfield.TextInputLayout;
import com.papasfritas.chat_firebase.MainActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Validaciones {

    public boolean validaCorreo(String email){
        if(!Patterns.EMAIL_ADDRESS.matcher(email.trim().toLowerCase()).matches()){
            return false;
        }else{
            return true;
        }
    }

    public boolean comprobarCampos(TextInputLayout correo, TextInputLayout contrasena, TextInputLayout usuario){
        boolean valido = true;
        if(!validaCorreo(correo.getEditText().getText().toString())) {
            valido = false;
            correo.setError("Correo Inválido");
            correo.setFocusable(true);
        }
        if(contrasena.getEditText().getText().toString().length() < 5) {
            valido = false;
            contrasena.setError("La contraseña es muy corta");
            contrasena.setFocusable(true);
        }
        if(usuario.getEditText().getText().toString().equals("")){
            valido = false;
            usuario.setError("El usuario es requerido");
            usuario.setFocusable(true);
        }
        return valido;
    }

    public boolean comprobarCamposLogin(TextInputLayout correo, TextInputLayout contrasena){
        boolean valido = true;
        if(!validaCorreo(correo.getEditText().getText().toString())) {
            valido = false;
            correo.setError("Correo Inválido");
            correo.setFocusable(true);
        }
        if(contrasena.getEditText().getText().toString().length() < 5) {
            valido = false;
            contrasena.setError("La contraseña es muy corta");
            contrasena.setFocusable(true);
        }
        return valido;
    }

    public boolean comprobarCorreo(TextInputLayout correo){
        boolean valido = true;
        if(!validaCorreo(correo.getEditText().getText().toString())) {
            valido = false;
            correo.setError("Correo Inválido");
            correo.setFocusable(true);
        }
        return valido;
    }

}
