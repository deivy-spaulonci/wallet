import type { FormaPagamento } from "@/features/FormaPagamento/model/FormaPagamento";
import type { TipoConta } from "./TipoConta";

export interface Conta{
    uuid:string;
    codigoBarras: string;
    tipoConta: TipoConta;
    emissao: Date;
    vencimento: Date;
    parcela: number;
    totalParcela: number;
    valor: number;
    dataPagamento: Date;
    formaPagamento: FormaPagamento;
    valorPago: number;
    titulo: string;
    comprovante: string;
    observacao:string;
    status:string;
}