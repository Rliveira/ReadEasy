package br.ufrpe.readeasy.gui;

import br.ufrpe.readeasy.beans.Usuario;

public class SessaoUsuario

{
    public static SessaoUsuario instance;
    public static Usuario usuarioLogado;

    public static synchronized SessaoUsuario getInstance()
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

    public static synchronized void setUsuarioLogado(Usuario usuario)
    {
        usuarioLogado = usuario;
    }

    public static void logOut()
    {
        usuarioLogado = null;
    }

}
