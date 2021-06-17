import { Pessoa } from './pessoa';

export class PessoaFisica extends Pessoa {
    private _cpf: string;

    set cpf(value: string) {
        this._cpf = value;
    }
    get cpf() {
        return this._cpf;
    }

    toJSON() {
        const objReturn = super.toJSON();
        objReturn['cpf'] = this.cpf;
        return objReturn;
    }
    fromJSON(raw: Object) {
        this.cpf = raw['cpf'];
        super.fromJSON(raw);
    }

}