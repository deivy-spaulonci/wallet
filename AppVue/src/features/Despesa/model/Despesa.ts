import type { Fornecedor } from "@/features/Fornecedor/model/fornecedor";
import type { TipoDespesa } from "./TipoDespesa";
import type { FormaPagamento } from "@/features/FormaPagamento/model/FormaPagamento";

export interface Despesa{
    uuid:string;
    tipoDespesa:TipoDespesa;
    fornecedor:Fornecedor;
    dataPagamento:Date;
  formaPagamento: FormaPagamento;
    valor:number;
    observacao: string;
}
