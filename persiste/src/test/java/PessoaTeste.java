/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template , choose Tools | Templates
 * and open the template in the editor.
 */

import br.com.nsinova.curso.dominio.Pessoa;
import br.com.nsinova.curso.dominio.PessoaFisica;
import br.com.nsinova.curso.persiste.postgres.PessoaPersiste;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author paulo
 */
public class PessoaTeste {

    Connection conexao;

    public PessoaTeste() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso?"
                    + "user=postgres&password=postgres");
        } catch (ClassNotFoundException ex) {
            throw new Exception("Classe não encontrada", ex);
        } catch (SQLException ex) {
            throw new Exception("Erro ao iniciar conexão", ex);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    @Test
    public void inserirPessoa() throws Exception {
        try {
            PessoaPersiste pessoa_persiste = new PessoaPersiste(conexao);

            PessoaFisica pessoa_teste = new PessoaFisica();
            pessoa_teste.setNome_completo("Paulinho das Coves");
            pessoa_teste.setCpf("08589541924");
            pessoa_teste.setData_nascimento(new Date());

            pessoa_persiste.inserir(pessoa_teste);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Test
    public void obterPessoa() throws Exception {
        try {
            PessoaPersiste pessoa_persiste = new PessoaPersiste(conexao);
            Pessoa pessoa = pessoa_persiste.obter("08589541924");

            System.out.println("Pessoa encontrada: " + pessoa.getNome_completo());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
