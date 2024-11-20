package ejemplobd.crud.Services;

import ejemplobd.crud.Entities.Fibonacci;
import ejemplobd.crud.Repository.FibonacciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FibonacciService {

    @Autowired
    private FibonacciRepository fibonacciRepository;

    @Autowired
    private EmailService emailService;  // Inyección del servicio de correo

    // Método para obtener y guardar el objeto Fibonacci en la base de datos
    public Fibonacci getFibonacci() {
        LocalTime now = LocalTime.now();
        // Crea el objeto Fibonacci basado en la hora actual
        Fibonacci fibonacci = createFibonacciFromCurrentTime(now);
        // Guarda el objeto Fibonacci en la base de datos
        fibonacciRepository.save(fibonacci);

        return fibonacci;
    }

    // Método para generar la secuencia de Fibonacci en base a la hora proporcionada
    public Fibonacci getGenerateFibonacci(String time) {
        LocalTime now = LocalTime.parse(time);
        Fibonacci fibonacci = createFibonacciFromCurrentTime(now);
        // Guarda el objeto Fibonacci en la base de datos
        fibonacciRepository.save(fibonacci);

        // Enviar correo con la secuencia generada
        emailService.sendFibonacciEmail("cparra@esri.co", fibonacci.getListFibonacci().toString());

        return fibonacci;
    }

    // Método para obtener una lista de Fibonacci almacenada en la base de datos
    public List<Fibonacci> getList() {
        return fibonacciRepository.findAll();
    }

    // Método para crear un objeto Fibonacci basado en la hora actual
    public Fibonacci createFibonacciFromCurrentTime(LocalTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeString = now.format(formatter);

        Long seedOne = (long) (now.getMinute() / 10); // Primera semilla: decenas de los minutos
        Long seedTwo = (long) (now.getMinute() % 10); // Segunda semilla: unidades de los minutos
        int numberList = now.getSecond();   // Longitud de la lista basada en los segundos

        // Organiza las semillas para que la menor sea el `seedOne` y la mayor el `seedTwo`
        if (seedOne > seedTwo) {
            Long temp = seedOne;
            seedOne = seedTwo;
            seedTwo = temp;
        }

        // Genera la lista de Fibonacci
        ArrayList<Long> fibonacciList = generateFibonacci(seedOne, seedTwo, numberList);

        // Crea un objeto Fibonacci y le asigna los valores
        Fibonacci fibonacci = new Fibonacci();
        fibonacci.setTime(timeString);
        fibonacci.setNumberOne(seedOne);
        fibonacci.setNumberTwo(seedTwo);
        fibonacci.setNumberList(numberList); // Asignar el valor de numberList
        fibonacci.setListFibonacci(fibonacciList);

        return fibonacci;
    }

    // Método para generar la secuencia de Fibonacci con semillas y tamaño
    private ArrayList<Long> generateFibonacci(Long seedOne, Long seedTwo, int count) {
        ArrayList<Long> fibonacci = new ArrayList<>();
        if (count <= 0) return fibonacci; // Retorna lista vacía si el conteo es inválido

        // Inicializa la secuencia con las semillas ordenadas
        fibonacci.add(seedOne);
        if (count == 1) return fibonacci;

        fibonacci.add(seedTwo);
        for (int i = 2; i < count; i++) {
            fibonacci.add(fibonacci.get(i - 1) + fibonacci.get(i - 2));
        }

        return fibonacci;
    }
}
