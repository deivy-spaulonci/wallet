import {api} from "@/core/service/api.ts";
import type {FormaPagamento} from "@/features/FormaPagamento/model/FormaPagamento.ts";

const BASE = '/forma-pagamento'

export const formatPagamentoService = {
    async findAll(): Promise<FormaPagamento[]> {
        const response = await api.get<FormaPagamento[]>(BASE);
        return response.data;
  },

  async findOne(params: any): Promise<FormaPagamento> {
    const response = await api.get<FormaPagamento>(BASE+'/uuid', { params: params });
    return response.data;
  }
}
