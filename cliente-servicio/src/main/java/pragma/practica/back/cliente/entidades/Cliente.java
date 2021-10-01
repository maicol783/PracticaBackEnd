package pragma.practica.back.cliente.entidades;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import pragma.practica.back.cliente.modelos.Foto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_clientes")
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Cliente {

    @Id
    @Size(min = 5, max = 15, message = "El tamaño del numero debe tener entre 5 a 15 caracteres")
    @Column(name = "numero_identificacion", unique = true)
    private String numeroIdentificacion;

    @NotNull(message = "El tipo de identificacion no debe estar vacio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_identificacion")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private TipoIdentificacion tipoIdentificacion;

    @Size(min = 3, max = 50, message = "El tamaño del nombre debe tener entre 3 a 50 caracteres")
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombres;

    @Size(min = 3, max = 50, message = "El tamaño del apellido debe tener entre 3 a 50 caracteres")
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String apellidos;

    @NotNull(message = "El nombre no puede estar vacio")
    @Positive(message = "Se debe ingresar una edad correcta")
    private int edad;

    @Size(max = 25, message = "El tamaño de la ciudad sobrepasa los 25 caracteres")
    @NotEmpty(message = "El nombre no puede estsar vacio")
    private String ciudad;

    private String estado;

    private String foto;

    @Transient
    private MultipartFile fotoM;

    @Transient
    private Foto fotoT;




}
