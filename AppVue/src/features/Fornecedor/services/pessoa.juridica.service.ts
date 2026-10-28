import { api } from "@/core/service/api.ts";
import type { PessoaJuridica } from "@/features/Fornecedor/model/pessoaJuridica";

export const pessoaJuridicaService = {

    async findAll(params: any): Promisse<PessoaJuridica[]> {
        const response = await api.get<PessoaJuridica[]>('/pessoa-juridica', {
            params: params
        })
        return response.data;
    },

    async buscarCnpj(cnpj: string) {
        const numeros = String(cnpj).replace(/\D/g, '')
        const RECEITA_CLOUD = 'https://publica.cnpj.ws/cnpj/'
        const resposta = await fetch(`${RECEITA_CLOUD}${numeros}`)

        if (!resposta.ok) {
            throw new Error(`Erro ao consultar CNPJ (HTTP ${resposta.status}).`)
        }

        return resposta.json()
    },

    async save(pessoaJuridica: Omit<PessoaJuridica, 'uuid'>): Promise<PessoaJuridica> {
        const response = await api.post<PessoaJuridica>(
            '/pessoa-juridica',
            pessoaJuridica
        );

        return response.data;
    },
}