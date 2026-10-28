import {api} from "@/core/service/api.ts";
import type {Fornecedor} from "@/features/Fornecedor/model/fornecedor.ts";

export const fornecedorService = {

    async findAll(params:any): Promisse<Fornecedor[]> {

        const response = await api.get<Fornecedor[]>('/pessoa', {
            params: params
        });
        return response.data;
    }
}