import { format } from 'date-fns';

export abstract class Pessoa {
    private nome_completo: string;
    private data_nascimento: Date;
    set nomeCompleto(value: string) {
        this.nome_completo = value;
    }
    set dataNascimento(value: Date) {
        if (typeof value === 'string') {
            this.data_nascimento = new Date(value);
        }
        this.data_nascimento = value;
    }
    get nomeCompleto() {
        return this.nome_completo;
    }
    get dataNascimento() {
        return this.data_nascimento;
    }

    toJSON() {
        const objReturn = {
            data_nascimento: this.dataNascimento ? format(this.dataNascimento, "yyyy-MM-dd'T'HH:mm:ssxxx") : null,
            nome_completo: this.nomeCompleto
        };

        return objReturn;
    }
    fromJSON(raw: Object) {
        this.dataNascimento = new Date(raw['data_nascimento']);
        this.nomeCompleto = raw['nome_completo'];
    }
}