/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nsinova.curso.persiste;

import br.com.nsinova.curso.dominio.Pessoa;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author paulo
 */
public interface IPessoa {

    public List<Pessoa> obterLista(String query) throws Exception;

    public Pessoa obter(String cnpjCpf) throws Exception;

    public int inserir(Pessoa pessoa) throws Exception;

    public int atualizar(Pessoa pessoa) throws Exception;

    public int deletar(String cnpjCpf) throws Exception;
}
