import {Table} from "./table.js";

export function coloredWithRowHeads(element)
{
    let table = document.getElementById(element.id);
    return Table.createTable(table, element, ['table', 'table-sm'], ['table-dark'], true);
}