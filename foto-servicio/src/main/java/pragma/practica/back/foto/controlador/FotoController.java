package pragma.practica.back.foto.controlador;

import com.fasterxml.jackson.databind.ser.Serializers;
import net.iharder.Base64;
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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/fotos")

public class FotoController {

    @Autowired
    private FotoServicio fotoServicio;

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

    @PostMapping
    public ResponseEntity<Foto> createFoto(@RequestParam(value = "fotoSubida")MultipartFile fotoSubida, Foto foto){
        if (!fotoSubida.isEmpty()){
            Path directorioFotos = Paths.get("src//main//resources/photos");
            String rutaAbsoluta = directorioFotos.toFile().getAbsolutePath();

            try {
                byte[] bytesPhoto = fotoSubida.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ fotoSubida.getOriginalFilename());
                Files.write(rutaCompleta, bytesPhoto);
                String fotoCodificar = fotoSubida.getOriginalFilename();
                String base64 = Base64.encodeBytes(fotoCodificar != null ? fotoCodificar.getBytes():null);
                foto.setNombre(base64);
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
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ fotoNueva.getOriginalFilename());
            Files.write(rutaCompleta, bytesPhoto);
            String fotoCodificar = fotoNueva.getOriginalFilename();
            String base64 = Base64.encodeBytes(fotoCodificar != null ? fotoCodificar.getBytes():null);
            fotoDb.setNombre(base64);
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
