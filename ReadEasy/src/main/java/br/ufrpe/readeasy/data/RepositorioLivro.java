package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.business.ComparadorDeLivro;
import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.exceptions.*;

import java.util.*;


public class RepositorioLivro implements IRepositorioLivro {
    private static RepositorioLivro instance;
    private List<Livro> livros;

    //CONSTRUTOR:
    private RepositorioLivro() {
        this.livros = new ArrayList<>();
    }

    //SINGLETON:
    public static RepositorioLivro getInstance() {
        if (instance == null) {
            instance = new RepositorioLivro();
        }
        return instance;
    }

    //MÉTODOS:
    @Override
    public void cadastrarLivro(Livro livro) throws LivroExistenteException {
        if (!this.livros.contains(livro)) {
            this.livros.add(livro);
        }
        else{
            throw new LivroExistenteException();
        }
    }

    @Override
    public void removerLivro(Livro livro) throws LivroNaoExistenteException {
        if (this.livros.contains(livro)) {
            this.livros.remove(livro);
        }
        else{
            throw new LivroNaoExistenteException();
        }
    }

    @Override
    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor) throws LivroNaoExistenteException {
        boolean achou = false;

        for (int i = 0; i < livros.size() && !achou; i++) {
            if (livros.get(i).getId().equals(livro.getId())) {
                livros.get(i).setTitulo(titulo);
                livros.get(i).setAutor(autor);
                livros.get(i).setPreco(preco);
                livros.get(i).setFornecedor(fornecedor);
                achou = true;
            }
        }
        if (!achou) {
            throw new LivroNaoExistenteException();
        }
    }

    @Override
    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException
            , LivroNaoExistenteException {

        if(this.livros.contains(livro)){
            if (!livro.getGeneros().contains(genero)) {
                livro.adicionarGenero(genero);
            }
            else {
                throw new GeneroExistenteException(livro.getTitulo(), genero.getDescricaoEnum());
            }
        }
        else {
            throw new LivroNaoExistenteException();
        }
    }


    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException
            , LivroNaoExistenteException {

        if(this.livros.contains(livro)){
            if (livro.getGeneros().contains(genero)) {
                livro.removerGenero(genero);
            }
            else {
                throw new GeneroNaoExistenteException("Não foi encontrado nenhum livro contendo o gênero " + genero.getDescricaoEnum());
            }
        }
        else {
            throw new LivroNaoExistenteException();
        }
    }

    @Override
    public Livro buscarLivro(UUID id){
        boolean achou = false;
        Livro livro = null;

        for(int i = 0; i < livros.size() && !achou; i++){
            if(livros.get(i).getId().equals(id)){
                livro = livros.get(i);
                achou = true;
            }
        }
        return livro;
    }

    @Override
    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade) throws LivroNaoExistenteException {
        if (livros.contains(livro)) {
            livro.aumentarQuantidade(quantidade);
        }
        else {
            throw new LivroNaoExistenteException();
        }
    }

    @Override
    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException, LivroNaoExistenteException {
        if (livros.contains(livro)) {
            if(livro.getQuantidade() != 0 ){
                if(livro.getQuantidade() - quantidade >= 0){
                    livro.diminuirQuantidade(quantidade);
                }
                else{
                    throw new QuantidadeInvalidaException();
                }
            }
            else{
                throw new EstoqueInsuficienteException();
            }
        } else {
            throw new LivroNaoExistenteException();
        }
    }

    @Override
    public List<Livro> listarTodosOsLivrosEmOrdemAlfabetica() {
        List<Livro> lista = new ArrayList<>();

        lista.addAll(livros);
        lista = ComparadorDeLivro.ordenarPorTitulo(lista);
        return lista;
    }

    @Override
    public Map<Livro, Integer> listarQuantidadeDeEstoque(){
        Map<Livro, Integer> estoque = new HashMap<>();

        for(Livro livro : livros){
            estoque.put(livro, livro.getQuantidade());
        }
        return estoque;
    }

    @Override
    public List<Livro> listarLivrosPorAutor(String autor) {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getAutor().contains(autor)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorGenero(Genero genero) throws GeneroNaoExistenteException {
        List<Livro> lista = new ArrayList<>();
        boolean contemGenero = false;

        for (Livro livro : livros) {
            if (livro.getGeneros().contains(genero)) {
                lista.add(livro);
                contemGenero = true;
            }
        }

        if(!contemGenero){
            throw new GeneroNaoExistenteException("Não foi encontrado nenhum livro contendo o gênero " + genero.getDescricaoEnum());
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getFornecedor().equals(fornecedor)) {
                lista.add(livro);
            }
        }
        return lista;
    }

    @Override
    public List<Livro> listarEOrdenarLivrosPorPreco() {
        List<Livro> lista = new ArrayList<>();

        lista.addAll(livros);
        lista = ComparadorDeLivro.ordenarPorPreco(lista);
        return lista;
    }
}
