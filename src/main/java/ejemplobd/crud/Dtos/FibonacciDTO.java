package ejemplobd.crud.Dtos;

import javax.validation.constraints.Pattern;

public class FibonacciDTO {
    // Validación para asegurar que el formato sea HH:mm:ss
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$", message = "El formato de hora debe ser HH:mm:ss")
    private String time;

    // Getters y Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
