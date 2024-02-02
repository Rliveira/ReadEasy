package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;

public class SessaoUsuario

{
    public static SessaoUsuario instance;
    public static Usuario usuarioLogado;

    public static SessaoUsuario getInstance()
    {
        if(instance == null)
        {
            instance = new SessaoUsuario();
        }
        return instance;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuarioLogado) {
        SessaoUsuario.usuarioLogado = usuarioLogado;
    }
}
