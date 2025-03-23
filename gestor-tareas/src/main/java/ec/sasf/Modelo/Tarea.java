package ec.sasf.Modelo;

import java.time.LocalDate;

import ec.sasf.Enums.Estado;
import ec.sasf.Enums.Prioridad;
import lombok.Data;

@Data
public class Tarea {

	private Integer id;
	private String titulo;
	private String descripcion;
	private LocalDate fechaVencimiento;
	private Prioridad prioridad;
	private Estado estado;
    
	//Constructor vacio para definir el estado inicial de la tarea
	public Tarea(){
		this.estado = Estado.PENDIENTE;
	}

	public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento, Prioridad prioridad) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaVencimiento = fechaVencimiento;
		this.prioridad = prioridad;
		this.estado = Estado.PENDIENTE;
	}



	@Override
	public String toString() {
		return "Tarea con " + "ID  " + id + ": titulo= " + titulo + ", descripcion= " + descripcion + 
				", fechaVencimiento= " + fechaVencimiento + ", prioridad= " + prioridad + ", estado= " + estado;
	}

}
