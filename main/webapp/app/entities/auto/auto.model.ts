import { BaseEntity } from './../../shared';

export class Auto implements BaseEntity {
    constructor(
        public id?: number,
        public marca?: string,
        public modello?: string,
        public cilindrata?: string,
        public alimentazione?: string,
        public colore?: string,
        public annoImmatricolazione?: number,
        public statoPrenotazione?: string,
    ) {
    }
}
