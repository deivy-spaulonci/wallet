import type { Response } from "@/core/type/Response.ts";
import { tableUtil } from "@/core/util/TableUtil";
import type { DespesaResponse } from "@/features/Despesa/model/DespesaResponse.ts";
import { despesaService } from '@/features/Despesa/services/despesa.service.ts';
import type { FormaPagamento } from "@/features/FormaPagamento/model/FormaPagamento.ts";
import type { Fornecedor } from "@/features/Fornecedor/model/fornecedor.ts";
import ExclamationTriangle from '@primeicons/vue/exclamation-triangle';
import Pencil from '@primeicons/vue/pencil';
import Trash from '@primeicons/vue/trash';
import { useConfirm } from "primevue";
import { ref } from "vue";

export function tableDespesaView() {
    const { totalElements, totalPages, page, onPage, onSort, loading, selectedItem, menuModel, cm, onRowContextMenu, toast } = tableUtil();

    const despesas = ref<DespesaResponse[]>([]);
    const valorTotal = ref<number>();
    const confirm = useConfirm();
    const editVisible = ref(false);

    menuModel.value = [
        { label: 'Editar', icon: Pencil, command: () => editVisible.value = true },
        { label: 'Excluir', icon: Trash, command: () => confirmDelete() },
    ];

    async function carregarDespesas() {
        
        loading.value = true;
        try {
            const response: Response<DespesaResponse> = await despesaService.findAll(page.value);
            despesas.value = response.content;
            totalElements.value = response.totalElements;
            totalPages.value = response.totalPages;
            valorTotal.value = await despesaService.valorTotal(page.value);
        } catch (e) {
            console.log(e)
            toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao consultar despesas', life: 3000 });
        } finally {
            loading.value = false;
        }
    }

    function filtrar(tipo: string[], fornecedor: Fornecedor, dataInicial: string, dataFinal: string, formaPgto: FormaPagamento) {
        tipo ? page.value = { ...page.value, tipos: tipo.toString() } : delete page.value.tipos;
        fornecedor ? page.value = { ...page.value, 'fornecedorUuid': fornecedor.uuid } : delete page.value['fornecedorUuid'];
        dataInicial ? page.value = { ...page.value, 'inicio': dataInicial } : delete page.value.inicio;
        dataFinal ? page.value = { ...page.value, 'termino': dataFinal } : delete page.value.termino;
        formaPgto ? page.value = { ...page.value, 'formaPagamentoUuid': formaPgto.uuid } : delete page.value['formaPagamentoUuid'];

        carregarDespesas()
    }

    const confirmDelete = () => {
        confirm.require({
            message: 'Deseja realmente excluir a despesa?',
            header: 'Confirmação',
            icon: ExclamationTriangle,
            rejectProps: {
                label: 'Cancelar',
                severity: 'secondary',                
            },
            acceptProps: {
                label: 'Excluir'
            },
            accept: () => {
                try {
                    if (selectedItem.value) {
                        despesaService.deleteDespesa(selectedItem.value.uuid)
                        toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Despesa excluida!', life: 3000 });
                        carregarDespesas();
                    }
                } catch (e) {
                    console.log(e)
                    toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir a despesa', life: 3000 });
                }
            }
        });
    };

    return {
        despesas,loading,selectedItem,totalElements,totalPages,toast,page,
        menuModel,valorTotal,cm,onRowContextMenu,confirm,carregarDespesas,
        onPage,onSort,filtrar,confirmDelete,editVisible
    };
}













