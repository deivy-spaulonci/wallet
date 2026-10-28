import {api} from "@/core/service/api.ts";
import type {TipoDespesa} from "@/features/Despesa/model/TipoDespesa.ts";

export const tipoDespesaService = {

    async findAll(): Promise<TipoDespesa[]> {

        const response = await api.get<TipoDespesa[]>('/tipo-despesa');

        return response.data;
    }
}