/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.nsinova.curso.persiste.postgres;

import br.com.nsinova.curso.dominio.Pessoa;
import br.com.nsinova.curso.dominio.PessoaFisica;
import br.com.nsinova.curso.dominio.PessoaJuridica;
import br.com.nsinova.curso.persiste.IPessoa;
import br.com.nsinova.curso.persiste.Persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class PessoaPersiste extends Persistencia implements IPessoa {

    public PessoaPersiste(Connection conexao) {
        super(conexao);
    }

    @Override
    public List<Pessoa> obterLista(String query) throws Exception {
        String comando = "SELECT nome_completo, cnpj_cpf, data_nascimento "
                + "FROM public.pessoa WHERE nome_completo ilike ?";
        PreparedStatement statement = this.getConexao().prepareStatement(comando);
        statement.setString(1, "%" + query + "%");
        ResultSet result = statement.executeQuery();

        List<Pessoa> lista = new ArrayList<>();

        while (result.next()) {
            Pessoa pessoa;
            String cnpjCpf = result.getString("cnpj_cpf");
            if (cnpjCpf.length() == 11) {
                pessoa = new PessoaFisica();
                ((PessoaFisica) pessoa).setCpf(cnpjCpf);
            } else {
                pessoa = new PessoaJuridica();
                ((PessoaJuridica) pessoa).setCnpj(cnpjCpf);
            }
            pessoa.setNome_completo(result.getString("nome_completo"));
            pessoa.setData_nascimento(result.getTimestamp("data_nascimento"));

            lista.add(pessoa);
        }

        return lista;
    }

    @Override
    public Pessoa obter(String cnpjCpf) throws Exception {
        if (cnpjCpf == null || (cnpjCpf.length() != 11 && cnpjCpf.length() != 14)) {
            throw new Exception("Informe um cnpj ou cpf");
        }

        String comando = "SELECT nome_completo, cnpj_cpf, data_nascimento "
                + "FROM public.pessoa WHERE cnpj_cpf=?";
        PreparedStatement statement = this.getConexao().prepareStatement(comando);
        statement.setString(1, cnpjCpf);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            Pessoa pessoa;
            if (cnpjCpf.length() == 11) {
                pessoa = new PessoaFisica();
                ((PessoaFisica) pessoa).setCpf(cnpjCpf);
            } else {
                pessoa = new PessoaJuridica();
                ((PessoaJuridica) pessoa).setCnpj(cnpjCpf);
            }
            pessoa.setNome_completo(result.getString("nome_completo"));
            pessoa.setData_nascimento(result.getTimestamp("data_nascimento"));

            return pessoa;
        } else {
            return null;
        }
    }

    @Override
    public int inserir(Pessoa pessoa) throws Exception {
        String comando_sql = "insert into public.pessoa( "
                + "nome_completo, cnpj_cpf, data_nascimento) "
                + "VALUES (?, ?, ?)";

        PreparedStatement statement = this.getConexao().prepareStatement(comando_sql);
        statement.setString(1, pessoa.getNome_completo());

        if (pessoa instanceof PessoaFisica) {
            if (((PessoaFisica) pessoa).getCpf().length() != 11) {
                throw new Exception("Cpf em Pessoa Física inválido");
            }
            statement.setString(2, ((PessoaFisica) pessoa).getCpf());
        } else if (pessoa instanceof PessoaJuridica) {
            if (((PessoaJuridica) pessoa).getCnpj().length() != 14) {
                throw new Exception("Cnpj em Pessoa Jurídica inválido");
            }
            statement.setString(2, ((PessoaJuridica) pessoa).getCnpj());
        }

        if (pessoa.getData_nascimento() != null) {
            statement.setTimestamp(3, new Timestamp(pessoa.getData_nascimento().getTime()));
        } else {
            statement.setNull(3, java.sql.Types.TIMESTAMP);
        }

        int result = 0;
        try {
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Erro ao inserir pessoa", ex);
        }
        if (result == 0) {
            throw new Exception("Nenhum dado inserido.");
        }
        return result;
    }

    @Override
    public int atualizar(Pessoa pessoa) throws Exception {
        String comando_sql = "update public.pessoa "
                + "SET nome_completo=?, data_nascimento=?) "
                + "WHERE cnpj_cpf=?";

        PreparedStatement statement = this.getConexao().prepareStatement(comando_sql);
        statement.setString(1, pessoa.getNome_completo());

        if (pessoa.getData_nascimento() != null) {
            statement.setTimestamp(2, new Timestamp(pessoa.getData_nascimento().getTime()));
        } else {
            statement.setNull(2, java.sql.Types.TIMESTAMP);
        }

        if (pessoa instanceof PessoaFisica) {
            statement.setString(3, ((PessoaFisica) pessoa).getCpf());
        } else if (pessoa instanceof PessoaJuridica) {
            statement.setString(3, ((PessoaJuridica) pessoa).getCnpj());
        }

        int result = 0;
        try {
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Erro ao atualizar pessoa.", ex);
        }
        if (result == 0) {
            throw new Exception("Nenhum dado inserido.");
        }
        return result;
    }

    @Override
    public int deletar(String cnpjCpf) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
