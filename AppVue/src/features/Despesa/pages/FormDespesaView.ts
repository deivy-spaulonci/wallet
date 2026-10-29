import { DateUtil } from "@/core/util/DateUtil";
import { FormatUtil } from "@/core/util/FormatUtil";
import type { TipoDespesa } from "@/features/Despesa/model/TipoDespesa.ts";
import { tipoDespesaService } from "@/features/Despesa/services/tipo.despesa.service.ts";
import type { Fornecedor } from "@/features/Fornecedor/model/fornecedor.ts";
import { fornecedorService } from "@/features/Fornecedor/services/fornecedor.services.ts";
import { zodResolver } from '@primevue/forms/resolvers/zod';
import { useToast } from "primevue/usetoast";
import { onMounted, ref } from "vue";
import { z } from 'zod';
import { despesaService } from "../services/despesa.service";
import type { Despesa } from "../model/Despesa";

export function formDespesaView(idEdicao: string | undefined, emit: any) {

    const selectedTipoDespesa = ref({});
    const tipos = ref<TipoDespesa[]>([]);
    const fornecedores = ref<Fornecedor[]>([]);
    const toast = useToast();
    const carregando = ref(false)
    const formRef = ref<any>(null)
    const initialValues = ref<any>({
        fornecedor: null,
        formaPagamento: null,
        tipoDespesa: null,
        dataPagamento: '',
        valor: '',
        observacao: ''
    });
    const resolver = zodResolver(
        z.object({
            fornecedor: z.object({ uuid: z.string().min(1) }).nullable().refine((Fornecedor) => Fornecedor !== null, { message: 'Informe o fornecedor!' }),
            tipoDespesa: z.object({ uuid: z.string().min(1) }).nullable().refine((TipoDespesa) => TipoDespesa !== null, { message: 'Informe o tipo de despesa!' }),
            dataPagamento: z.string().trim().min(10, 'Informe a data pagamento!'),
            valor: z.string().trim().min(1, 'Informe um valor!'),
            observacao: z.string().optional(),
            formaPagamento: z.object({ uuid: z.string().min(1) }).nullable().refine((FormaPagamento) => FormaPagamento !== null, { message: 'Informe uma forma pagamento!' }),
        })
    )

    async function carregarTipos() {
        try {
            tipos.value = await tipoDespesaService.findAll();
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar tipos.', life: 3000 });
        }
    }

    async function salvar({ valid, values }) {

        if (!valid) return

        const [dia, mes, ano] = values.dataPagamento!.split('/');
        values.dataPagamento = `${ano}-${mes}-${dia}`;
        values.valor = values.valor.replace(/\./g, '').replace(',', '.');

        try {
            if(!idEdicao){
                console.log('salvando nova despesa...')
                despesaService.save(values);
            }else{
                values = {...values, uuid: idEdicao}
                console.log('salvando edicao despesa...')
                despesaService.update(values);

            }
            initialValues.value.dataPagamento = '';
            initialValues.value.valor = '0';
            initialValues.value.fornecedor = null;
            formRef.value?.reset();

            toast.add({ severity: 'success', summary: 'Sucesso!', detail: 'Despesa salva.', life: 3000 });
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao salvar despesa.', life: 3000 });
        } finally{
            emit("closeDialog");
        }

    }

    const search = async (event: any) => {
        try {
            const query = event.query.toLowerCase();
            fornecedores.value = await fornecedorService.findAll({ busca: query })
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar fornecedores.', life: 3000 });
        }
    };

    async function checkEdicao() {
        if (!idEdicao) return
        carregando.value = true

        try{
            const despesa: Despesa = await despesaService.findOne({uuid: idEdicao});
            initialValues.value = despesa;
            selectedTipoDespesa.value = despesa.tipoDespesa;
            initialValues.value.valor = FormatUtil.floatToNumberBRNoSymbol(despesa.valor);
            initialValues.value.dataPagamento = DateUtil.toFormDate(despesa.dataPagamento+'');
        }catch (e){
            console.log(e);
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Houve um erro ao carregar a Despesa', life: 3000 });
        } finally {
           carregando.value = false
        }
    }

    return {
        selectedTipoDespesa,
        tipos,
        fornecedores,
        initialValues,
        carregando,
        formRef,
        resolver,
        salvar,
        search,
        carregarTipos,
        onMounted,
        checkEdicao
    }
}
