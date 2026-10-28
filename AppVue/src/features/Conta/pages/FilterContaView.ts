import { ref } from 'vue';
import type { TipoConta } from '@/features/Conta/model/TipoConta';
import { tipoContaService } from "@/features/Conta/services/tipo.conta.service.ts";
import { useToast } from 'primevue/usetoast';
import { DateUtil } from '@/core/util/DateUtil';

export function filterContaView(emit: any) {
    const tipos = ref<TipoConta[]>([]);
    const selectedTipoConta = ref({});
    const vencimentoInicial = ref();
    const vencimentoFinal = ref();
    const mesDate = ref<Date>();
    const opcPeriodo = ref('periodo');
    const toast = useToast();
    const statusSelected = ref(null);
    const statusArray = ref([
        {label:'Todos', value:''},
        {label:'Vencimento Hoje', value:'HOJE'},
        {label:'Em Aberto', value:'ABERTO'},
        {label:'Atrasados', value:'ATRASADO'},
        {label:'Pagos', value:'PAGO'},
    ])

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
            inicioParam = DateUtil.toApiDate(vencimentoInicial.value);
            terminoParam = DateUtil.toApiDate(vencimentoFinal.value);
        }

        emit('filtrar',
            selectedTipoConta.value,
            inicioParam,
            terminoParam,
            statusSelected.value,
        );
    }

    async function carregarTipos() {
        try {
            tipos.value = await tipoContaService.findAll();
        } catch (e) {
            toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar tipos.', life: 3000 });
        }
    }

    function limparFiltros() {
        statusSelected.value = null;
        vencimentoInicial.value = '';
        vencimentoFinal.value = '';
        mesDate.value = undefined;
        selectedTipoConta.value = {};
        filtrar();
    }

    return {
        tipos, selectedTipoConta, vencimentoFinal, vencimentoInicial, mesDate, opcPeriodo, 
        statusSelected, statusArray,
        carregarTipos, limparFiltros, filtrar
    }
}