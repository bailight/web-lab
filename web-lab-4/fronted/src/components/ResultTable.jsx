import React from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

const ResultTable = ({ results }) => {

    const [page, setPage] = React.useState(0);
    const [rows] = React.useState(8);

    const onPageChange = (event) => {
        setPage(event.page);
    };

    return (
        <DataTable
            value={results}
            id="resultTable"
            className="prime-table"
            scrollable
            scrollHeight="450px"

            paginator
            rows={rows}
            first={page * rows}
            totalRecords={results ? results.length : 0}
            onPageChange={onPageChange}
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
                body={(row) => (
                    <span style={{
                        color: row.check ? '#5fec5f' : '#ef3d3d',
                        fontWeight: 600,
                        fontSize: '14px'
                    }}>
                      {row.check ? 'True' : 'False'}
                    </span>
                )}
            />
        </DataTable>
    );
};

export default ResultTable;