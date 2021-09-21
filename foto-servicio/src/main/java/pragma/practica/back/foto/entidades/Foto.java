package pragma.practica.back.foto.entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document

@AllArgsConstructor @NoArgsConstructor @Builder
public class Foto {
    @Id
    private int idFoto;
    private String nombre;

}
