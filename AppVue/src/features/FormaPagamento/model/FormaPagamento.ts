import type {Modalidade} from "@/features/FormaPagamento/model/Modalidade.ts";

export interface FormaPagamento {
    uuid:string;
    modalidade: Modalidade;
    instituicao: string;
    descricao: string;
}