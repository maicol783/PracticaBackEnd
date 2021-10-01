package pragma.practica.back.foto.controlador;

import com.fasterxml.jackson.databind.ser.Serializers;
//import net.iharder.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pragma.practica.back.foto.entidades.Foto;
import pragma.practica.back.foto.servicios.FotoServicio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/fotos")

public class FotoController {

    @Autowired
    private FotoServicio fotoServicio;

    private Logger logger = LoggerFactory.getLogger(FotoController.class);

    @GetMapping
    public ResponseEntity<List<Foto>> listFotos(@RequestParam(value = "id", required = false) String id){
        List<Foto> fotos = new ArrayList<>();
        if(id == null) {
            fotos = fotoServicio.listFotos();
            if (fotos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }else{
            fotos = fotoServicio.findByid(id);
            if (fotos.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(fotos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Foto> getFoto(@PathVariable("id")  String id){
        Foto fotoDB = fotoServicio.getFoto(id);
        if (fotoDB == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fotoDB);

    }

    @PostMapping
    public ResponseEntity<Foto> createFoto(@RequestParam(value = "fotoSubida") MultipartFile fotoSubida){
        Foto foto = new Foto();
        if (!fotoSubida.isEmpty()){
            try {
                foto.setNombre(Base64.getEncoder().encodeToString(fotoSubida.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            fotoServicio.createFoto(foto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(foto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Foto> updateFoto(@PathVariable("id") String id, @RequestParam(value = "fotoNueva") MultipartFile fotoNueva){
        Path directorioFotos = Paths.get("src//main//resources/photos");
        String rutaAbsoluta = directorioFotos.toFile().getAbsolutePath();
        Foto fotoDb = fotoServicio.getFoto(id);
        try {
            byte[] bytesPhoto = fotoNueva.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ Base64.getEncoder().encodeToString(fotoNueva.getBytes()));
            Files.write(rutaCompleta, bytesPhoto);

            fotoDb.setNombre(Base64.getEncoder().encodeToString(fotoNueva.getBytes()));
            fotoServicio.updateFoto(fotoDb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(fotoDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Foto> deleteFoto(@PathVariable("id") String id){
        Foto fotoDelete = fotoServicio.deleteFoto(id);
        if(fotoDelete == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fotoDelete);
    }
}
