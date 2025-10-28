package com.example.inmoprop.util;

import java.util.regex.Pattern;

public class Validacion {

    //-----------Validaciones del LOGIN-------------------------------------------------------------
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
        if(p.isEmpty()){ // Comprueba que la contraseña no este en blanco
            return "Debe ingresar la contraseña.";
        }
        return "";
    }

    //-----------Validaciones de PROPIETARIO--------------------------------------------------------
    public static String id(String id){
        if(id.isEmpty()){ // Comprueba que el ID no este en blanco
            return "Id no válido.";
        }
        final Pattern ID_REGEX = Pattern.compile("^[1-9]\\d*$");
        if(!ID_REGEX.matcher(id).matches()){ // Comprueba que el texto ingresado sea un ID (numero natural mayor a 0)
            return "Id no válido.";
        }
        return "";
    }
    public static String nombre(String n){
        if(n.isEmpty()){ // Comprueba que el nombre no este en blanco
            return "Debe ingresar un nombre.";
        }
        return "";
    }
    public static String apellido(String a){
        if(a.isEmpty()){ // Comprueba que el apellido no este en blanco
            return "Debe ingresar un apellido.";
        }
        return "";
    }
    public static String dni(String dni){
        if(dni.isEmpty()){ // Comprueba que el DNI no este en blanco
            return "Debe ingresar un DNI.";
        }
        final Pattern DNI_REGEX = Pattern.compile("^\\d{7,8}$");
        if(!DNI_REGEX.matcher(dni).matches()){ // Comprueba que el texto ingresado sea un DNI (número de 7 u 8 dígitos)
            return "El DNI ingresado no es válido.";
        }
        return "";
    }
    public static String telefono(String t){
        if(t.isEmpty()){ // Comprueba que el teléfono no este en blanco
            return "Debe ingresar un teléfono de contacto.";
        }
        final Pattern TELEFONO_REGEX = Pattern.compile("^\\d{10}$");
        if(!TELEFONO_REGEX.matcher(t).matches()){ // Comprueba que el texto ingresado sea un teléfono (numero de 10 digitos)
            return "El teléfono ingresado no es válido.";
        }
        return "";
    }
    public static String email(String u){
        if(u.isEmpty()){ // Comprueba que el eMail no este en blanco
            return "Debe ingresar un eMail.";
        }
        final Pattern EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+");
        if(!EMAIL_REGEX.matcher(u).matches()){ // Comprueba que el texto ingresado sea un eMail
            return "El eMail ingresado no es válido.";
        }
        return "";
    }

    //-----------Validaciones de INMUEBLE-----------------------------------------------------------
    public static String direccion(String d){
        if(d.isEmpty()){ // Comprueba que la dirección no este en blanco
            return "Debe ingresar la dirección del inmueble.";
        }
        return "";
    }
    public static String tipo(String t){
        if(t.isEmpty()){ // Comprueba que el tipo no este en blanco
            return "Debe ingresar el tipo de inmueble.";
        }
        return "";
    }
    public static String uso(String u){
        if(u.isEmpty()){ // Comprueba que el uso no este en blanco
            return "Debe ingresar el uso del inmueble.";
        }
        return "";
    }
    public static String superficie(String s){
        if(s.isEmpty()){ // Comprueba que la superficie no este en blanco
            return "Debe ingresar la superficie del inmueble.";
        }
        final Pattern ID_REGEX = Pattern.compile("^[1-9]\\d*$");
        if(!ID_REGEX.matcher(s).matches()){ // Comprueba que el texto ingresado sea una superficie (numero natural mayor a 0)
            return "La superficie ingresada no es válida.";
        }
        return "";
    }
    public static String ambientes(String a){
        if(a.isEmpty()){ // Comprueba que la cantidad de ambientes no este en blanco
            return "Debe ingresar la cantidad de ambientes.";
        }
        final Pattern ID_REGEX = Pattern.compile("^[1-9]\\d*$");
        if(!ID_REGEX.matcher(a).matches()){ // Comprueba que el texto ingresado sea una cantidad de ambientes (numero natural mayor a 0)
            return "La cantidad de ambientes ingresada no es válida.";
        }
        return "";
    }
    public static String precio(String p){
        if(p.isEmpty()){ // Comprueba que el precio no este en blanco
            return "Debe ingresar el precio de alquiler.";
        }
        final Pattern ID_REGEX = Pattern.compile("^(?:[1-9]\\d*(?:\\.\\d+)?|\\d*\\.\\d*[1-9]\\d*)$");
        if(!ID_REGEX.matcher(p).matches()){ // Comprueba que el texto ingresado sea un precio (numero decimal mayor a 0)
            return "El precio ingresado no es válido.";
        }
        return "";
    }
    public static String latitud(String l){
        if(l.isEmpty()){ // Comprueba que la latitud no este en blanco
            return "Debe ingresar la latitud del inmueble.";
        }
        final Pattern ID_REGEX = Pattern.compile("^-?(?:0|[1-9]\\d*)(?:\\.\\d+)?$");
        if(!ID_REGEX.matcher(l).matches()){ // Comprueba que el texto ingresado sea una latitud (numero decimal)
            return "La latitud ingresada no es válida.";
        }
        if(Double.parseDouble(l)>90){//Comprueba que la latitud sea menor o igual a 90°
            return "La máxima latitud válida es 90°.";
        }
        if(Double.parseDouble(l)<-90){//Comprueba que la latitud sea mayor o igual a -90°
            return "La mínima latitud válida es -90°.";
        }
        return "";
    }
    public static String longitud(String l){
        if(l.isEmpty()){ // Comprueba que la longitud no este en blanco
            return "Debe ingresar la longitud del inmueble.";
        }
        final Pattern ID_REGEX = Pattern.compile("^-?(?:0|[1-9]\\d*)(?:\\.\\d+)?$");
        if(!ID_REGEX.matcher(l).matches()){ // Comprueba que el texto ingresado sea una longitud (numero decimal)
            return "La longitud ingresada no es válida.";
        }
        if(Double.parseDouble(l)>180){//Comprueba que la latitud sea menor o igual a 180°
            return "La máxima longitud válida es 180°.";
        }
        if(Double.parseDouble(l)<-180){//Comprueba que la latitud sea mayor o igual a -180°
            return "La mínima longitud válida es -180°.";
        }
        return "";
    }
}
