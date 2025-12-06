import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

const ResultTable = ({ results }) => {
    return (
        <DataTable
            value={results}
            id="resultTable"
            className="prime-table"
            scrollable
            scrollHeight="450px"
            stripedRows
        >
            <Column
                field="x"
                header="X"
                align="center"
                body={(row) => row.x}
            />
            <Column
                field="y"
                header="Y"
                align="center"
                body={(row) => row.y}
            />
            <Column
                field="r"
                header="R"
                align="center"
                body={(row) => row.r}
            />
            <Column
                header="Result"
                align="center"
                body={(row) => row.check ? "True" : "False"}
            />
        </DataTable>
    );
};

export default ResultTable;