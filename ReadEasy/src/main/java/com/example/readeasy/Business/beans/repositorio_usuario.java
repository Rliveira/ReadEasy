package com.example.readeasy.Business.beans

import java.util.ArrayList;
import java.util.List;

// Classe que representa um repositório de usuários
public class repositorio_usuario {
    private List<Usuario> usuarios;  // Lista para armazenar os usuários

    // Construtor que inicializa a lista de usuários
    public repositorio_usuario() {
        this.usuarios = new ArrayList<>();
    }

    // Método para adicionar um usuário à lista
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    // Método para remover um usuário da lista
    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
    }

    // Método para buscar um usuário pelo login na lista
    public Usuario buscarUsuarioPorLogin(String login) {
        for (Usuario usuario : usuarios) {
            if (usuario.getLogin().equals(login)) {
                return usuario;
            }
        }
        return null;  // Retorna null se o usuário não for encontrado
    }

    // Outros métodos conforme necessário

    // Método para obter a lista completa de usuários
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}