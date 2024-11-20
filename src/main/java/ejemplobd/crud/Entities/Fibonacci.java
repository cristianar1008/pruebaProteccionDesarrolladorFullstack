package ejemplobd.crud.Entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fibonacci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time;
    private Long numberOne;
    private Long numberTwo;


    private Integer numberList;  // Este campo almacena el tamaño de la lista

    // Esto es para mapear la lista Fibonacci
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "fibonacci_value") // Cambio en el nombre de la columna
    private List<Long> listFibonacci = new ArrayList<>(); // Inicialización de la lista

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(Long numberOne) {
        this.numberOne = numberOne;
    }

    public Long getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(Long numberTwo) {
        this.numberTwo = numberTwo;
    }

    // Getter y setter para numberList
    public Integer getNumberList() {
        return numberList;
    }

    public void setNumberList(Integer numberList) {
        this.numberList = numberList;
    }

    // Método para acceder a la lista de Fibonacci
    public List<Long> getListFibonacci() {
        return listFibonacci;
    }

    // Establecer la lista de Fibonacci
    public void setListFibonacci(List<Long> listFibonacci) {
        this.listFibonacci = listFibonacci;
    }
}

