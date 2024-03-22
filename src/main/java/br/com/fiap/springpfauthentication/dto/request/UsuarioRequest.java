package br.com.fiap.springpfauthentication.dto.request;

public record UsuarioRequest(

        String email,
        String senha,

        PessoaRequest pessoa

) {
}
