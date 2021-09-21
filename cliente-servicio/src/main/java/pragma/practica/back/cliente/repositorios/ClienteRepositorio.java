package pragma.practica.back.cliente.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pragma.practica.back.cliente.entidades.Cliente;
import pragma.practica.back.cliente.entidades.TipoIdentificacion;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    List<Cliente> findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion);
    /*Cliente findByNumeroIdentificacion();*/
    @Query("Select c from Cliente c where c.edad>=?1")
    List<Cliente> findByEdad(int edad);


}
