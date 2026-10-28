import { api } from './api';

export interface LoginRequest {
    email: string;
    password: string;
}


export const authService = {

    async login(data: LoginRequest): Promise<any> {
        console.log(JSON.stringify(data));
        const response = await api.post<any>(
            '/auth/login',
            data
        );

        return response.data;
    }
};