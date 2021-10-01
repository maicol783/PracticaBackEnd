package pragma.practica.back.cliente.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pragma.practica.back.cliente.modelos.Foto;

import java.util.List;

@FeignClient(name = "foto-servicio", url = "http://localhost:8091")
@RequestMapping(value = "/fotos")
@Component
public interface ClienteFoto {

    @PostMapping
    ResponseEntity<Foto> createFoto(@RequestBody Foto foto);

    @GetMapping(value = "/{id}")
    public ResponseEntity<Foto> getFoto(@PathVariable("id")  String id);
}
