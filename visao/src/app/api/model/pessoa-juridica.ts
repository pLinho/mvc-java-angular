import { Pessoa } from './pessoa';

export class PessoaJuridica extends Pessoa {
    private _cnpj: string;

    set cnpj(value: string) {
        this._cnpj = value;
    }
    get cnpj() {
        return this._cnpj;
    }

    toJSON() {
        const objReturn = super.toJSON();
        objReturn['cnpj'] = this.cnpj;
        return objReturn;
    }
    fromJSON(raw: Object) {
        this.cnpj = raw['cnpj'];
        super.fromJSON(raw);
    }

}