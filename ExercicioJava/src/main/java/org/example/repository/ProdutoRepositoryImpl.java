package org.example.repository;

import org.example.model.Produto;
import org.example.util.ConexaoBanco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository{

    @Override
    public Produto save(Produto produto) throws SQLException {
        String sql = """
                INSERT INTO produto (
                nome,
                preco, 
                quantidade, 
                categoria ) 
                VALUES (?,?,?,?)
                """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setString(4, produto.getCategoria());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    produto.setId(id);
                }
            }
        }
        return produto;
    }

    @Override
    public List<Produto> findAll() throws SQLException {
        List<Produto>produtos = new ArrayList<>();
        String sql = """
                SELECT id, nome, preco, quantidade, categoria
                FROM produto
                """;

        try (Connection conn = ConexaoBanco.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCategoria(rs.getString("categoria"));
                produtos.add(produto);
            }
        }

        return produtos;
    }

    @Override
    public Produto findById(int id) throws SQLException {

        Produto produto = null;

        String sql = """
                SELECT id, nome, preco, quantidade, categoria
                FROM produto
                WHERE id = ?
                """;
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoria(rs.getString("categoria"));
                }
            }
        return produto;
    }
    }

    @Override
    public Produto update(Produto produto, int idOriginal) throws SQLException {

        String sql = """
                UPDATE produto
                SET 
                nome = ?,
                preco = ?,
                quantidade = ?,
                categoria = ?
                WHERE id = ?
                """;

        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setInt(3, produto.getQuantidade());
            ps.setString(4, produto.getCategoria());
            ps.setInt(5, idOriginal);
            ps.executeUpdate();
        }
        return produto;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        String sql = """
                DELETE FROM produto 
                WHERE id = ?
                """;
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
