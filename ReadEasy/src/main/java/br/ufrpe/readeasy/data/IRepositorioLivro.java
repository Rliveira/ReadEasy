package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.CompraLivariaDTO;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.exceptions.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IRepositorioLivro {
    void cadastrarLivro(Livro livro) throws LivroExistenteException;
    void removerLivro(Livro livro);
    void atualizarLivro(Livro livro, String titulo, String autor, double Preco, Fornecedor fornecedor, byte[] capaDoLivro, URL urlLivro) throws LivroExistenteException;
    void adicionarGenero (Livro livro, Genero genero) throws GeneroExistenteException;
    void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException;
    void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao, Double ValorTotalPago);
    void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException
            , ValorInvalidoException;
    Livro buscarLivro(UUID id);
    List<Livro> listarTodosOsLivrosEmOrdemAlfabetica();
    List<Livro> listarLivrosPorAutor(String autor);
    List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException;
    List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor)  throws FornecedorNaoEncontradoException;
    List<CompraLivariaDTO> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor, LocalDate dataInicio
            , LocalDate dataFim) throws FornecedorNaoEncontradoException;
    List<CompraLivariaDTO> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException;
    List<Livro> listarEOrdenarLivrosPorPreco();
    Map<Livro, Integer> listarQuantidadeDeEstoque();
    List<Livro> listarLivrosComEstoqueDisponivel();
    Livro buscarLivroPorNome(String titulo);
    void salvarArquivo();
}
