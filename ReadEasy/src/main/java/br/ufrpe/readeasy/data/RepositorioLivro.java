package br.ufrpe.readeasy.data;

import br.ufrpe.readeasy.beans.Fornecedor;
import br.ufrpe.readeasy.beans.Genero;
import br.ufrpe.readeasy.beans.Livro;
import br.ufrpe.readeasy.business.ComparadorDeLivro;
import br.ufrpe.readeasy.exceptions.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;


public class RepositorioLivro implements IRepositorioLivro, Serializable {
    private static RepositorioLivro instance;
    private List<Livro> livros;

    //CONSTRUTOR:
    private RepositorioLivro() {
        this.livros = new ArrayList<>();
    }

    //SINGLETON:
    public static RepositorioLivro getInstance() {
        if (instance == null) {
            instance = lerDoArquivo();
        }
        return instance;
    }

    //MÉTODOS:
    @Override
    public void cadastrarLivro(Livro livro) throws LivroExistenteException {
        if (!this.livros.contains(livro)) {
            if(!verificarLivrosComTitulosIguais(livro)){
                this.livros.add(livro);
            }
            else{
                throw new LivroExistenteException();
            }
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
    public void atualizarLivro(Livro livro, String titulo, String autor, double preco, Fornecedor fornecedor, URL urlLivro) throws LivroExistenteException {
        boolean achou = false;

        for (int i = 0; i < livros.size() && !achou; i++) {
            if(verificarLivrosComTitulosIguais(livro)){
                throw new LivroExistenteException();
            }
            else if (livros.get(i).getId().equals(livro.getId()) && verificarLivrosComTitulosIguais(livro)){
                throw new LivroExistenteException();
            }
            else if (livros.get(i).getTitulo().equals(livro.getTitulo()) && livros.get(i).getId().equals(livro.getId())){
                livros.get(i).setTitulo(titulo);
                livros.get(i).setAutor(autor);
                livros.get(i).setPreco(preco);
                livros.get(i).setFornecedor(fornecedor);
                livros.get(i).setCapaDoLivro(urlLivro);
                achou = true;
            }
        }
    }

    @Override
    public void adicionarGenero(Livro livro, Genero genero) throws GeneroExistenteException {
        if(this.livros.contains(livro)){
            if (!livro.getGeneros().contains(genero)) {
                livro.adicionarGenero(genero);
            }
            else {
                throw new GeneroExistenteException(livro.getTitulo(), genero.getDescricaoEnum());
            }
        }
    }


    @Override
    public void removerGenero(Livro livro, Genero genero) throws GeneroNaoExistenteException, LivroSemGeneroException {
        if(this.livros.contains(livro)){
            if(livro.getGeneros().size() > 1){
                if (livro.getGeneros().contains(genero)) {
                    livro.removerGenero(genero);
                }
                else {
                    throw new GeneroNaoExistenteException("Não foi encontrado nenhum livro contendo o gênero "
                            + genero.getDescricaoEnum());
                }
            }
            else{
                throw new LivroSemGeneroException();
            }
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
    public void aumentarQuantidadeEmEstoque(Livro livro, int quantidade, LocalDate dataDaAtualizacao) {
        if (livros.contains(livro)) {
            livro.aumentarQuantidade(quantidade);
            livro.atualizarRegistroDeEstoque(dataDaAtualizacao, quantidade);
        }
    }

    @Override
    public void diminuirQuantidadeEmEstoque(Livro livro, int quantidade) throws EstoqueInsuficienteException,
            QuantidadeInvalidaException {
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
    public Livro buscarLivroPorNome(String titulo) {
        boolean achou = false;
        Livro livro = null;

        for(int i = 0; i < livros.size() && !achou; i++){
            if(livros.get(i).getTitulo().equals(titulo)){
                livro = livros.get(i);
                achou = true;
            }
        }
        return livro;
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
            throw new GeneroNaoExistenteException("Não foi encontrado nenhum livro contendo o gênero "
                    + genero.getDescricaoEnum());
        }
        return lista;
    }

    @Override
    public List<Livro> listarLivrosPorFornecedor(Fornecedor fornecedor) throws FornecedorNaoEncontradoException {
        List<Livro> lista = new ArrayList<>();
        boolean fornecedorEncontrado = false;

        for (Livro livro : livros) {
            if (livro.getFornecedor().equals(fornecedor)) {
                fornecedorEncontrado = true;
                lista.add(livro);
            }
        }
        if(!fornecedorEncontrado){
            throw new FornecedorNaoEncontradoException();
        }
        return lista;
    }

    @Override
    public Map<Livro, Map<LocalDate, Integer>> ListarHistoricoDeVendasFornecedor(Fornecedor fornecedor
            , LocalDate dataInicio, LocalDate dataFim) throws FornecedorNaoEncontradoException {
        if(dataInicio == null ){
            dataInicio = LocalDate.MIN;
        }
        if(dataFim == null){
            dataFim = LocalDate.now();
        }

        Map<Livro, Map<LocalDate, Integer>> lista = new HashMap<>();
        boolean fornecedorEncontrado = false;

        for (Livro livro : livros) {
            if (livro.getFornecedor().equals(fornecedor)) {

                fornecedorEncontrado = true;
                Map<LocalDate, Integer> registroDoEstoque = livro.getRegistroAtualizacaoEstoque();
                Map<LocalDate, Integer> dadosNoIntervalo = new HashMap<>();

                for (Map.Entry<LocalDate, Integer> entry : registroDoEstoque.entrySet()) {
                    LocalDate dataAtualizacao = entry.getKey();

                    if ((!dataAtualizacao.isBefore(dataInicio) || dataAtualizacao.isEqual(dataInicio)) &&
                            (!dataAtualizacao.isAfter(dataFim) || dataAtualizacao.isEqual(dataFim))) {
                        dadosNoIntervalo.put(dataAtualizacao, entry.getValue());
                    }
                }
                if (!dadosNoIntervalo.isEmpty()) {
                    lista.put(livro, dadosNoIntervalo);
                }
            }
        }
        if(!fornecedorEncontrado){
            throw new FornecedorNaoEncontradoException();
        }
        return lista;
    }

    @Override
    public List<Livro> historicoLivrosCompradosLivraria(LocalDate dataInicio, LocalDate dataFim) throws DataInvalidaException {
        List<Livro> lista = new ArrayList<>();

        if(dataInicio == null ){
            dataInicio = LocalDate.MIN;
        }
        if(dataFim == null){
            dataFim = LocalDate.now();
        }

        if (dataInicio.isAfter(dataFim)) {
            throw new DataInvalidaException("'Data de início' ou 'Data de fim' inválida(s)");
        }

        for (Livro livro : livros) {
            Map<LocalDate, Integer> registroDoEstoque = livro.getRegistroAtualizacaoEstoque();

            for (Map.Entry<LocalDate, Integer> entry : registroDoEstoque.entrySet()) {
                LocalDate dataAtualizacao = entry.getKey();

                if ((!dataAtualizacao.isBefore(dataInicio) || dataAtualizacao.isEqual(dataInicio)) &&
                        (!dataAtualizacao.isAfter(dataFim) || dataAtualizacao.isEqual(dataFim))) {
                    lista.add(livro);
                }
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

    private boolean verificarLivrosComTitulosIguais(Livro livro){
        boolean temtituloIgual = false;

        for (int i = 0; i < livros.size() && !temtituloIgual; i++){
            if(livros.get(i).getTitulo().equals(livro.getTitulo())){
                if(!livros.get(i).getId().equals(livro.getId())){
                    temtituloIgual = true;
                }
            }
        }
        return temtituloIgual;
    }

    public List<Livro> listarLivrosComEstoqueDisponivel() {
        List<Livro> livrosComEstoque = new ArrayList<>();

        for (Livro livro : livros) {
            if (livro.getQuantidade() >= 1) {
                livrosComEstoque.add(livro);
            }
        }

        return livrosComEstoque;
    }


    private static RepositorioLivro lerDoArquivo() {
        RepositorioLivro instanciaLocal = null;

        File in = new File("RepoLivros.dat");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(in);
            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            instanciaLocal = (RepositorioLivro) o;
        } catch (Exception e) {
            instanciaLocal = new RepositorioLivro();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {/* Silent exception */
                }
            }
        }

        return instanciaLocal;
    }

    @Override
    public void salvarArquivo() {
        if (instance == null) {
            return;
        }
        File out = new File("RepoLivros.dat");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(out);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    /* Silent */}
            }
        }
    }
}
