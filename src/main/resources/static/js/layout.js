import {ChartCreator} from "./Elements/charts.js";
import { Table } from "./Elements/table.js";
import {coloredWithRowHeads } from "./Elements/tables.js";
import {targetEndpoint, contentBox} from "./app.js"

const mainContentContainer = "mainContainer";
export const intervals = new Set();

function addDiv(parentNode, id, _class)
{
    let div = document.createElement("div");
    div.id = id;
    if (Array.isArray(_class))
    {
        _class.forEach(c =>
            {
                div.classList.add(c);
            });
    } else div.classList.add(_class);

    parentNode.appendChild(div);

    return div;
}


function addRows(parentNode, data)
{
    let elementNum = 0;
    for (let i = 0; i < data.maxRows; i++)
	{
		let row = addDiv(parentNode, "row"+(i+1), "row");
        let columnsCnt = data.elements.filter((e) => e.row == i + 1).length
        if(columnsCnt > 0)
            {
                for(let j = 0; j < columnsCnt; ++j)
                {
                    let col = addColumn(row, (elementNum), columnsCnt);
                    col.insertAdjacentHTML("afterbegin", fill(data.elements[elementNum]));
                    fillContent(data.elements[elementNum++]);

                }

            }
	}

}

function addColumn(parentNode, id, columnCnt)
{
    let bootstrapColClass = () =>
    {
        let width =  Math.floor(12/columnCnt);
        return "col-md-" + width;
    }

    return addDiv(parentNode, "col"+id, bootstrapColClass())
}

function fill(element)
{
    switch (element.type)
	{
        case 'TABLE':
            return (`<div class="card card-mini"><div class="card-body"><h5 class="card-title"><strong>${element.name}<div class="card-title-date"></div></strong></h5><table id =\"${element.id}"\></table></div>`)
        default:  return ('<div class="card-header"><h5>'
            +element.name+'<div class="card-header-date"></div></h5></div><div class="chart-container"><canvas id='
            + element.id +'></canvas></div>');
    }

}

function fillContent(element)
{
	switch (element.type)
	{
        case 'CHART':
          return ChartCreator.drawChart(element);
		default:
            return coloredWithRowHeads(element);

	  }
}

function getAllCharts()
{
    const chartSelectors = document.querySelectorAll("canvas");

    const charts = new Array();

    chartSelectors.forEach(x=> charts.push(Chart.getChart(x.id)));

    return charts;
}

function setIntervalToChart(chart, elements)
{
    const currentElement = elements.find(y=>y.id === chart.canvas.id);
    chart['interval'] = currentElement.interval;
    return chart;
}

export function layout(data)
{

    let container = addDiv(contentBox, mainContentContainer, ["container"]);

    addRows(container, data);

    updateCharts(data, targetEndpoint);

    updateTables(data);
}

function updateTables(data)
{
    const tabels = () =>
    {
        const t = new Array();
        for(const e of data.elements){
            if(e.type == "TABLE")
            {
                t.push(e);
            }
        }
        return t;
    }

    for (const el of tabels())
    {
        if(el.interval > 0)
        {
            intervals.add(setInterval(Table.fetchData, el.interval, targetEndpoint, el));
        }
    }
}

function updateCharts(data)
{
    getAllCharts().forEach(x =>  {
        setIntervalToChart(x, data.elements);
        if(x.interval > 0)
        {
           intervals.add(setInterval(ChartCreator.fetchData, x.interval, targetEndpoint, x));
        }
    });
    return intervals;
}

export default layout;