package br.com.fiap.springpfauthentication.service;


import br.com.fiap.springpfauthentication.dto.listagem.SistemaListagem;
import br.com.fiap.springpfauthentication.dto.request.SistemaRequest;
import br.com.fiap.springpfauthentication.dto.response.SistemaResponse;
import br.com.fiap.springpfauthentication.dto.response.UsuarioResponse;
import br.com.fiap.springpfauthentication.entity.Sistema;
import br.com.fiap.springpfauthentication.repository.SistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SistemaService implements ServiceDTO<Sistema, SistemaRequest, SistemaResponse, SistemaListagem> {

    @Autowired
    SistemaRepository repo;


    @Autowired
    UsuarioService usuarioService;

    @Override
    public Sistema toEntity(SistemaRequest request) {
        if (Objects.isNull( request )) return null;
        return Sistema.builder().nome( request.nome() ).sigla( request.sigla() ).build();
    }

    @Override
    public SistemaResponse toResponse(Sistema entity) {

        List<UsuarioResponse> responsaveis = new ArrayList<>();

        if (Objects.nonNull( entity.getResponsaveis() )) {
            responsaveis = entity.getResponsaveis().stream().map( usuarioService::toResponse ).toList();
        }


        return new SistemaResponse( entity.getId(), entity.getNome(), entity.getSigla(), responsaveis );
    }

    @Override
    public Sistema toObjet(SistemaListagem sistemaListagem) {
        Sistema sistema = repo.findById( sistemaListagem.id() ).orElseThrow();
        return sistema;
    }
}
