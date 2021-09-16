package pragma.practica.back.cliente.controlador.errores;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data @Builder
public class ErrorMensaje {

    private String codigo;
    private List<Map<String,String>> mensaje;
}
