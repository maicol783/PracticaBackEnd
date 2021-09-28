package pragma.practica.back.foto.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import pragma.practica.back.foto.entidades.Foto;

import java.util.List;

@Repository
public interface FotoRepositorio extends MongoRepository<Foto, String> {
    Foto deleteByid(String id);
    List<Foto> findByid(String id);

}
