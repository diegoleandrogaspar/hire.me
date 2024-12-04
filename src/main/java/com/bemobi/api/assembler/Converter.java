package com.bemobi.api.assembler;

import java.util.Collection;

/**
 * Interface para conversão entre DTOs e entidades.
 * @param <D> Tipo de domínio (entidade).
 * @param <O> Tipo de objeto de saída (DTO).
 * @param <I> Tipo de entrada (DTO de entrada).
 * @return
 */
public interface Converter<D, O, I > {

    /**
     * Converte um DTO de entrada para um objeto de domínio (entidade).
     *
     * @param input DTO de entrada
     * @return Objeto de domínio (entidade)
     */
    public D toDomainObject(I input);

    /**
     * Converte um objeto de domínio (entidade) para um DTO.
     *
     * @param domain Objeto de domínio
     * @return DTO de saída
     */
    public O toDto(D domain);

    /**
     * Converte uma coleção de objetos de domínio (entidades) para uma coleção de DTOs.
     *
     * @param list Lista de objetos de domínio
     * @return Lista de DTOs
     */
    public Collection<O> toCollectionDTO(Collection<D> list);

    /**
     * Copia os dados de um DTO de entrada para um objeto de domínio.
     *
     * @param input DTO de entrada
     * @param domain Objeto de domínio a ser atualizado
     */
    public void copyToDomainObject(I input, D domain);
}
