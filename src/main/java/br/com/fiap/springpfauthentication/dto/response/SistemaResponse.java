package br.com.fiap.springpfauthentication.dto.response;

import java.util.List;

public record SistemaResponse(


        Long id,

        String nome,

        String sigla,

        List<UsuarioResponse> responsaveis

) {
}
