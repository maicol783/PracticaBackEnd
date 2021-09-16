package pragma.practica.back.cliente.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import pragma.practica.back.cliente.entidades.Cliente;
import pragma.practica.back.cliente.entidades.TipoIdentificacion;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    List<Cliente> findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion);

}
