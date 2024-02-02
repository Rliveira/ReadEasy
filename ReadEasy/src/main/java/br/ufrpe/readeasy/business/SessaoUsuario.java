package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;

public class SessaoUsuario {
    public static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        SessaoUsuario.usuarioLogado = usuarioLogado;
    }
}
