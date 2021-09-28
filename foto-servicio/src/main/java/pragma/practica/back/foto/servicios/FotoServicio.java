package pragma.practica.back.foto.servicios;

import pragma.practica.back.foto.entidades.Foto;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public interface FotoServicio {
    List<Foto> listFotos();
    List<Foto> findByid(String id);
    Foto getFoto(String id);
    Foto createFoto(Foto foto);
    Foto updateFoto(Foto foto);
    Foto deleteFoto(String id);


}
