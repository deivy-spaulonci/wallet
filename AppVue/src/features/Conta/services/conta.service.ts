import type { Response } from "@/core/type/Response.ts";
import { api } from "@/core/service/api.ts";
import type { ContaResponse } from "@/features/Conta/model/ContaResponse";
import type { Conta } from "@/features/Conta/model/Conta";

const BASE = "/conta";

export const contaService = {
  async findAll(params: any): Promise<Response<ContaResponse>> {
    const response = await api.get<Response<ContaResponse>>(BASE, {
      params: params,
    });
    return response.data;
  },

  async valorTotal(params: any): Promise<number> {
    const response = await api.get<number>(BASE + "/valorTotal", {
      params: params,
    });
    return response.data;
  },

  async findOne(params: any): Promise<Conta> {
    const response = await api.get<Conta>(BASE + "/uuid", { params: params });
    return response.data;
  },

  async save(conta: Omit<Conta, "uuid">): Promise<Conta> {
    const response = await api.post<Conta>(BASE, conta);
    return response.data;
  },

  async update(conta: Partial<Conta>): Promise<Conta> {
      const response = await api.put<Conta>(BASE,conta);
      return response.data;
  },

  async setContaPaga(params: any): Promise<Conta> {
    const response = await api.put(BASE + "/setContaPaga", params);
    return response.data;
  },

  async getTotalPorTipoConta(): Promise<any[]> {
    const response = await api.get<any[]>(BASE+'/totalPorTipo');
    return response.data;
  },

  async getTotalPorsStatus(): Promise<any[]> {
    const response = await api.get<any[]>(BASE+'/totaisStatus');
    return response.data;
  },

  async deleteConta(uuid: string): Promise<void> {
    await api.delete(BASE+'/'+uuid);
  },
};
