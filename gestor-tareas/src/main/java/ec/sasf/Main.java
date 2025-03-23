package ec.sasf;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

import ec.sasf.Enums.Estado;
import ec.sasf.Enums.Prioridad;
import ec.sasf.Modelo.Tarea;
import ec.sasf.Repository.ITareaRepository;
import ec.sasf.Repository.TareaRepository;
import ec.sasf.Service.TareaService;
import ec.sasf.Utils.Validaciones;

public class Main {

  private static ITareaRepository tareaRepository = new TareaRepository();
  private static TareaService tareaService = new TareaService(tareaRepository);
  public  static Scanner sc = new Scanner(System.in);


  public static void main(String[] args) {
    System.out.println("\n!Bienvenido al Sistema Gestor de Tareas!");
    ObtenerOpcion();
  
  }


  private static void ObtenerOpcion(){

    Integer opcionMenu = null;
    try {
      do {
        MostrarOpciones();
        System.out.println("Ingrese una opción:");
        String input = sc.nextLine();
        opcionMenu = Validaciones.validarNumero(input);
        if (opcionMenu== null)
          continue;
        switch(opcionMenu){
          case 1:
            agregarTarea();
            break;
          case 2:
            editarTareas();
            break;
          case 3:
            eliminarTarea();
            break;
          case 4:
            tareaService.listarTareas();
            break;
          case 5:
            marcarTarea();
            break;
          case 6:
            ordenarTareas();
            break;
          case 7:
            filtrarTareas();
            break;
          case 8:
            System.out.println("-------> Finalizando ejecución de programa...");
            break;
          default:
            System.out.println("-------> Opción no disponible!");
        }
      } while (opcionMenu == null || opcionMenu != 8);
    } catch (Exception  e) {
      System.out.println("La ejecución del programa fue interrumpida...!");
    } finally {
      // Al finalizar la ejecución del programa cerrar el scanner para evitar fuga de recursos 
      sc.close();
    }
  }


  private static void agregarTarea(){
    Tarea tarea = new Tarea();
    tarea.setTitulo(obtenerInput("Ingrese el titulo de la tarea"));
    tarea.setDescripcion(obtenerInput("Ingrese la descripción de la tarea"));
    tarea.setFechaVencimiento(obtenerFecha("Ingrese la fecha de vencimiento de la tarea"));
    tarea.setPrioridad(obtenerPrioridad("Ingrese la prioridad de la tarea"));
    tareaService.agregarTarea(tarea);
  }

  private static void editarTareas(){
    System.out.println("Ingrese el ID de la tarea a editar:");
    Integer numero;
      do {
          String input = sc.nextLine();
          numero = Validaciones.validarNumero(input);
      } while (numero == null); 
    //Antes de pedir mayor información sobre la tarea, validamos que exista un registro con el ID indicado.
    Tarea tarea = tareaService.obtenerTareaById(numero).orElse(null);
    if (!tarea.equals(null)) {
      //Si la tarea existe, se procede a pedir por consola los valores actuales de la tarea y se realiza su respectiva validación.
      tarea.setTitulo(obtenerInput("Ingrese el titulo de la tarea"));
      tarea.setDescripcion(obtenerInput("Ingrese la descripción de la tarea"));
      tarea.setFechaVencimiento(obtenerFecha("Ingrese la fecha de vencimiento de la tarea"));
      tarea.setPrioridad(obtenerPrioridad("Ingrese la prioridad de la tarea"));
      tarea.setEstado(obtenerEstado("Ingrese el estado de la tarea"));
      tareaService.editarTarea(numero, tarea);
      return;
    }
    System.out.println("-------> No existe tarea con el ID " + numero + " registrado");
  }

  private static void eliminarTarea(){

    System.out.println("Ingrese el ID de la tarea a eliminar:");
    Integer numero;
    //Validamos que el input ingresado sea correspondiente a un número
      do {
          String input = sc.nextLine();
          numero = Validaciones.validarNumero(input);
      } while (numero == null); 
      tareaService.eliminarTarea(numero);
  }


  private static void marcarTarea(){
    System.out.println("Ingrese el ID de la tarea que desea marcar como completada:");
    Integer numero;
      do {
          String input = sc.nextLine();
          numero = Validaciones.validarNumero(input);
      } while (numero == null); 
    
      tareaService.marcarTarea(numero, Estado.COMPLETADA);
  }

  private static void ordenarTareas(){
    Integer tipoOrder = null;
    do {
      System.out.println("1. Ordenar tareas por fecha de vencimiento");
      System.out.println("2. Ordenar tareas por prioridad");
      System.out.println("Ingrese una opción:");

      String input = sc.nextLine();
      tipoOrder = Validaciones.validarNumero(input);  
      if (tipoOrder!=null && (tipoOrder != 1 && tipoOrder != 2)) {
          System.out.println("-------> Opción no válida, por favor ingrese 1 o 2.");
      }
    } while (tipoOrder == null || tipoOrder != 1 && tipoOrder != 2);  
    tareaService.ordenarTareas(tipoOrder);
  }

  /**
   * Metodo que muestra las opciones disponibles para realizar filtrado sobre la colección. 
   */
  private static void filtrarTareas(){
    Integer tipoFiltrado = null;
    do {
      System.out.println("1. Filtrar por tareas pendientes");
      System.out.println("2. Filtrar por tareas completadas");
      System.out.println("Ingrese una opción:");

      String input = sc.nextLine();
      tipoFiltrado = Validaciones.validarNumero(input); 
      // Valida que la opción ingresada se encuentre entre los valores de 1 y 2 
      if (tipoFiltrado!=null && (tipoFiltrado != 1 && tipoFiltrado != 2)) {
          System.out.println("-------> Opción no válida, por favor ingrese 1 o 2.");
      }
    } while (tipoFiltrado == null || tipoFiltrado != 1 && tipoFiltrado != 2);  
    tareaService.filtrarTareas(tipoFiltrado == 1 ? Estado.PENDIENTE : Estado.COMPLETADA);
  }


  private static void MostrarOpciones(){
    System.out.println("************************************");
    System.out.println("1. Agregar Tarea");
    System.out.println("2. Editar Tarea");
    System.out.println("3. Eliminar Tarea");
    System.out.println("4. Listar todas las Tareas");
    System.out.println("5. Marcar Tarea como completada");
    System.out.println("6. Ordenar Tareas");
    System.out.println("7. Filtrar Tareas");
    System.out.println("8. Salir del Programa");
    System.out.println("************************************");
  }


  private static String obtenerInput(String mensajeInput){
    String input = null;
    do {
      System.out.println(mensajeInput);
      input = Validaciones.validarString(sc.nextLine());
    } while (input==null);
    return input;
  }

  private static LocalDate obtenerFecha(String mensajeInput){
    LocalDate input = null;
    do {
      System.out.println(mensajeInput);
      input = Validaciones.validarFecha(sc.nextLine());
    } while (input == null);
    return input;
  }

  private static Prioridad obtenerPrioridad(String mensaje){
    Prioridad prioridad = null;
    do {
      System.out.println(mensaje);
      prioridad = Validaciones.validarPrioridad(sc.nextLine());
    }while (prioridad == null);
    return prioridad;
  }

  private static Estado obtenerEstado(String mensaje){
    Estado prioridad = null;
    do {
      System.out.println(mensaje);
      prioridad = Validaciones.validarEstado(sc.nextLine());
    }while (prioridad == null);
    return prioridad;
  }
}