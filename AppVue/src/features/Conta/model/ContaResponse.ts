export interface ContaResponse {    
    nome: string;    
    vencimento: Date;
    parcela: number;
    totalParcela: number;
    valor: number;
    status: string;
    intStatus: number;
}