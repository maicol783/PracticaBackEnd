package pragma.practica.back.cliente.servicios;

import org.springframework.web.multipart.MultipartFile;
import pragma.practica.back.cliente.entidades.Cliente;
import pragma.practica.back.cliente.entidades.TipoIdentificacion;
import pragma.practica.back.cliente.modelos.Foto;

import java.util.List;

public interface ClienteServicio {
    List<Cliente> listAllClientes();
    Cliente getCliente(String numeroDocumento);
    Cliente createCliente(Cliente cliente);
    Cliente updateCiente(Cliente cliente);
    Cliente deleteCliente(String numeroDocumento);
    List<Cliente> ListByEdad(int edad);
    List<Cliente> findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion);
}
