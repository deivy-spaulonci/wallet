export class FormatUtil{
    static floatToNumberBR(valor:number = 0): string{        
        if(valor)
            return new Intl.NumberFormat('pt-BR', {
                        style: 'currency', 
                        currency: 'BRL'}
                    ).format(valor);
       return '0';
    }

    static floatToNumberBRNoSymbol(valor:number = 0): string{        
        if(valor)
            return new Intl.NumberFormat('pt-BR', {
                        style: 'decimal', 
                        minimumFractionDigits: 2, 
                        maximumFractionDigits: 2}
                    ).format(valor);
       return '0';
    }
}