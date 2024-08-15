//import { Chart } from "./chart.js";

/**
 * @class ChartCreator
 * @property {String} id - html canvas id
 * @property {String} type - chart type
 * @property {Object} apiData - labels and axes data from api
 * @property {Array} apiDataFields - array of keys of apiData object
 * @property {Array}  globalOptions
 * @param {Object} element
 * @param {Array} globalOptions
 */

export class ChartCreator
{
  #id
  #type
  #apiData;
  #apiDataFields;
  #interval;
  #globalOptions;

  static labelColumnName = 'LABEL';
  static xAxisColumnName = 'XAXIS';
  static yAxisColumnName = 'YAXIS';

  constructor(element, globalOptions)
  {
    const apiData = this.#apiData = element.data;
    const apiDataFields  = this.#apiDataFields = Object.keys(apiData);
    this.#id = element.id;
    this.#interval = element.interval;
    this.#type = apiDataFields[0];
    this.#globalOptions  = globalOptions;
  }

  #isDifferentChartTypes()
  {
    const fields = this.#apiDataFields;
    return fields.length > 1 && new Set(fields).size != fields.length;
  }

  /**
   * dozen lines of shitty code
   */
  #initDatasets()
  {
    let datasetsArr = new Array();
        for (const e of Object.entries(this.#apiData))
        {
          let data = e[1];
          for (const d of data)
          {
            for (const property in d.data)
            {
              if (property != ChartCreator.labelColumnName
                && property != ChartCreator.xAxisColumnName
                && property != ChartCreator.yAxisColumnName)
              {
                const data = d.data[property];
                const label = property.split('', 1) + property.substring(1, property.length).toLowerCase();
                (this.#isDifferentChartTypes)
                  ? datasetsArr.push({type: e[0].toLowerCase(), label: label, data: data})
                  : datasetsArr.push({label: label, data: data})
              }
            }
          }
        }
      return datasetsArr;
  }

  #initData()
  {
    const type = this.#type;

    const data = ()=>
    {
      let labels = this.#apiData[type][0].data[ChartCreator.labelColumnName];
      let datasets = this.#initDatasets();
      return {labels: labels, datasets: datasets}
    }

    return {type: type.toLowerCase(), data: data(), options : this.#setOptions(), plugins: [ChartDataLabels]};
  }

  #setOptions()
  {
    const yScaleLeft =
    {
      type: 'linear',
      position: 'left',
      title: {
      }
    };

    const xScale =
    {
      title: {
      }
    };

    const options =
    {   responsive: true,
        maintainAspectRatio: false,
        scales:
        {
        },
        plugins:
        {
            datalabels:
                {
                },
            legend:
                {
                }
        }
    }

    options.scales.y = yScaleLeft;
    options.scales.x = xScale;

    const xScaleTitle = Object.entries(this.#apiData)[0][1][0].data[ChartCreator.xAxisColumnName];
    const yScaleTitle = Object.entries(this.#apiData)[0][1][0].data[ChartCreator.yAxisColumnName];

    if(xScaleTitle != null )
    {
      options.scales.x.title  =
        {
          display: true,
          text: xScaleTitle[0]
        }
    }

    if(yScaleTitle != null )
    {
      options.scales.y.title  =
        {
          display: true,
          text: yScaleTitle[0]
        }
    }

    // TODO Lable settings
    options.plugins.legend =
        {
            position: 'top'
        }

    options.plugins.datalabels =
        {
            anchor: 'end',
            color: '#000000',
            rotation: 270,
            align: 'center',
            //display: function(context) {
            //    return context.dataIndex % 2; // display labels with an odd index
            //},
            display: false, //auto TODO legenda
            clamp: true,
            formatter: function(value, context) {
                return ('' + value).replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');;
            }
        }

    return options;
  }

  #initChart()
  {
    //const global =  [Chart.defaults.elements.bar.backgroundColor = 'rgba(43, 159, 159, 0.5)', Chart.defaults.elements.bar.borderColor = ('rgba(255, 99, 71)')];
    const data = this.#initData();
    const ctx = document.getElementById(this.#id);
    const chart  = new Chart(ctx, data);
    return chart;
  }

  static updateData(chart, data)
  {
    const newChart  = new ChartCreator(data);
    const newChartData = newChart.#initData();
    chart.data.labels = newChartData.data.labels;

    chart.data.datasets = newChartData.data.datasets;

    chart.update();

  }

  static  fetchData(url, chart)
  {
    const splitUrl = url.split("?");

    const newUrl = splitUrl[0]
        + "/"
        + chart.canvas.id
        + ((splitUrl.length > 1) ? '?' + splitUrl[1] : "");

    const request = new XMLHttpRequest();
    request.open("GET", newUrl, true);

    request.send();

    request.onload = async function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                var data = await JSON.parse(this.response);
                ChartCreator.updateData(chart, data);
			} else {
                console.error(request.statusText);
			}
        }
    }
  }

  static setIntervalForChart(url, chart, data)
  {
    const interval  = data.elements.find(e => e.id === chart.canvas.id).interval;
    if(interval > 0)
    {
      setInterval(ChartCreator.fetchData(url, chart), interval);
    }
  }

  static drawChart(element)
  {
    const chart = new ChartCreator(element).#initChart();
    return chart;
  }

}
