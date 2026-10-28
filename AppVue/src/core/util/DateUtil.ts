
export class DateUtil {
  private constructor() {}

  /**
   * Converte uma data no formato dd/MM/yyyy para yyyy-MM-dd.
   */
  static toApiDate(data: string): string {
    if (!data) {
      return '';
    }

    const [dia, mes, ano] = data.split('/');

    return `${ano}-${mes}-${dia}`;
  }

  /**
   * Converte uma data no formato yyyy-MM-dd para dd/MM/yyyy.
   */
  static toFormDate(data: string): string {
    if (!data) {
      return '';
    }

    const [ano, mes, dia] = data.split('-');

    return `${dia}/${mes}/${ano}`;
  }

  /**
   * Converte uma data no formato Date para yyyy-MM-dd.
   */
  static fromDateToData(data: Date):string {
    if (!data) {
      return '';
    }

    const formatada = [
      data.getFullYear(),
      String(data.getMonth() + 1).padStart(2, '0'),
      String(data.getDate()).padStart(2, '0'),
    ].join('-');

    return formatada;
  }
}
