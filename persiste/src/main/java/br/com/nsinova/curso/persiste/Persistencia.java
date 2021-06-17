/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nsinova.curso.persiste;

import java.sql.Connection;

/**
 *
 * @author paulo
 */
public class Persistencia {
    private final Connection conexao;
    public Persistencia(Connection conexao) {
        this.conexao = conexao;
    }
    public Connection getConexao() {
        return conexao;
    }
}
