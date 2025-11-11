package org.example.service;

import org.example.model.Produto;
import org.example.repository.ProdutoRepositoryImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService{
    @Override
    public Produto cadastrarProduto(Produto produto) {
        ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();

        try{
        if(produto.getPreco() < 0){
            throw new IllegalArgumentException ("Preço deve ser positivo.");
        }else
            produtoRepository.save(produto);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return produto;
    }

    @Override
    public List<Produto> listarProdutos() {
        ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();
        List<Produto> produtos = new ArrayList<>();
        try {
            produtos = produtoRepository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    @Override
    public Produto buscarPorId(int id) {
        ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();
        Produto produto = null;
        try{
            produtoRepository.findById(id);
            if (produto == null){
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    @Override
    public Produto atualizarProduto(Produto produto, int idOriginal) {
        ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();
        Produto produtoAtualizado = null;

        try {
            Produto produtoExistente = produtoRepository.findById(idOriginal);
            if (produtoExistente == null) {
                return  null;
            }else{
                produto = produtoRepository.update(produto, idOriginal);
                produtoAtualizado = produto;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return produtoAtualizado;
    }

    @Override
    public boolean excluirProduto(int id) {
        ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();
        try {
            Produto produtoExistente = produtoRepository.findById(id);
            if (produtoExistente == null) {
                return false;
            } else {
                produtoRepository.deleteById(id);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
