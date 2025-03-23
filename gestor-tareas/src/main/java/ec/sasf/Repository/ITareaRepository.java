package ec.sasf.Repository;

import java.util.List;
import java.util.Optional;

import ec.sasf.Enums.Estado;
import ec.sasf.Modelo.Tarea;

public interface ITareaRepository {
    
    void agregarTarea(Tarea tarea);
    void editarTarea(Integer id, Tarea tarea);
    void eliminarTarea(Tarea tarea);
    List<Tarea>  listarTareas();
    void marcarTarea(Integer id, Estado estado);
    List<Tarea> ordenarTareasPorFecha();
    List<Tarea> ordenarTareasPorPrioridad();
    List<Tarea> filtrarTareas(Estado estadoTarea);
    Optional<Tarea> obtenerTareaById(Integer id);
    Integer obtenerTamanoArreglo();

}
