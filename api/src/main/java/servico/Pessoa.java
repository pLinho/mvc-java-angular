/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import br.com.nsinova.curso.dominio.PessoaFisica;
import br.com.nsinova.curso.dominio.PessoaJuridica;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import servico.dto.PessoaDto;

/**
 * REST Web Service
 *
 * @author ricardo.tonetti
 */
@Path("pessoa")
public class Pessoa {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public Pessoa() {

    }

    /**
     * Retrieves representation of an instance of servico.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("obter")
    @Produces(MediaType.APPLICATION_JSON)
    public PessoaDto obter(@QueryParam("cnpj_cpf") String cnpj_cpf) throws Exception {
        Class.forName("org.postgresql.Driver").newInstance();
        Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso?"
                + "user=postgres&password=postgres");
        br.com.nsinova.curso.negocio.Pessoa pessoa_negocio
                = new br.com.nsinova.curso.negocio.Pessoa(conexao);
        br.com.nsinova.curso.dominio.Pessoa pessoa = pessoa_negocio.obter(cnpj_cpf);
        PessoaDto dto = new PessoaDto();
        dto.setNome_completo(pessoa.getNome_completo());
        dto.setData_nascimento(pessoa.getData_nascimento());
        dto.setCnpj_cpf(pessoa instanceof PessoaFisica
                ? ((PessoaFisica) pessoa).getCpf()
                : ((PessoaJuridica) pessoa).getCnpj());

        return dto;
    }

    @GET
    @Path("obterLista")
    @Produces(MediaType.APPLICATION_JSON)
    public List<br.com.nsinova.curso.dominio.Pessoa> obterLista(@QueryParam("query") String query) throws Exception {
        Class.forName("org.postgresql.Driver").newInstance();
        Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso?"
                + "user=postgres&password=postgres");
        br.com.nsinova.curso.negocio.Pessoa pessoa_negocio
                = new br.com.nsinova.curso.negocio.Pessoa(conexao);
        List<br.com.nsinova.curso.dominio.Pessoa> lista = pessoa_negocio.obterLista(query);

        return lista;
    }

    @POST
    @Path("inserir")
    @Produces(MediaType.APPLICATION_JSON)
    public void inserir(br.com.nsinova.curso.dominio.Pessoa pessoa) throws Exception {
        Class.forName("org.postgresql.Driver").newInstance();
        Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso?"
                + "user=postgres&password=postgres");
        br.com.nsinova.curso.negocio.Pessoa pessoa_negocio
                = new br.com.nsinova.curso.negocio.Pessoa(conexao);
        pessoa_negocio.inserir(pessoa);
    }

    @POST
    @Path("atualizar")
    @Produces(MediaType.APPLICATION_JSON)
    public void atualizar(br.com.nsinova.curso.dominio.Pessoa pessoa) throws Exception {
        Class.forName("org.postgresql.Driver").newInstance();
        Connection conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/curso?"
                + "user=postgres&password=postgres");
        br.com.nsinova.curso.negocio.Pessoa pessoa_negocio
                = new br.com.nsinova.curso.negocio.Pessoa(conexao);
        pessoa_negocio.atualizar(pessoa);
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
