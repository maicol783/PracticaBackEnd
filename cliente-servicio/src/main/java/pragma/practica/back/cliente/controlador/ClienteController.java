package pragma.practica.back.cliente.controlador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pragma.practica.back.cliente.controlador.errores.ErrorMensaje;
import pragma.practica.back.cliente.entidades.Cliente;
import pragma.practica.back.cliente.entidades.TipoIdentificacion;
import pragma.practica.back.cliente.servicios.ClienteServicio;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
@Slf4j
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping
    public ResponseEntity<List<Cliente>> listClientes(@RequestParam(name = "documentoId", required = false) Long documentoId, @RequestParam(name = "numeroDocumento", required = false) String numeroDocumento){
        List<Cliente> clientes = new ArrayList<>();
        if (documentoId == null || numeroDocumento == null){
            clientes = clienteServicio.listAllClientes();
            if(clientes.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            clientes = clienteServicio.findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion.builder().id(documentoId).build(),numeroDocumento);
            if(clientes.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@Valid  @RequestBody Cliente cliente, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatoMensaje(result));
        }
        Cliente clienteCreate = clienteServicio.createCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreate);

    }

    @PutMapping(value = "/{numeroCedula}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable("numeroCedula") String numeroCedula, @RequestBody Cliente cliente){
        cliente.setNumeroIdentificacion(numeroCedula);
        Cliente clienteDB = clienteServicio.updateCiente(cliente);


        if (clienteDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDB);

    }

    @DeleteMapping(value = "/{numeroCedula}")
    public ResponseEntity<Cliente> deleteCliente(@PathVariable("numeroCedula") String numeroCedula){
        Cliente clienteDelete = clienteServicio.deleteCliente(numeroCedula);
        if (clienteDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDelete);
    }

    @GetMapping(value = "/edad/{edad}")
    public ResponseEntity<List<Cliente>> ListByEdad(@PathVariable("edad") int edad) {
        List<Cliente> clienteDB = clienteServicio.ListByEdad(edad);
        if (clienteDB.isEmpty()) {
                return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteDB);
    }

    private String formatoMensaje(BindingResult result){
        List<Map<String, String>> errores = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMensaje errorMessage = ErrorMensaje.builder()
                .codigo("01")
                .mensaje(errores).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @PostMapping(value = "/form-data", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> formDataPrueba(@RequestBody ClienteDto model){
        log.info("name: {}",model.getNombre());
        log.info("fileName: {}",model.getFile().getName());
        return ResponseEntity.ok("Termino la prueba");
    }

    @AllArgsConstructor @NoArgsConstructor @Data @Builder
    public static class ClienteDto{
        private String nombre;
        private MultipartFile file;
    }
}
