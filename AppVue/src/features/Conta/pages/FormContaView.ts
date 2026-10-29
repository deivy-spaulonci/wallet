import { ref } from "vue";
import type { TipoConta } from "../model/TipoConta";
import { contaService } from "../services/conta.service";
import { tipoContaService } from "../services/tipo.conta.service";
import { useToast } from "primevue/usetoast";
import { zodResolver } from "@primevue/forms/resolvers/zod";
import z from "zod";
import { DateUtil } from "@/core/util/DateUtil";
import type { Conta } from "../model/Conta";
import { FormatUtil } from "@/core/util/FormatUtil";

export function formContaView(idEdicao: string | undefined, emit: any) {

  const tipos = ref<TipoConta[]>([]);
  const toast = useToast();
  const carregando = ref(true);
  const errorValid = ref("");
  const formRef = ref<any>(null)
  const initialValues = ref<any>({
    codigoBrras: "",
    tipoConta: {},
    emissao: "",
    vencimento: "",
    parcelas: "00/000",
    valor: "",
    formaPagamento: null,
    dataPagamento: "",
    valorPago: "",
    observacao: "",
  });
  const resolver = zodResolver(
    z.object({
      codigoBarras: z
        .string()
        .trim()
        .min(48, "Informe um codigo de barras valido!"),
      tipoConta: z
        .object({ uuid: z.string().min(1) })
        .nullable()
        .refine((TipoConta) => TipoConta !== null, {
          message: "Informe o tipo de conta!",
        }),
      emissao: z.string().trim().min(10, "Informe a data de emissão!"),
      vencimento: z.string().trim().min(10, "Informe a data de vencimento!"),
      valor: z.string().trim().min(1, "Informe um valor!"),
      parcelas: z.string().trim().min(6, "Informe as parcelas corretamenteo!"),
      formaPagamento: z.object({ uuid: z.string() }).nullable().optional(),
      observacao: z.string().optional(),
      valorPago: z.string().optional(),
      dataPagamento: z.string().optional(),
    }),
  );

  async function carregarTipos() {
    try {
      tipos.value = await tipoContaService.findAll();
      if (tipos.value[0]) initialValues.value.tipoConta = tipos.value[0];
    } catch (e) {
      toast.add({
        severity: "error",
        summary: "Erro!",
        detail: "Erro ao consultar tipos de conta.",
        life: 3000,
      });
    } finally {
      carregando.value = false;
    }
  }

  async function salvar({ valid, values }) {
    if (!valid) return

    errorValid.value = "";
    values = { ...values, emissao: DateUtil.toApiDate(values.emissao) };
    values = { ...values, vencimento: DateUtil.toApiDate(values.vencimento) };

    if (values.emissao > values.vencimento) {
      errorValid.value = "Erro emissão maior que o vencimento!";
      return;
    }

    let parcela = values.parcelas.split("/")[0];
    let totalParcela = values.parcelas.split("/")[1];

    values = { ...values, parcela: Number(parcela) };
    values = { ...values, totalParcela: Number(totalParcela) };
    delete values["parcelas"];

    if (Number(parcela) > Number(totalParcela)) {
      errorValid.value = "Erro parcela esta maior que numero total!";
      return;
    }

    values.valor = values.valor.replace(/\./g, "").replace(",", ".");
    values = { ...values, valor: values.valor }

    if (values.formaPagamento || values.dataPagamento || values.valorPago) {
      if (!values.formaPagamento) {
        errorValid.value = "Selecione uma foma de pagamento!";
        return;
      }
      if (!values.dataPagamento) {
        errorValid.value = "Selecione uma data de pagamento!";
        return;
      }
      if (!values.valorPago) {
        errorValid.value = "Selecione um valor de pago!";
        return;
      }
      values.valorPago = values.valorPago.replace(/\./g, "").replace(",", ".");
      if (values.valorPago < 0) {
        errorValid.value = "Valor pago deve ser maior que 0!";
        return;
      }

      values = {...values, valorPago: values.valorPago}
      values = {...values, dataPagamento: DateUtil.toApiDate(values.dataPagamento) };
    }

    try {
        if(!idEdicao){
          console.log('salvando nova despesa...')
          contaService.save(values);

        }else{
            values = {...values, uuid: idEdicao}
            console.log('salvando edicao despesa...')
            contaService.update(values);

        }
        formRef.value?.reset();

        toast.add({ severity: 'success', summary: 'Sucesso!', detail: 'Despesa salva.', life: 3000 });
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao salvar despesa.', life: 3000 });
    } finally {
      initialValues.value.codigoBrras = '';
       formRef.value?.reset();
        //emit("closeDialog");
    }
  }

  async function checkEdicao() {
      if (!idEdicao) return
      carregando.value = true
      try{
        const conta: Conta = await contaService.findOne({ uuid: idEdicao });
        initialValues.value = conta;
        initialValues.value.emissao = DateUtil.toFormDate(conta.emissao+'');
        initialValues.value.vencimento = DateUtil.toFormDate(conta.vencimento+'');
        (conta.totalParcela && conta.totalParcela) ? initialValues.value.parcelas = conta.parcela + '/0' + conta.totalParcela : '00/000';
        initialValues.value.valor = FormatUtil.floatToNumberBRNoSymbol(conta.valor);
        initialValues.value.formaPagamento = conta.formaPagamento;
        initialValues.value.dataPagamento = DateUtil.toFormDate(conta.dataPagamento+'');
        initialValues.value.valorPago = FormatUtil.floatToNumberBRNoSymbol(conta.valorPago);
        initialValues.value.observacao = conta.observacao;
      }catch (e){
          console.log(e);
          toast.add({ severity: 'error', summary: 'Erro!', detail: 'Houve um erro ao carregar a Conta', life: 3000 });
      } finally {
          carregando.value = false
      }
  }

  return {
    tipos,
    initialValues,
    carregando,
    errorValid,
    resolver,
    salvar,
    formRef,
    carregarTipos,
    checkEdicao
  };
}
