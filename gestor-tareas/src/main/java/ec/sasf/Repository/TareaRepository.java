package ec.sasf.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ec.sasf.Enums.Estado;
import ec.sasf.Modelo.Tarea;

public class TareaRepository implements ITareaRepository {

  private List<Tarea> tareasRegistradas;

  public TareaRepository(){
    this.tareasRegistradas = new ArrayList<>();
  }

  /*
   * Método que agrega un nuevo elemento a la colección
   */
  @Override
  public void agregarTarea(Tarea tarea) {
    tareasRegistradas.add(tarea);
  }

  /*
   * Modifica todos los atributos del primer elemento que cumpla con la condición establecida.
   */
  @Override
  public void editarTarea(Integer id, Tarea tarea) {
    tareasRegistradas.stream()
        .filter(t -> t.getId().equals(id))
        .findFirst()
        .ifPresent(t -> {
            t.setTitulo(tarea.getTitulo());
            t.setDescripcion(tarea.getDescripcion());
            t.setEstado(tarea.getEstado());
            t.setFechaVencimiento(tarea.getFechaVencimiento());
            t.setPrioridad(tarea.getPrioridad());
        });
  }

  /*
   * Método que elimina un elemento de la colección
   */
  @Override
  public void eliminarTarea(Tarea tarea) {
    tareasRegistradas.remove(tarea);
  }

  /*
   * Retorna colección con las tareas registradas.
   */
  @Override
  public List<Tarea> listarTareas() {
    return tareasRegistradas;
  }

  /*
   * Modifica el estado del primer elemento que cumpla con la condición establecida.
   */
  @Override
  public void marcarTarea(Integer id, Estado estado) {
    tareasRegistradas.stream()
      .filter(t -> t.getId().equals(id))
      .findFirst()
      .ifPresent(t -> {
          t.setEstado(estado);
      });
  }

  /*
   * Retorna una colección con los elementos ordenados de mayor a menor según su fecha de vencimiento
   */
  @Override
  public List<Tarea> ordenarTareasPorFecha() {
    return tareasRegistradas.stream()
        .sorted(Comparator.comparing(Tarea::getFechaVencimiento).reversed())
        .collect(Collectors.toList());
  }

  /*
   * Retorna una colección con los elementos ordenados de mayor a menor según su Prioridad
   */
  @Override
  public List<Tarea> ordenarTareasPorPrioridad() {
    return tareasRegistradas.stream()
        .sorted(Comparator.comparing(Tarea::getPrioridad).reversed())
        .collect(Collectors.toList());      
  }


  /*
   * Retorna una colección donde los elementos cumplan con el estado enviado como parámetro
   */
  @Override
  public List<Tarea> filtrarTareas(Estado estado) {
    return  tareasRegistradas.stream()
        .filter(t-> t.getEstado().equals(estado))
        .collect(Collectors.toList());
  }

  /*
   * Busca en la colección si existe algún elemento que cumpla el predicado enviado 
   * y retorna el primero que cumpla con el mismo
   */
  @Override
  public Optional<Tarea> obtenerTareaById(Integer id) {
    return tareasRegistradas.stream()
        .filter(t -> t.getId().equals(id))
        .findFirst();
  }
  /*
   * Retorna el tamano de la colección correspondiente a tareas.
   */
  @Override
  public Integer obtenerTamanoArreglo(){
    return tareasRegistradas.size();
  }
  
}
