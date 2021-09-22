package pragma.practica.back.foto.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pragma.practica.back.foto.entidades.Foto;

@Repository
public interface FotoRepositorio extends MongoRepository<Foto, String> {
}
