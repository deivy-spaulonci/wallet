export interface Response<T> {
    content: T[];
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
}
