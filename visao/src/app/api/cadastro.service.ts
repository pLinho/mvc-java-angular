import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Pessoa } from './model/pessoa';
import { PessoaFisica } from './model/pessoa-fisica';
import { PessoaJuridica } from './model/pessoa-juridica';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {
  API_URL = 'http://localhost:8080/cadastro/api/pessoa';
  constructor(
    private http: HttpClient
  ) { }

  async oberLista(query: string) {
    const result = await this.http.get<Pessoa[]>(this.API_URL + '/obterLista', {
      params: {
        query,
      }
    }).toPromise();

    return result.map(
      result_raw => {
        const result: Pessoa = this.pessoaFromJson(result_raw);
        console.log(result);
        return result;
      }
    );
  }
  async obter(cnpjCpf: string) {
    const result = await this.http.get<Pessoa>(this.API_URL + '/obter', {
      params: {
        cnpj_cpf: cnpjCpf,
      }
    }).toPromise();

  }
  async inserir(pessoa: Pessoa) {
    await this.http.post<void>(this.API_URL + '/inserir', pessoa).toPromise();

  }
  async atualizar(pessoa: Pessoa) {
    await this.http.post<void>(this.API_URL + '/atualizar', pessoa).toPromise();
  }

  pessoaFromJson(raw: any) {
    if (raw['cpf']) {
      const resulSerializado = new PessoaFisica();
      resulSerializado.fromJSON(raw);
      return resulSerializado;
    } else if (raw['cnpj']) {
      const resulSerializado = new PessoaJuridica();
      resulSerializado.fromJSON(raw);
      return resulSerializado;
    } else {
      const resulSerializado = new PessoaFisica();
      resulSerializado.fromJSON(raw);
      return resulSerializado;
    }
  }
}
