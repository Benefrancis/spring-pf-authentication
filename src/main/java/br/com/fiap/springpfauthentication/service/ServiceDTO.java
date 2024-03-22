package br.com.fiap.springpfauthentication.service;

public interface ServiceDTO<E, I, O, L> {

    E toEntity(I request);

    O toResponse(E entity);

    E toObjet(L l);


}
