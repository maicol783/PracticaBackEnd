package pragma.practica.back.foto.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tbl_fotos")
@AllArgsConstructor @NoArgsConstructor
public class Foto {
    @Id
    @Column(name = "id_foto")
    private int idFoto;
    private String nombre;

}
