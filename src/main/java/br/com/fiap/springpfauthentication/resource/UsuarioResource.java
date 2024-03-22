package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.dto.request.UsuarioRequest;
import br.com.fiap.springpfauthentication.dto.response.UsuarioResponse;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import br.com.fiap.springpfauthentication.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponse> findAll() {
        return repo.findAll().stream().map( service::toResponse ).toList();
    }

    @GetMapping(value = "/{id}")
    public UsuarioResponse findById(@PathVariable Long id) {
        Optional<Usuario> usuario = repo.findById( id );
        return service.toResponse( usuario.get() );
    }

    @Transactional
    @PostMapping
    public UsuarioResponse save(@RequestBody UsuarioRequest u) {
        Usuario entity = service.toEntity( u );
        Usuario usuario = repo.save( entity );
        return service.toResponse( usuario );
    }


}
