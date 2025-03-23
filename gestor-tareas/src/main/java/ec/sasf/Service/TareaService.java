package ec.sasf.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import ec.sasf.Enums.Estado;
import ec.sasf.Modelo.Tarea;
import ec.sasf.Repository.ITareaRepository;

public class TareaService {
    
    private ITareaRepository tareaRepository;

    public TareaService(ITareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
    }


    public void agregarTarea(Tarea tarea) {
        tarea.setId(generateIdTarea());
        this.tareaRepository.agregarTarea(tarea);
        System.out.println("-------> Tarea fue agregada exitosamente con ID " + tarea.getId());
    }

    /*
     * Método que devuelve la tarea asociada al id enviado por parámetro
     */
    public Optional<Tarea> obtenerTareaById(Integer id){
        return this.tareaRepository.obtenerTareaById(id);
    }


    public void editarTarea(Integer id, Tarea tarea){
        // Validamos que el id que se intenta modificar exista dentro de la colección
        Optional<Tarea> tsk = this.tareaRepository.obtenerTareaById(id);
        if(tsk.isPresent()){
            this.tareaRepository.editarTarea(id, tarea);
            System.out.println("-------> Tarea con ID " + id + " actualizada exitosamente");
            return;
        }
        System.out.println("-------> No existe tarea registrada con el ID " + id );
    }

    /*
     * Método que verifica si un elemento existe y si esto se cumple,
     *  lo elimina de la colección a través de la llamada al repository.
     */
    public void eliminarTarea(Integer id){
        Optional<Tarea> tsk = this.tareaRepository.obtenerTareaById(id);
        if(tsk.isPresent()){
            this.tareaRepository.eliminarTarea(tsk.get());
            System.out.println("-------> Tarea con ID " + id + " eliminada exitosamente");
            return;
        }
        System.out.println("-------> No existe tarea registrada con el ID " + id );
    }

    public void listarTareas(){
        List<Tarea> tareas = this.tareaRepository.listarTareas();
        if (!tareas.isEmpty()){
            tareas.stream().forEach(System.out::println);
            return;
        }
        System.out.println("-------> No existen tareas registradas en este momento!");
    }

    /*
     * Método que valida la existencia del elemento en la colección y si existe cambia su estado.
     */
    public void marcarTarea(Integer id, Estado estado){
        Optional<Tarea> tsk = this.tareaRepository.obtenerTareaById(id);
        if(tsk.isPresent()){
            this.tareaRepository.marcarTarea(id, estado);
            System.out.println("-------> Tarea con ID " + id + " fue marcada como " + estado);
            return;
        }
        System.out.println("-------> No existe tarea registrada con el ID " + id );
    }

    /**
     * Método que ordena las tareas según la opción ingresada por el usuario.
     * @param tipoOrder
     */
    public void ordenarTareas(Integer tipoOrder){
        //Antes de ordenar los registros, se valida que existan tareas almacenadas en la colección.
        if (this.tareaRepository.obtenerTamanoArreglo().equals(0)){
            System.out.println("-------> Al momento no existen tareas registradas!");
            return;
        }
        if(tipoOrder==1){
            System.out.println("  ---Tareas ordenadas por Fecha de Vencimiento---");
            this.tareaRepository.ordenarTareasPorFecha().forEach(System.out::println);
            return;
        }
        System.out.println("  ---Tareas ordenadas por Prioridad---");
        this.tareaRepository.ordenarTareasPorPrioridad().forEach(System.out::println);
    }

    /*
     * Método que realiza un filtrado sobre la colección dependiendo del estado pasado como parámetro y
     * muestra mensaje informativo si el resultado no contiene ningún elemento.  
     */
    public void filtrarTareas(Estado estadoTarea){
        List<Tarea> tareas = this.tareaRepository.filtrarTareas(estadoTarea);
        if (tareas.isEmpty()){
            System.out.println("-------> No existen tareas registradas con el estado " + estadoTarea);
            return;
        }
        tareas.stream().forEach(System.out::println);
    }

    /*
     * Metodo privado que genera un id aleatorio y valida si ya existe dentro del arreglo.
     */
    private Integer generateIdTarea(){
        Integer newId;
        Random random = new Random();
        do {
            newId = random.nextInt(200);
        }while(this.tareaRepository.obtenerTareaById(newId).isPresent());
        return newId;
    }
  
}
