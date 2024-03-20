package br.com.fiap.springpfauthentication.resource;

import br.com.fiap.springpfauthentication.dto.response.PessoaResponse;
import br.com.fiap.springpfauthentication.dto.request.UsuarioRequest;
import br.com.fiap.springpfauthentication.dto.response.UsuarioResponse;
import br.com.fiap.springpfauthentication.entity.Pessoa;
import br.com.fiap.springpfauthentication.entity.Usuario;
import br.com.fiap.springpfauthentication.repository.UsuarioRepository;
import br.com.fiap.springpfauthentication.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository repo;


    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioResponse> findAll() {
        return repo.findAll().stream().map(service::toResponse).toList();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> findById(@PathVariable Long id) {
        Usuario usuario = repo.findById(id).orElse(null);
        if(Objects.isNull(usuario)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(service.toResponse(usuario));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest u) {

        //Se não me informou a propriedade pessoa na hora de mandar a requisição eu retorno null
        if (Objects.isNull(u.pessoa())) return null;

        //Eu recebi um DTO (Usuariorequest), entretanto eu só posso salvar Entidades, por isso eu converti
        Usuario entity = service.toEntity(u);

        //Agora posso salvar no meu repositório.
        Usuario salvo = repo.save(entity);

        //Neste momento eu vou transformar o entity que recebeu o Id do Banco de Dados em um DTO de resposta
        UsuarioResponse body = service.toResponse(salvo);


        //Agora minha preocupação está em criar o correto cabeçalho de resposta (Response),
        // quando salvamos, é necessário incluir no cabeçalho o Location do recurso que foi salvo.
        // Neste caso o resource é de usuário
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();

        return ResponseEntity.created(uri).body(body);

    }


}
