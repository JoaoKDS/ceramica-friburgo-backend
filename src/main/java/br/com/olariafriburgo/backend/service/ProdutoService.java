package br.com.olariafriburgo.backend.service;

import br.com.olariafriburgo.backend.model.Produto;
import br.com.olariafriburgo.backend.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    private ResponseStatusException naoEncontrado() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(this::naoEncontrado);
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoAtual = buscarPorId(id);

        produtoAtual.setNome(produto.getNome());
        produtoAtual.setCategoria(produto.getCategoria());
        produtoAtual.setDimensoes(produto.getDimensoes());
        produtoAtual.setDescricao(produto.getDescricao());
        produtoAtual.setFoto(produto.getFoto());

        return produtoRepository.save(produtoAtual);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw naoEncontrado();
        }
        produtoRepository.deleteById(id);
    }
}