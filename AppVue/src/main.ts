import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import Nora from '@primeuix/themes/nora';
import Aura from '@primeuix/themes/aura';
import Mask from 'primevue/mask';
import {
    AutoComplete,
    BlockUI,
    Button,
    Card,
    Column,
    ColumnGroup,
    ConfirmationService,
    ConfirmDialog,
    ContextMenu,
    DataTable, DatePicker, Dialog, Divider, Fieldset, IconField, InputGroup, InputGroupAddon, InputIcon, InputNumber,
    InputPassword,
    InputText, Label,
    Message, Panel, RadioButton,
    Row,
    Select, Tab, TabList, TabPanel, TabPanels, Tabs,
    Textarea,
    Toast, ToastService,
    Tooltip
} from "primevue";
import {createPinia} from "pinia";

const app = createApp(App)
app.use(createPinia());
app.use(router)
app.use(PrimeVue, {
        theme: {
            preset: Nora
        },
        options: {
            darkModeSelector: false,
        },
        license: 'eyJpZCI6IjlhYWU1NjJjLWU5ZWMtNDhhZi1iNGVhLWI4ODhiOWFkYTY5YiIsInByb2R1Y3QiOiJwcmltZXVpIiwidGllciI6ImNvbW11bml0eSIsInR5cGUiOiJkZXYiLCJpYXQiOjE3OTAxMzk1MzMsImV4cCI6MTgyMTY3NTUzM30.7AASHHBkXD7LPYcznEZmzWblFOqP_PTSDztxE5HlKIw0KfSQsWXHMeIh0bM8fSq7K7xcDamSArH5NJjp7o7AAg'
        })
app.use(ToastService);
app.use(ConfirmationService);
app.directive('mask', Mask);
app.component('Button', Button);
app.component('InputText', InputText);
app.component('DataTable',DataTable);
app.component('Column',Column);
app.component('ColumnGroup',ColumnGroup);
app.component('Row',Row);
app.component('InputPassword',InputPassword);
app.component('Message',Message);
app.component('Card',Card);
app.component('Select',Select);
app.component('Toast',Toast);
app.component('AutoComplete',AutoComplete);
app.component('DatePicker',DatePicker);
app.component('RadioButton',RadioButton);
app.component('Label',Label);
app.component('InputIcon',InputIcon);
app.component('IconField',IconField);
app.component('InputNumber',InputNumber);
app.component('Tabs',Tabs);
app.component('TabList',TabList);
app.component('Tab',Tab);
app.component('TabPanels',TabPanels);
app.component('TabPanel',TabPanel);
app.directive('tooltip', Tooltip);
app.component('Textarea', Textarea);
app.component('InputGroup', InputGroup);
app.component('InputGroupAddon', InputGroupAddon);
app.component('Divider', Divider);
app.component('ContextMenu', ContextMenu);
app.component('ConfirmDialog', ConfirmDialog);
app.component('Dialog', Dialog);
app.component('BlockUI', BlockUI);
app.component('Panel', Panel);
app.component('Fieldset', Fieldset);

app.mount('#app')
