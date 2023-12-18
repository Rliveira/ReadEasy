package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.data.IRepositorioLivro;
import br.ufrpe.readeasy.data.RepositorioLivro;
import br.ufrpe.readeasy.exceptions.*;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ControladorLivro implements IcontroladorLivro {
    private IRepositorioLivro repLivro;
    private static ControladorLivro instance;

    //CONSTRUTOR:
    private ControladorLivro(){
        repLivro = RepositorioLivro.getInstance();
    }

    //SINGLETON:
    public static ControladorLivro getInstance(){
        if (instance == null) {
            instance = new ControladorLivro();
        }
        return instance;
    }

    //MÉTODOS:

    @Override
    public void adicionarLivro(Livro livro) throws LivroNuloException, CampoVazioException, PrecoInvalidoException
            , LivroExistenteException {
        if(livro == null || livro.getTitulo() == null || livro.getAutor() == null || livro.getFornecedor() == null){
            throw new LivroNuloException();
        }
        if(livro.getTitulo().isBlank() && !livro.getAutor().isBlank()){
            throw new CampoVazioException();
        }
        if(livro.getPreco() < 0){
            throw new PrecoInvalidoException();
        }

        repLivro.cadastrarLivro(livro);
    }

    @Override
    public void removerLivro(Livro livro) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException {
        if(livro == null || livro.getTitulo() == null || livro.getAutor() == null || livro.getFornecedor() == null){
            throw new LivroNuloException();
        }
        if(livro.getTitulo().isBlank() && !livro.getAutor().isBlank()){
            throw new CampoVazioException();
        }

        repLivro.removerLivro(livro);
    }

    @Override
    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor)
            throws LivroNaoExistenteException, CampoVazioException, LivroNuloException,
            PrecoInvalidoException, UsuarioNuloException, DataInvalidaException, MenorDeIdadeException {

        if(livro != null && livro.getTitulo() != null && livro.getAutor() != null && livro.getFornecedor() != null) {
            if(!titulo.isBlank() && !autor.isBlank()) {
                if(preco > 0){
                    if(fornecedor.getNome() != null && fornecedor.getCpf() != null && fornecedor.getLogin() != null
                            && fornecedor.getSenha() != null){
                        if(!fornecedor.getNome().isBlank() && !fornecedor.getCpf().isBlank() && !fornecedor.getLogin().isBlank()
                                && !fornecedor.getSenha().isBlank()){
                            if(!fornecedor.getDataNascimento().isAfter(LocalDate.now())){
                                if(fornecedor.getIdade() > 18){
                                    repLivro.atualizarLivro(livro, titulo, autor, preco, fornecedor);
                                }
                                else{
                                    throw new MenorDeIdadeException(fornecedor.getIdade());
                                }
                            }
                            else{
                                throw new DataInvalidaException(fornecedor.getDataNascimento());
                            }
                        }
                        else{
                            throw new CampoVazioException();
                        }
                    }
                    else{
                        throw new UsuarioNuloException();
                    }
                }
                else{
                    throw new PrecoInvalidoException();
                }
            }
            else {
                throw new CampoVazioException();
            }
        }
        else {
            throw new LivroNuloException();
        }
    }

    @Override
    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException, CampoVazioException
            , LivroNuloException, LivroNaoExistenteException {

        if(livro != null){
            repLivro.adicionarGenero(livro, genero);
        }
        else{
            throw new LivroNuloException();
        }
    }

    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, CampoVazioException
            , LivroNuloException, LivroNaoExistenteException {

        if(livro.getTitulo() != null) {
            if(!livro.getTitulo().isBlank()) {
                repLivro.removerGenero(livro, genero);
            }
            else {
                throw new CampoVazioException();
            }
        }
        else {
            throw new LivroNuloException();
        }
    }
    @Override
    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade) throws LivroNaoExistenteException
            , LivroNuloException, QuantidadeInvalidaException {

        if (livro != null) {
            if(quantidade > 0){
                repLivro.aumentarQuantidadeEmEstoque(livro, quantidade);
            }
            else{
                throw new QuantidadeInvalidaException();
            }
        }
        else{
            throw new LivroNuloException();
        }
    }

    @Override
    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException, QuantidadeInvalidaException, LivroNaoExistenteException, LivroNuloException {
        if (livro != null) {
            if(quantidade > 0){
                repLivro.diminuirQuantidadeEmEstoque(livro, quantidade);
            }
            else{
                throw new QuantidadeInvalidaException();
            }
        }
        else{
            throw new LivroNuloException();
        }
    }

    @Override
    public Livro buscarLivro(UUID id) {
        return repLivro.buscarLivro(id);
    }

    @Override
    public List<Livro> listarTodosOslivrosEmOrdemAlfabetica(){
        return repLivro.listarTodosOsLivrosEmOrdemAlfabetica();
    }

    @Override
    public List<Livro> listarLivrosPorAutor(String nomeAutor) {
        List<Livro> lista = new ArrayList<>();

        if(nomeAutor != null && nomeAutor.isBlank()){
            lista = repLivro.listarLivrosPorAutor(nomeAutor);
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException{
        return  repLivro.listarLivrosPorGenero(genero);
    }

    @Override
    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) {
        return repLivro.listarLivrosPorFornecedor(fornecedor);
    }

    @Override
    public List<Livro> listarEOrdenarLivrosPorPreco(){
        return repLivro.listarEOrdenarLivrosPorPreco();
    }

    @Override
    public Map<Livro, Integer> listarQuantidadeDeEstoque(){
        return repLivro.listarQuantidadeDeEstoque();
    }

}