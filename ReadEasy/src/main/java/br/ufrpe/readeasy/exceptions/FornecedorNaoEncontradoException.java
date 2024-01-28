package br.ufrpe.readeasy.exceptions;

public class FornecedorNaoEncontradoException extends RuntimeException {
    public FornecedorNaoEncontradoException() {
        super("O fornecedor em questão não possui nenhum livro relacionado a ele na livraria");
    }
}