package br.com.fiap.springpfauthentication.resource;


import br.com.fiap.springpfauthentication.dto.listagem.UsuarioListagem;
import br.com.fiap.springpfauthentication.dto.request.SistemaRequest;
import br.com.fiap.springpfauthentication.dto.response.SistemaResponse;
import br.com.fiap.springpfauthentication.entity.Sistema;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.SistemaRepository;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import br.com.fiap.springpfauthentication.service.SistemaService;
import br.com.fiap.springpfauthentication.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/sistema")
public class SistemaResource {

    @Autowired
    private SistemaRepository repo;


    @Autowired
    private SistemaService service;

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<SistemaResponse> findAll() {
        return repo.findAll().stream().map( service::toResponse ).toList();
    }

    @GetMapping(value = "/{id}")
    public SistemaResponse findById(@PathVariable Long id) {
        Optional<Sistema> sistema = repo.findById( id );
        return service.toResponse( sistema.get() );
    }

    @GetMapping(value = "/{id}/responsaveis")
    public SistemaResponse getResponsaveis(@PathVariable Long id) {
        Optional<Sistema> sistema = repo.findById( id );
        return service.toResponse( sistema.get() );
    }

    @Transactional
    @PostMapping
    public SistemaResponse save(@RequestBody SistemaRequest s) {
        if (Objects.isNull( s )) return null;
        Sistema entity = service.toEntity( s );
        Sistema sistema = repo.save( entity );
        return service.toResponse( sistema );
    }

    @Transactional
    @PostMapping(value = "/{id}/responsaveis")
    public SistemaResponse addResponsavel(@PathVariable Long id, @RequestBody UsuarioListagem responsavel) {
        Sistema sistema = repo.findById( id ).orElseThrow();
        Usuario usuario = usuarioService.toObjet( responsavel );
        //Adiciono o responsável à lista de responsáveis
        sistema.getResponsaveis().add( usuario );
        //Como confio no JPA e na transação, eu nem dou o comando de salvar o usuário.
        //Eu já mando pegar a listagem de responsáveis do sistema
        return service.toResponse( sistema );
    }

}
