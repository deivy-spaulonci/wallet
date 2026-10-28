<script setup lang="ts">
import { ref } from "vue";
import { Form } from '@primevue/forms'
import { z } from 'zod'
import { zodResolver } from '@primevue/forms/resolvers/zod'
import { useToast } from "primevue/usetoast";
import { pessoaJuridicaService } from "../../services/pessoa.juridica.service";
import Globe from "@primeicons/vue/globe";

const formRef = ref(null)
const cnpj = ref('')
const cidade = ref('')
const uf = ref('')
const codigoIbge = ref('');
const toast = useToast();
const initialValues = ref({
    nome: '',
    razaoSocial: '',
    cnpj: '',
    codigoIbge: '',
});

const resolver = zodResolver(
    z.object({
        nome: z.string().trim().min(10, 'Informe o nome!'),
        razaoSocial: z.string().trim().min(10, 'Informe a Razão Social!'),
        cnpj: z.string().trim().min(10, 'Informe a CNPJ!'),
        codigoIbge: z.string().optional(),
    })
)

async function buscaCNPJ() {    
    const response = await pessoaJuridicaService.buscarCnpj(cnpj.value);
    formRef.value?.setValues({
        nome: response.estabelecimento?.nome_fantasia ? response.estabelecimento?.nome_fantasia : response.razao_social,
        razaoSocial: response.razao_social ?? '',
    })
    codigoIbge.value = response.estabelecimento?.cidade.ibge_id ?? '';
    cidade.value = response.estabelecimento?.cidade.nome;
    uf.value = response.estabelecimento?.estado.sigla;
}

async function salvar({ valid, values }) {

    if (!valid) return

    values.codigoIbge = codigoIbge.value;
    values.cnpj = values.cnpj.replace(/\D/g, '')
    try {
        pessoaJuridicaService.save(values);
        toast.add({ severity: 'success', summary: 'Sucesso!', detail: 'Pessoa salva.', life: 3000 });
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao salvar pessoa.', life: 3000 });
    }
}
</script>

<template>
    <Form ref="formRef" v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="salvar"
        class="flex flex-col gap-4">
        <div class="flex gap-3 w-100">
            <div class="flex flex-col gap-3 w-full">
                <Label class="w-25">CNPJ:</Label>
                <div class="flex flex-row w-full">
                    <InputText class="basis-5/6" v-mask="'99.999.999/9999-99'" name="cnpj" v-model="cnpj" />
                    <Button @click="buscaCNPJ" class="basis-1/6">
                        <Globe />
                    </Button>
                </div>
                <Message v-if="$form.cnpj?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.cnpj.error?.message }}
                </Message>

                <Label class="w-25">Nome:</Label>
                <InputText class="w-full" name="nome" />
                <Message v-if="$form.nome?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.nome.error?.message }}
                </Message>

                <Label class="w-25">Razão Social:</Label>
                <InputText class="w-full" name="razaoSocial" />
                <Message v-if="$form.razaoSocial?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.razaoSocial.error?.message }}
                </Message>

                <Label class="w-25">Cidade:</Label>
                <Message v-if="cidade" severity="info">
                    {{ cidade }} - {{ uf }}
                </Message>

                <Button type="submit" class="w-full mt-10"><Save />Salvar</Button>
            </div>
        </div>
    </Form>
</template>

<style scoped></style>