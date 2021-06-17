import { Component, OnInit } from '@angular/core';
import { CadastroService } from '../api/cadastro.service';
import { Pessoa } from '../api/model/pessoa';

@Component({
  selector: 'ns-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss']
})
export class CadastroComponent implements OnInit {
  pesquisa = '';
  pessoas: Pessoa[];
  selecionado: Pessoa;
  constructor(
    private cadastroService: CadastroService,
  ) { }

  ngOnInit() {

  }

  async obterLista() {
    this.pessoas = await this.cadastroService.oberLista(this.pesquisa);
  }

}
