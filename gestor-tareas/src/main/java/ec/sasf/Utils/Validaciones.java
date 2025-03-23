package ec.sasf.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import ec.sasf.Enums.Estado;
import ec.sasf.Enums.Prioridad;

public class Validaciones {
    
  /*
   * Recibe un input(Sc.nextLine) e intenta parsear el valor a una variable de tipo Integer.
   */
  public static Integer validarNumero(String numero){    
    try {
      Integer num = Integer.parseInt(numero);
      if (num > 0) {
          return num;
      }
      System.out.println("-------> El número ingresado debe ser mayor a cero");
      return null;
    } catch (NumberFormatException e) {
      System.out.println("-------> Debe ingresar un valor númerico!");
      return null;
    }
  }

  /*
   * Valida que el input(Sc.nextLine) sea diferente a vacio/nulo
   */
  public static String validarString(String input) {
    try {
      if (input == null || input.trim().isEmpty()) {
          throw new IllegalArgumentException("-------> El valor ingresado no puede ser vacío o nulo.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return null;  
    }
    return input;
  }


  /*
   * Recibe un input(Sc.nextLine) e intenta parsear el valor a una variable de tipo LocalDate.
   */
  public static LocalDate validarFecha(String input){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    try {
      return LocalDate.parse(input, formatter);
    } catch (DateTimeParseException  e) {
      System.out.println("-------> La fecha ingresada es incorrecta! Ingrese la fecha siguiendo el siguiente formato dd-mm-yyyy");
      return null;
    }
  }
  
  /*
   * Valida que el input(Sc.nextLine) pertenezca a uno de los valores asignados al enum PRIORIDAD.
   */
  public static Prioridad validarPrioridad(String input){
    //String prioridad = validarString(input);
    try {
      return Prioridad.valueOf(input.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("-------> Las prioridades aceptadas son (BAJA, MEDIA, ALTA)");
      return null;
    }
  }

  /*
   * Valida que el input(Sc.nextLine) pertenezca a uno de los valores asignados al enum ESTADO.
   */
  public static Estado validarEstado(String input){
    //String prioridad = validarString(input);
    try {
      return Estado.valueOf(input.toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println("-------> Los estados aceptados son (PENDIENTE, COMPLETADO)");
      return null;
    }
  }
}
