package pragma.practica.back.foto.servicios;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pragma.practica.back.foto.entidades.Foto;
import pragma.practica.back.foto.repositorios.FotoRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FotoServicioImp implements FotoServicio{
    @Autowired
    private final FotoRepositorio fotoRepositorio;
    @Override
    public List<Foto> listFotos() {
        return fotoRepositorio.findAll();
    }

    @Override
    public List<Foto> findByid(String id) {
        return fotoRepositorio.findByid(id);
    }

    @Override
    public Foto getFoto(String id) {
        return fotoRepositorio.findById(id).orElse(null);
    }

    @Override
    public Foto createFoto(Foto foto) {
        return fotoRepositorio.insert(foto);
    }

    @Override
    public Foto updateFoto(Foto foto) {
        /*Foto fotoDB =getFoto(foto.getId());
        if (fotoDB == null){
            return null;
        }
        fotoDB.setId(foto.getId());

        fotoDB.setNombre(foto.getNombre());*/
        return fotoRepositorio.save(foto);
    }

    @Override
    public Foto deleteFoto(String id) {
        Foto fotoDB =getFoto(id);
        if (fotoDB == null){
            return null;
        }
        return fotoRepositorio.deleteByid(id);
    }
}
