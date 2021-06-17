/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nsinova.curso.negocio;

import br.com.nsinova.curso.dominio.PessoaFisica;
import br.com.nsinova.curso.dominio.PessoaJuridica;
import br.com.nsinova.curso.persiste.postgres.PessoaPersiste;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Pessoa {

    PessoaPersiste pessoaPersiste;

    public Pessoa(Connection conexao) {
        this.pessoaPersiste = new PessoaPersiste(conexao);
    }

    public br.com.nsinova.curso.dominio.Pessoa obter(String cnpjCpf) throws Exception {
        return this.pessoaPersiste.obter(cnpjCpf);
    }

    public List<br.com.nsinova.curso.dominio.Pessoa> obterLista(String query) throws Exception {
        return this.pessoaPersiste.obterLista(query);
    }

    public void inserir(br.com.nsinova.curso.dominio.Pessoa pessoa) throws Exception {
        this.validar(pessoa);
        this.pessoaPersiste.inserir(pessoa);
    }

    public void atualizar(br.com.nsinova.curso.dominio.Pessoa pessoa) throws Exception {
        this.validar(pessoa);
        this.pessoaPersiste.atualizar(pessoa);
    }

    private void validar(br.com.nsinova.curso.dominio.Pessoa pessoa) throws Exception {
        if (pessoa instanceof PessoaFisica) {
            if (((PessoaFisica) pessoa).getCpf() == null
                    || ((PessoaFisica) pessoa).getCpf().length() != 11) {
                throw new Exception("Informar CPF válido");
            }
        }
        if (pessoa instanceof PessoaJuridica) {
            if (((PessoaJuridica) pessoa).getCnpj() == null
                    || ((PessoaJuridica) pessoa).getCnpj().length() != 11) {
                throw new Exception("Informar CPF válido");
            }
        }
    }
}
