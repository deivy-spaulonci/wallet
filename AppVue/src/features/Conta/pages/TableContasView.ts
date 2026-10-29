import { ref } from "vue";
import { tableUtil } from "@/core/util/TableUtil";
import type { ContaResponse } from "@/features/Conta/model/ContaResponse.ts";
import type { Response } from "@/core/type/Response.ts";
import { contaService } from "@/features/Conta/services/conta.service.ts";
import Pencil from "@primeicons/vue/pencil";
import Trash from "@primeicons/vue/trash";
import ExclamationTriangle from '@primeicons/vue/exclamation-triangle';
import type { TipoConta } from "@/features/Conta/model/TipoConta";
import type { Conta } from "@/features/Conta/model/Conta";
import { useConfirm } from "primevue";

export function tableContasView() {
  const {
    totalElements,
    totalPages,
    page,
    onPage,
    onSort,
    loading,
    selectedItem,
    menuModel,
    cm,
    onRowContextMenu,
    toast
  } = tableUtil();

  const contas = ref<ContaResponse[]>([]);
  const contaDetail = ref();
  const valorTotal = ref<number>();
  const editVisible = ref(false);
  const confirm = useConfirm();
  const setPayVisible = ref(false);

  menuModel.value = [
    {
      label: "Editar",
      icon: Pencil,
      command: () => (editVisible.value = true),
    },
    { label: "Excluir", icon: Trash, command: () => confirmDelete() },
  ];

  async function carregarContas() {
    loading.value = true;
    try {
      const response: Response<ContaResponse> = await contaService.findAll(
        page.value,
      );
      contas.value = response.content;
      totalElements.value = response.totalElements;
      totalPages.value = response.totalPages;
      valorTotal.value = await contaService.valorTotal(page.value);
    } catch (e) {
      console.log(e);
    } finally {
      loading.value = false;
    }
  }

  const rowStyle = (data: any) => {
    if (data.ativo) {
      if (data.intStatus === -1) {
        return {
          fontWeight: "bold",
          fontStyle: "italic",
          color: "var(--color-red-700)",
        };
      }
    } else {
      return { textDecoration: "line-through" };
    }
  };

  async function findConta() {
    try {
      let param = { uuid: selectedItem.value.uuid };
      const response: Conta = await contaService.findOne(param);
      contaDetail.value = response;
    } finally {
    }
  }

  const confirmDelete = () => {
    confirm.require({
        message: 'Deseja realmente excluir a conta?',
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
                    contaService.deleteConta(selectedItem.value.uuid)
                    toast.add({ severity: 'success', summary: 'Sucesso', detail: 'Despesa excluida!', life: 3000 });
                    carregarContas();
                }
            } catch (e) {
                console.log(e)
                toast.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir a despesa', life: 3000 });
            }
        }
    });
  };

  function filtrar(
    tipoConta: TipoConta,
    vencimentoInicial: string,
    vencimentoFinal: string,
    contaStatus: string,
  ) {
    tipoConta
      ? (page.value = { ...page.value, tipoUuid: tipoConta.uuid })
      : delete page.value["tipoUuid"];
    vencimentoInicial
      ? (page.value = { ...page.value, vencimentoInicial: vencimentoInicial })
      : delete page.value.vencimentoInicial;
    vencimentoFinal
      ? (page.value = { ...page.value, vencimentoFinal: vencimentoFinal })
      : delete page.value.vencimentoFinal;
    contaStatus
      ? (page.value = { ...page.value, contaStatus: contaStatus })
      : delete page.value.contaStatus;

    carregarContas();
  }

  return {
    totalElements,
    page,
    loading,
    selectedItem,
    cm,
    menuModel,
    contas,
    contaDetail,
    valorTotal,
    carregarContas,
    findConta,
    rowStyle,
    filtrar,
    onPage,
    onSort,
    onRowContextMenu,
    setPayVisible,
    editVisible
  };
}
