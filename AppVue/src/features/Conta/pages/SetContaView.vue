<script lang="ts" setup>
import { onMounted, ref, watch } from "vue";
import type { Conta } from "../model/Conta";
import SelectFormaPagamento from "@/components/shared/SelectFormaPagamento.vue";
import { DateUtil } from "@/core/util/DateUtil";
import InputDate from "@/components/shared/InputDate.vue";
import InputCurrency from "@/components/shared/InputCurrency.vue";
import Times from "@primeicons/vue/times";
import Check from "@primeicons/vue/check";
import CheckSquare from "@primeicons/vue/check-square";
import { zodResolver } from "@primevue/forms/resolvers/zod";
import z from "zod";
import { Form } from "@primevue/forms";
import { contaService } from "../services/conta.service";
import { useToast } from "primevue";
import { FormatUtil } from "@/core/util/FormatUtil";
import type { FormaPagamento } from "@/features/FormaPagamento/model/FormaPagamento";
import { formatPagamentoService } from "@/features/FormaPagamento/service/forma.pagamento.service";

const visible = defineModel<boolean>("visible");
const conta = defineModel<Conta>("conta");
const emit = defineEmits<{ "update:visible": [value: boolean], "atualizar":[] }>();
const toast = useToast();
const carregando = ref(false);
const initialValues = ref({
    formaPagamento: {},
    dataPagamento: "",
    valorPagamento: "",
});
const resolver = zodResolver(
    z.object({
        formaPagamento: z.object({ uuid: z.string() }).nullable().optional(),
        valorPagamento: z.string().trim().min(1, "Informe um valor!"),
        dataPagamento: z
            .string()
            .trim()
            .min(10, "Informe a data de pagamento!"),
    }),
);

async function salvar({ valid, values }) {
    if (!valid) return;

    values = { ...values, uuid: conta.value?.uuid };
    values = { ...values, formaPagamentoUuid: values.formaPagamento.uuid };
    delete values.formaPagamento;
    values = {
        ...values,
        dataPagamento: DateUtil.toApiDate(values.dataPagamento),
    };
    values = {
        ...values,
        valorPagamento: values.valorPagamento
            .replace(/\./g, "")
            .replace(",", "."),
    };
    try {
        contaService.setContaPaga(values);
        toast.add({
            severity: "success",
            summary: "Sucesso!",
            detail: "Conta salva.",
            life: 3000,
        });
    } catch (e) {
        toast.add({
            severity: "error",
            summary: "Erro!",
            detail: "Erro ao salvar Conta.",
            life: 3000,
        });
    } finally {
      visible.value = false;
      emit("atualizar");
    }
}

emit("update:visible", false);

watch(visible, async (aberto) => {
    if (aberto) {
        carregando.value = true;
        try {
            let param = { uuid: conta.value?.uuid };
            const response: Conta = await contaService.findOne(param);
            conta.value = response;
            initialValues.value.dataPagamento = DateUtil.toFormDate(
                conta.value.vencimento + "",
            );
            initialValues.value.valorPagamento =
                FormatUtil.floatToNumberBRNoSymbol(conta.value.valor);
            const formaDefault: FormaPagamento =
                await formatPagamentoService.findOne({
                    uuid: "2a37b6ff-5b92-424c-a265-844421c0e73e",
                });
            initialValues.value.formaPagamento = formaDefault;
        } catch (e) {
            console.error(e);
            toast.add({
                severity: "error",
                summary: "Erro!",
                detail: "Erro ao consultar Conta.",
                life: 3000,
            });
        } finally {
            carregando.value = false;
        }
    }
});
</script>

<template>
    <Dialog
        v-if="conta"
        v-model:visible="visible"
        modal
        :style="{ width: '28rem' }"
    >
        <template #header>
            <div class="inline-flex items-center justify-center gap-2">
                <CheckSquare size="30" />
                <span class="font-bold whitespace-nowrap"
                    >Ajustar conta como paga</span
                >
            </div>
        </template>
        <Form
            ref="formRef"
            v-slot="$form"
            :resolver="resolver"
            :initialValues="initialValues"
            v-if="!carregando"
            @submit="salvar"
        >
            <div class="flex flex-col gap-3">
                <div
                    class="flex items-center gap-4 text-green-400"
                    v-if="conta.tipoConta"
                >
                    {{ conta.tipoConta.nome }}
                </div>
                <div
                    class="flex items-center gap-4 text-green-400"
                    v-if="conta"
                >
                    Vencimento: {{ DateUtil.toFormDate(conta.vencimento) }}
                </div>

                <div class="flex flex-col gap-2">
                    <Label>Forma Pagamento:</Label>
                    <SelectFormaPagamento
                        name="formaPagamento"
                        class="w-full"
                    />
                </div>
                <div class="flex flex-row gap-3">
                    <Label class="basis-1/2">Data Pagamento:</Label>
                    <Label class="basis-1/2">Valor Pagamento:</Label>
                </div>
                <div class="flex flex-row gap-3">
                    <InputDate name="dataPagamento" class="basis-1/2" />
                    <InputCurrency name="valorPagamento" class="basis-1/2" />
                </div>
                <div class="flex flex-row gap-3">
                    <Message
                        v-if="$form?.dataPagamento?.invalid"
                        severity="error"
                        size="small"
                        variant="simple"
                        class="w-full"
                    >
                        {{ $form.dataPagamento.error?.message }}
                    </Message>
                    <Message
                        v-if="$form?.valorPagamento?.invalid"
                        severity="error"
                        size="small"
                        variant="simple"
                        class="w-full"
                    >
                        {{ $form.valorPagamento.error?.message }}
                    </Message>
                </div>

                <div class="flex justify-end gap-4">
                    <Button
                        severity="secondary"
                        @click="visible = false"
                        size="large"
                    >
                        <Times /> Cancelar
                    </Button>
                    <Button type="submit" size="large">
                        <Check /> Efetura Pagamento
                    </Button>
                </div>
            </div>
        </Form>
    </Dialog>
</template>

<style></style>
