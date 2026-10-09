package br.com.olariafriburgo.backend.service;

import br.com.olariafriburgo.backend.model.Produto;
import br.com.olariafriburgo.backend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {

        Optional<Produto> produto = produtoRepository.findById(id);

        if (produto.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        return produto.get();
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto) {

        Optional<Produto> produtoExistente = produtoRepository.findById(id);

        if (produtoExistente.isEmpty()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produtoAtual = produtoExistente.get();

        produtoAtual.setNome(produto.getNome());
        produtoAtual.setCategoria(produto.getCategoria());
        produtoAtual.setDimensoes(produto.getDimensoes());
        produtoAtual.setDescricao(produto.getDescricao());
        produtoAtual.setFoto(produto.getFoto());

        return produtoRepository.save(produtoAtual);
    }

    public void deletarProduto(Long id) {

        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }

        produtoRepository.deleteById(id);
    }
}