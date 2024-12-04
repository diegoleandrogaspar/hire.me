package com.bemobi.domain.repository;

import com.bemobi.domain.model.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {

    /**
    * Busca uma URL encurtada pelo alias.
    *
    * @param alias Alias para pesquisa.
    * @return Um Optional contendo a entidade encontrada, ou vazio se não existir.
    */
    Optional<UrlShortener> findByAlias(String alias);

    /**
     * Verifica se um alias já está registrado.
     *
     * @param alias Alias para verificar.
     * @return True se o alias já existir, caso contrário, false.
     */
    boolean existsByAlias(String alias);
}
