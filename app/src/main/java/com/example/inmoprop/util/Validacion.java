package com.example.inmoprop.util;

import java.util.regex.Pattern;

public class Validacion {

    public static String usuario(String u){
        if(u.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un usuario.";
        }
        final Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        if(!EMAIL_REGEX.matcher(u).matches()){ // Comprueba que el usuario sea un eMail
            return "El usuario ingresado no es válido.";
        }
        return "";
    }
    public static String pass(String p){
        if(p.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar la contraseña.";
        }
        return "";
    }
    public static String id(String id){
        if(id.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Id no válido.";
        }
        final Pattern ID_REGEX = Pattern.compile("^[1-9]\\d*$");
        if(!ID_REGEX.matcher(id).matches()){ // Comprueba que el texto ingresado sea un ID (numero natural mayor a 0)
            return "Id no válido.";
        }
        return "";
    }
    public static String nombre(String n){
        if(n.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un nombre.";
        }
        return "";
    }
    public static String apellido(String a){
        if(a.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un apellido.";
        }
        return "";
    }
    public static String dni(String dni){
        if(dni.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un DNI.";
        }
        final Pattern DNI_REGEX = Pattern.compile("^\\d{7,8}$");
        if(!DNI_REGEX.matcher(dni).matches()){ // Comprueba que el texto ingresado sea un DNI (numero de 7 u 8 digitos)
            return "El DNI ingresado no es válido.";
        }
        return "";
    }
    public static String telefono(String t){
        if(t.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un teléfono de contacto.";
        }
        final Pattern TELEFONO_REGEX = Pattern.compile("^\\d{10}$");
        if(!TELEFONO_REGEX.matcher(t).matches()){ // Comprueba que el texto ingresado sea un telefono (numero de 10 digitos)
            return "El teléfono ingresado no es válido.";
        }
        return "";
    }
    public static String email(String u){
        if(u.isEmpty()){ // Comprueba que el usuario no este en blanco
            return "Debe ingresar un eMail.";
        }
        final Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        if(!EMAIL_REGEX.matcher(u).matches()){ // Comprueba que el texto ingresado sea un eMail
            return "El eMail ingresado no es válido.";
        }
        return "";
    }
}
