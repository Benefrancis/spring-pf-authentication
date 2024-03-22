package br.com.fiap.springpfauthentication.service;


import br.com.fiap.springpfauthentication.dto.listagem.UsuarioListagem;
import br.com.fiap.springpfauthentication.dto.request.UsuarioRequest;
import br.com.fiap.springpfauthentication.dto.response.PessoaResponse;
import br.com.fiap.springpfauthentication.dto.response.UsuarioResponse;
import br.com.fiap.springpfauthentication.entity.Pessoa;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements ServiceDTO<Usuario, UsuarioRequest, UsuarioResponse, UsuarioListagem> {

    @Autowired
    UsuarioRepository repo;


    @Override
    public Usuario toEntity(UsuarioRequest request) {
        if (Objects.isNull( request )) return null;
        if (Objects.isNull( request.pessoa().nome() )) return null;
        Pessoa pessoa = Pessoa.builder().nome( request.pessoa().nome() ).build();
        return Usuario.builder().email( request.email() ).senha( request.senha() ).pessoa( pessoa ).build();
    }

    @Override
    public UsuarioResponse toResponse(Usuario entity) {
        if (Objects.isNull( entity )) return null;
        PessoaResponse pessoaResponse = new PessoaResponse( entity.getPessoa().getId(), entity.getPessoa().getNome() );
        return new UsuarioResponse( entity.getId(), entity.getEmail(), pessoaResponse );
    }

    @Override
    public Usuario toObjet(UsuarioListagem usuarioListagem) {
        Optional<Usuario> usuario = repo.findById( usuarioListagem.id() );
        if (usuario.isEmpty()) return null;
        return usuario.get();
    }


}
