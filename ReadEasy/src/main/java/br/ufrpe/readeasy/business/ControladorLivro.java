package br.ufrpe.readeasy.business;

import br.ufrpe.readeasy.data.IRepositorioLivro;
import br.ufrpe.readeasy.data.RepositorioLivro;
import br.ufrpe.readeasy.exceptions.*;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import java.util.List;

public class ControladorLivro {
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
    public void adicionarLivro(List<Livro> livros) throws LivroNuloException, CampoVazioException, PrecoInvalidoException, LivroExistenteException {
        for (Livro livro : livros){
            if(livro == null || livro.getTitulo() == null || livro.getAutor() == null || livro.getFornecedor() == null){
                throw new LivroNuloException();
            }
            if(livro.getTitulo().isBlank() && !livro.getAutor().isBlank()){
                throw new CampoVazioException();
            }
            if(livro.getPreco() < 0){
                throw new PrecoInvalidoException();
            }
        }
        repLivro.adicionarLivros(livros);
    }

    public void removerLivros(List<Livro> livros) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException {
        for (Livro livro : livros){
            if(livro == null || livro.getTitulo() == null || livro.getAutor() == null || livro.getFornecedor() == null){
                throw new LivroNuloException();
            }
            if(livro.getTitulo().isBlank() && !livro.getAutor().isBlank()){
                throw new CampoVazioException();
            }
        }

        repLivro.removerLivros(livros);

    }

    public void atualizarLivros(Livro livro) throws LivroNuloException, CampoVazioException, LivroNaoExistenteException{

        if(livro.getTitulo() != null && livro.getAutor() != null && livro.getFornecedor() != null) {
            if(!livro.getTitulo().isBlank() && !livro.getAutor().isBlank()) {
                repLivro.atualizarLivros(livro);
            }
            else {
                throw new CampoVazioException();
            }
        }
        else {
            throw new LivroNuloException();
        }
    }

    public void adicionarGenero(String tituloLivro, Genero genero) throws GeneroExistenteException, CampoVazioException, LivroNuloException, LivroNaoExistenteException {

        if(tituloLivro != null){
            if(!tituloLivro.isBlank()){
                repLivro.adicionarGenero(tituloLivro, genero);
            }
            else{
                throw new CampoVazioException();
            }
        }
        else{
            throw new LivroNuloException();
        }
    }

    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, CampoVazioException, LivroNuloException, LivroNaoExistenteException {

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


    public List<Livro> listarTodosOslivrosEmOrdemAlfabetica(){
        return repLivro.listarTodosOsLivrosEmOrdemAlfabetica();
    }

    public List<Livro> listarLivrosPorTitulo(String titulo) throws LivroNaoExistenteException{
        return repLivro.listarLivrosPorTitulo(titulo);
    }

    public List<Livro> listarLivrosPorAutor(String nomeAutor) {
        return  repLivro.listarLivrosPorAutor(nomeAutor);
    }

    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException{
        return  repLivro.listarLivrosPorGenero(genero);
    }

    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) {
        return  repLivro.listarLivrosPorFornecedor(fornecedor);
    }

    public List<Livro> listarEOrdenarLivrosPorPreco(){
        return repLivro.listarEOrdenarLivrosPorPreco();
    }
}