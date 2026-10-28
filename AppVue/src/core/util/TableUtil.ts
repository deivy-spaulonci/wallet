import { useToast } from "primevue";
import { ref } from "vue";

export function tableUtil() {
    const selectedItem = ref();
    const loading = ref(false);
    const totalElements = ref<number>();
    const totalPages = ref<number>();
    const cm = ref();
    const menuModel = ref();
    const toast = useToast();
    const page = ref<any>({
        page: 0,
        size: 15,
        sort: '',
    });
    const onPage = (event: any, carregarTabela: () => void) => {
        page.value = { ...page.value, page: event.page, size: event.rows };
        carregarTabela();
    };
    const onSort = (event: any, carregarTabela: () => void) => {
        let order = event.sortField + ',' + (event.sortOrder === -1 ? 'desc' : 'asc');
        page.value = { ...page.value, sort: order };
        carregarTabela();
    };
    const onRowContextMenu = (event: any) => {
        cm.value.show(event.originalEvent);
    };

    return { totalElements, totalPages, page, onPage, onSort, loading, selectedItem, cm, onRowContextMenu, menuModel, toast }
}
