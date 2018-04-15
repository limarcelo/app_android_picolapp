package br.com.projetofinal.iceapp.manager;

import java.util.ArrayList;
import java.util.List;

import br.com.projetofinal.iceapp.model.Usuario;

/**
 * Created by Marcelo on 31/10/2016.
 */
public class GerenciadorUsuario {

    private static Usuario usuario = new Usuario();
    private static List<Usuario> todosUsuarios = new ArrayList<>();

    public static void adicionarUsuario(Usuario usuarioAdicionado){
        usuario = usuarioAdicionado;
    }
    public static Usuario getUsuario(){
        return usuario;
    }

    public static void adicionar(Usuario usuario){
        todosUsuarios.add(usuario);
    }

    public static List<Usuario> getListaUsuarios(){
        return todosUsuarios;
    }
    public static void removerTodos() {
        todosUsuarios.clear();
    }


    public static void logoff() {
        usuario = null;
    }
}
