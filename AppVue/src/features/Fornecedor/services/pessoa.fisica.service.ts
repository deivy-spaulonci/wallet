import { api } from "@/core/service/api.ts";
import type { PessoaFisica } from "../model/pessoaFisica";
import type { Cidade } from "../model/cidade";


export const pessoaFisicaService = {
        async findAll(params: any): Promisse<PessoaFisica[]> {
            const response = await api.get<PessoaFisica[]>('/pessoa-fisica', {
                params: params
            })
            return response.data;
        },

        async findCidadePorUf(uf: string): Promisse<Cidade[]> {
            let url = `https://servicodados.ibge.gov.br/api/v1/localidades/estados/${uf}/municipios?orderBy=nome`;
            const response = await api.get<Cidade[]>(url)
            return response.data;
        }
}