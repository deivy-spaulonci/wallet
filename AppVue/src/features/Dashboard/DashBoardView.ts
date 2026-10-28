import { chartView } from "@/components/shared/ChartView";

import { despesaService } from "../Despesa/services/despesa.service";
import { contaService } from "../Conta/services/conta.service";
import { useToast } from "primevue";
import { ref } from "vue";
import { tableUtil } from "@/core/util/TableUtil";
import type { Response } from "@/core/type/Response";
import type { ContaResponse } from "../Conta/model/ContaResponse";

export function dashBoardView() {

  const toast = useToast();
  const totalDespesa = ref<number>(0);
  const totalContas = ref<number>(0);
  const totalContasEmAberto = ref<number>(0);
  const totalGastos = ref<number>(0);
  const contas = ref<ContaResponse[]>([]);

  const PIECH = chartView("pieChart");
  const BARCH = chartView("barChart");

  const {
    chartData: pieChartData,
    chartOptions: pieChartOptions,
    legendas: pieLegendas,
    valores: pieValores,
    cores: pieCores,
  } = chartView("Contas");

  const {
    chartData: barChartData,
    chartOptions: barChartOptions,
    legendas: barLegendas,
    valores: barValores,
    cores: barCores,
  } = chartView("Gastos de Desepesas nesse ano");

  async function graficoDespesaAno() {
    try {
      const response = await despesaService.getTotalPorMesDespesa({
        ano: "2026",
      });
      let dados = Array.isArray(response) ? response : [];
      barLegendas.value = dados.map((item) => item.mes);
      barValores.value = dados.map((item) => item.total);
      barCores.value = ["#14532D", "#16A34A", "#4ADE80", "#A3E635"];
    } catch {
      toast.add({
        severity: "error",
        summary: "Erro!",
        detail: "Erro ao consultar gráfico por status.",
        life: 3000,
      });
    }
  }

  async function graficoEstatus() {
    try {
      const response = await contaService.getTotalPorsStatus();
      let dados = Array.isArray(response) ? response : [];
      pieLegendas.value = dados.map((item) => item.status);
      pieValores.value = dados.map((item) => item.total);
      pieCores.value = ["#00C950", "#2B7FFF", "#FB2C36", "#F0B100"];
    } catch {
      toast.add({
        severity: "error",
        summary: "Erro!",
        detail: "Erro ao consultar gráfico por status.",
        life: 3000,
      });
    }
  }

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
  } = tableUtil();

  async function carregaContas() {
    try {
      page.value = {...page.value, 'contaStatus': 'ATRASADO,HOJE,ABERTO'}
      const response: Response<ContaResponse> = await contaService.findAll(page.value);
      contas.value = response.content;
    } catch (e) {

    }
  }

  async function carregarValores() {
    totalDespesa.value = await despesaService.valorTotal({});
    totalContas.value = await contaService.valorTotal({});
    totalGastos.value = totalContas.value + totalDespesa.value;
    totalContasEmAberto.value = await contaService.valorTotal({
      contaStatus: "ABERTO,ATRASADO,HOJE",
    });
  }

  return {
    totalDespesa, totalContas, totalContasEmAberto, totalGastos, pieChartData, pieChartOptions,
    barChartData, barChartOptions, contas,
    graficoDespesaAno,graficoEstatus,carregarValores, carregaContas
  }

}
