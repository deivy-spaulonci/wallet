<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useToast } from "primevue/usetoast";
import { Form } from '@primevue/forms'
import { z } from 'zod'
import { zodResolver } from '@primevue/forms/resolvers/zod'
import { pessoaFisicaService } from "../../services/pessoa.fisica.service";
import type { Cidade } from "../../model/cidade";
import Cog from '@primeicons/vue/cog';
import Save from '@primeicons/vue/save';

const loading = ref(false);
const estadoSelecionado = ref('SP')
const cidadeSelecionada = ref({})
const cidades = ref<Cidade[]>([])
const toast = useToast();

const initialValues = ref({
    nome: '',
    cpf: '',
    codigoIbge: '',
});

const resolver = zodResolver(
    z.object({
        nome: z.string().trim().min(3, 'Informe o nome!'),
        cpf: z.string().trim().min(3, 'Informe a CPF!'),                
        codigoIbge:  z.union([z.string(), z.number()])
                        .refine((value) => String(value).trim().length > 0, {
                        message: 'Selecione uma cidade!',}),
    })
)

async function carregarCidades() {
    loading.value = true;
    try {
        cidades.value = await pessoaFisicaService.findCidadePorUf(estadoSelecionado.value + '');

    } catch (e) {
        toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao consultar cidades.', life: 3000 });
    } finally {
        loading.value = false;
    }
}


async function salvar({ valid, values }) {

    if (!valid) return

    try {
        console.log(JSON.stringify(values))
        //pessoaFisicaService.save(values);
        toast.add({ severity: 'success', summary: 'Sucesso!', detail: 'Pessoa salva.', life: 3000 });
    } catch (e) {
        toast.add({ severity: 'error', summary: 'Erro!', detail: 'Erro ao salvar pessoa.', life: 3000 });
    }
}


onMounted(() => {
    carregarCidades()
});

</script>

<template>
    <Form ref="formRef" v-slot="$form" :resolver="resolver" :initialValues="initialValues" @submit="salvar"
        class="flex flex-col gap-4">
        <div class="flex gap-3 w-100">
            <div class="flex flex-col gap-3 w-full">
                <Label class="w-25">Nome:</Label>
                <InputText class="w-full" name="nome" />
                <Message v-if="$form.nome?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.nome.error?.message }}
                </Message>

                <Label class="w-25">CPF:</Label>
                <InputText class="w-full" name="cpf" />
                <Message v-if="$form.cpf?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.cpf.error?.message }}
                </Message>                

                <Label class="w-25">UF:</Label>
                <Select v-model="estadoSelecionado" :options="[
                    'AC', 'AL', 'AM', 'AP', 'BA', 'CE', 'DF',
                    'ES', 'GO', 'MA', 'MG', 'MS', 'MT', 'PA',
                    'PB', 'PE', 'PI', 'PR', 'RJ', 'RN', 'RO',
                    'RR', 'RS', 'SC', 'SE', 'SP', 'TO'
                ]" scrollHeight="30rem" placeholder="Selecione um estado" class="w-full" @change="carregarCidades" />

                <Label class="w-25">Cidade:
                    <Cog spin :size="18" v-if="loading" />
                </Label>
                <Select v-model="cidadeSelecionada" optionLabel="nome" optionValue="id"
                    
                    name="codigoIbge" 
                    :filter="true"
                    :options="cidades" 
                    placeholder="selecione uma cidade" class="w-100" scrollHeight="40rem" />
                <Message v-if="$form.codigoIbge?.invalid" severity="error" size="small" variant="simple">
                    {{ $form.codigoIbge.error?.message }}
                </Message>  
                    
                <Button type="submit" class="w-full mt-10">
                    <Save />Salvar
                </Button>
            </div>
        </div>
    </Form>
</template>