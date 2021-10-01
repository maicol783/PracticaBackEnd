package pragma.practica.back.cliente.servicios;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pragma.practica.back.cliente.clientes.ClienteFoto;
import pragma.practica.back.cliente.entidades.Cliente;
import pragma.practica.back.cliente.entidades.TipoIdentificacion;
import pragma.practica.back.cliente.modelos.Foto;
import pragma.practica.back.cliente.repositorios.ClienteRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServicioImp implements ClienteServicio{

    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    ClienteFoto clienteFoto;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<Cliente> listAllClientes() {
        return clienteRepositorio.findAll();
    }

    @Override
    public Cliente getCliente(String numeroDocumento) {
        Cliente cliente = clienteRepositorio.findById(numeroDocumento).orElse(null);
        return cliente;
    }

    @Transactional
    @Override
    public Cliente createCliente(Cliente cliente) {
        if (cliente.getFotoT() != null){
            ResponseEntity<Foto> response = clienteFoto.createFoto(cliente.getFotoT());
            Foto foto = modelMapper.map(response.getBody(), Foto.class);
            cliente.setFoto(foto.getId());
        }

        cliente.setEstado("CREATED");
        return clienteRepositorio.save(cliente);


    }


    @Override
    public Cliente updateCiente(Cliente cliente) {
        Cliente clienteDB = getCliente(cliente.getNumeroIdentificacion());
        if(clienteDB == null){
            return null;
        }
        clienteDB.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        clienteDB.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteDB.setNombres(cliente.getNombres());
        clienteDB.setApellidos(cliente.getApellidos());
        clienteDB.setEdad(cliente.getEdad());
        clienteDB.setCiudad(cliente.getCiudad());
        clienteDB.setFoto(cliente.getFoto());
        return clienteRepositorio.save(clienteDB);
    }

    @Override
    public  Cliente deleteCliente(String numeroDocumento) {
        Cliente clienteDB = getCliente(numeroDocumento);
        if (clienteDB == null){
            return null;
        }
        clienteDB.setEstado("DELETED");
        return clienteRepositorio.save(clienteDB);
    }

    @Override
    public List<Cliente> ListByEdad(int edad) {
        return clienteRepositorio.findByEdad(edad);
    }

    @Override
    public List<Cliente> findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion) {
        return clienteRepositorio.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
    }

}
