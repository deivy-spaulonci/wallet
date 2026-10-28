import {api} from "@/core/service/api.ts";
import type { TipoConta } from "@/features/Conta/model/TipoConta";


export const tipoContaService ={
        async findAll(): Promise<TipoConta[]> {
    
            const response = await api.get<TipoConta[]>('/tipo-conta');
    
            return response.data;
        }
}