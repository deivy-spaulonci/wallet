import { ref } from 'vue';
import type { TipoDespesa } from "@/features/Despesa/model/TipoDespesa.ts";
import { tipoDespesaService } from "@/features/Despesa/services/tipo.despesa.service.ts";
import { useToast } from 'primevue/usetoast';
import { fornecedorService } from "@/features/Fornecedor/services/fornecedor.services.ts";
import type { Fornecedor } from "@/features/Fornecedor/model/fornecedor.ts";
import type { FormaPagamento } from "@/features/FormaPagamento/model/FormaPagamento.ts";
import { formatPagamentoService } from "@/features/FormaPagamento/service/forma.pagamento.service.ts";
import { DateUtil } from "@/core/util/DateUtil.ts";

export function filterDespesaView(emit:any) {    

    const selectedTipoDespesa = ref([]);
    const selectedFornecedor = ref({});
    const selectedFormaPgto = ref({});
    const tipos = ref<TipoDespesa[]>([]);
    const fornecedores = ref<Fornecedor[]>([]);
    const formas = ref<FormaPagamento[]>([])
    const dataInicial = ref();
    const dataFinal = ref();
    const mesDate = ref<Date>();
    const opcPeriodo = ref('periodo');
    const toast = useToast();

    function filtrar() {

        let inicioParam;
        let terminoParam;

        if (opcPeriodo.value == 'mes' && mesDate.value) {
            const ano = mesDate.value.getFullYear();
            const mes = mesDate.value.getMonth();

            const inicio = new Date(ano, mes, 1);
            const fim = new Date(ano, mes + 1, 0);

            inicioParam = DateUtil.fromDateToData(inicio);
            terminoParam = DateUtil.fromDateToData(fim);
        } else {
            inicioParam = DateUtil.toApiDate(dataInicial.value);
            terminoParam = DateUtil.toApiDate(dataFinal.value);
        }

        emit('filtrar',
            selectedTipoDespesa.value,
            selectedFornecedor.value,
            inicioParam,
            terminoParam,
            selectedFormaPgto.value,
        );
    }

    async function carregarTipos() {
        try {
            tipos.value = await tipoDespesaService.findAll();
            //tipos.value = [{nome:'Selecione', value:''}, ...tipos.value]
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar tipos.', life: 3000 });
        }
    }

    async function carregarFormas() {
        try {
            formas.value = await formatPagamentoService.findAll();
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar formas de pagamento.', life: 3000 });
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

    function limparFiltros() {
        selectedTipoDespesa.value = [];
        selectedFornecedor.value = {};
        dataInicial.value = '';
        dataFinal.value = '';
        mesDate.value = undefined;
        selectedFormaPgto.value = {};
        filtrar();
    }

    return {selectedTipoDespesa, selectedFornecedor, selectedFormaPgto, tipos, fornecedores, formas, dataInicial, 
        dataFinal, mesDate, opcPeriodo, carregarTipos, carregarFormas, search, limparFiltros, filtrar
    }
}