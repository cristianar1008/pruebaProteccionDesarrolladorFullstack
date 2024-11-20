package ejemplobd.crud.Controllers;

import ejemplobd.crud.Dtos.FibonacciDTO;
import ejemplobd.crud.Entities.Fibonacci;
import ejemplobd.crud.Services.FibonacciService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/fibonacci")
@Tag(name = "Fibonacci", description = "Operaciones relacionadas con la secuencia de Fibonacci")
public class FibonacciController {

    @Autowired
    private FibonacciService fibonacciService;

    /**
     * Genera automáticamente una lista de Fibonacci.
     * @return El objeto Fibonacci generado.
     */
    @GetMapping
    @Operation(summary = "Genera automáticamente una secuencia de Fibonacci",
            description = "Este endpoint genera una secuencia de Fibonacci basada en valores predeterminados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia de Fibonacci generada correctamente."),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor al generar la secuencia de Fibonacci.")
    })
    public ResponseEntity<Object> autoGenerateList() {
        try {
            Fibonacci fibonacci = fibonacciService.getFibonacci();
            return ResponseEntity.ok().body(new ApiResponseMessage("Secuencia de Fibonacci generada correctamente", fibonacci));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponseMessage("Error interno en el servidor al generar la secuencia de Fibonacci", null));
        }
    }

    /**
     * Obtiene una lista de todos los objetos Fibonacci almacenados.
     * @return Lista de objetos Fibonacci.
     */
    @GetMapping("/list")
    @Operation(summary = "Obtiene la lista completa de Fibonacci",
            description = "Este endpoint retorna todos los objetos Fibonacci almacenados en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Fibonacci obtenida correctamente."),
            @ApiResponse(responseCode = "404", description = "No se encontraron elementos de Fibonacci en la base de datos."),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor al obtener la lista de Fibonacci.")
    })
    public ResponseEntity<Object> getList() {
        try {
            List<Fibonacci> fibonacciList = fibonacciService.getList();
            if (fibonacciList.isEmpty()) {
                return ResponseEntity.status(404).body(new ApiResponseMessage("No se encontraron elementos de Fibonacci en la base de datos", null));
            }
            return ResponseEntity.ok().body(new ApiResponseMessage("Lista de Fibonacci obtenida correctamente", fibonacciList));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponseMessage("Error interno en el servidor al obtener la lista de Fibonacci", null));
        }
    }

    /**
     * Genera una secuencia de Fibonacci basada en la hora proporcionada.
     * @param fibonacciRequestDTO Contiene la hora como cadena de texto.
     * @return El objeto Fibonacci generado.
     */
    @PostMapping
    @Operation(summary = "Genera una secuencia de Fibonacci a partir de la hora proporcionada",
            description = "Este endpoint genera una secuencia de Fibonacci basada en la hora proporcionada en el cuerpo de la solicitud.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia de Fibonacci generada correctamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, probablemente por un formato de hora inválido."),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor al generar la secuencia de Fibonacci.")
    })
    public ResponseEntity<Object> generateFibonacci(
            @Valid @RequestBody
            @Parameter(description = "Datos de la solicitud que contienen la hora para generar la secuencia de Fibonacci.")
            FibonacciDTO fibonacciRequestDTO) {
        try {
            // Validar que los números en la hora no sean negativos
            if (fibonacciRequestDTO.getTime() == null || !isValidTime(fibonacciRequestDTO.getTime())) {
                return ResponseEntity.status(400).body(new ApiResponseMessage("Hora inválida o negativa", null));
            }

            Fibonacci fibonacci = fibonacciService.getGenerateFibonacci(fibonacciRequestDTO.getTime());
            return ResponseEntity.ok().body(new ApiResponseMessage("Secuencia de Fibonacci generada correctamente", fibonacci));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ApiResponseMessage("Solicitud incorrecta, probablemente por un formato de hora inválido", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponseMessage("Error interno en el servidor al generar la secuencia de Fibonacci", null));
        }
    }

    // Método para validar que la hora no sea negativa
    private boolean isValidTime(String time) {
        try {
            LocalTime parsedTime = LocalTime.parse(time);
            return parsedTime.getHour() >= 0 && parsedTime.getMinute() >= 0 && parsedTime.getSecond() >= 0;
        } catch (Exception e) {
            return false; // Si la hora no es válida, se devuelve false
        }
    }

    // Clase para envolver la respuesta
    public static class ApiResponseMessage {
        private String message;
        private Object data;

        public ApiResponseMessage(String message, Object data) {
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public Object getData() {
            return data;
        }
    }
}
