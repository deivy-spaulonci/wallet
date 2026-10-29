import type {Response} from "@/core/type/Response.ts";
import type {DespesaResponse} from "@/features/Despesa/model/DespesaResponse";
import {api} from "@/core/service/api.ts";
import type { Despesa } from "../model/Despesa";

const BASE = "/despesa";

export const despesaService = {

    async findAll(params:any): Promise<Response<DespesaResponse>> {
        const response = await api.get<Response<DespesaResponse>>(BASE, {
            params: params
        });
        return response.data;
    },

    async valorTotal(params:any): Promise<number> {
        const response = await api.get<number>(BASE+'/valorTotal', {
            params: params
        });
        return response.data;
    },

    async save(despesa: Omit<Despesa, 'uuid'>): Promise<Despesa> {
        const response = await api.post<Despesa>(BASE,despesa);
        return response.data;
    },

    async update(despesa: Partial<Despesa>): Promise<Despesa> {
        const response = await api.put<Despesa>(BASE,despesa);
        return response.data;
    },

    async deleteDespesa(uuid: string): Promise<void> {
      await api.delete(BASE+'/'+uuid);
    },

    async findOne(params:any): Promise<Despesa> {
        const response = await api.get<Despesa>(BASE+'/uuid',{params: params});
        return response.data;
    },

    async getTotalPorTipoDespesa(): Promise<any[]>{
        const reponse = await api.get<any[]>(BASE+'/totalPorTipo');
        return reponse.data;
    },

    async getTotalPorAnoDespesa(): Promise<any[]>{
        const reponse = await api.get<any[]>(BASE+'/totalPorAno');
        return reponse.data;
    },

    async getTotalPorMesDespesa(params:any): Promise<any[]>{
        const reponse = await api.get<any[]>(BASE+'/totalPorMes',{
            params: params
        });
        return reponse.data;
    },

};
