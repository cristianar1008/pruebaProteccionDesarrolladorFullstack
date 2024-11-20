package ejemplobd.crud.Repository;

import ejemplobd.crud.Entities.Fibonacci;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FibonacciRepository extends JpaRepository<Fibonacci, Long> {
}

