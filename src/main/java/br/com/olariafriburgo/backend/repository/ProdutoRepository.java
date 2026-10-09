package br.com.olariafriburgo.backend.repository;

import br.com.olariafriburgo.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
