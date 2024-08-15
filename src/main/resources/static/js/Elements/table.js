/**
 * @class Table
 * @property {HTMLElement} ctx - element id
 * @property {Map<String, Array<String>>} content - map from result set where keys are column names and values are column values array
 * @property {Array<String>} [classList] - html table class list
 * @property {Array<String>} [headClassList] - html table heads class list
 * @property {boolean} [isRowsHeadEnabled] - if the table has rows head
 */

export class Table
{
    #ctx;
    #content;
    #classList;
    #headClassList;
    #isRowsHeadEnabled;

    constructor(ctx, data, classList, headClassList, isRowsHeadEnabled)
    {
        this.#ctx = ctx;
        this.#content = new Map(Object.entries(data.data));
        this.#classList = classList;
        this.#headClassList = headClassList;
        if (isRowsHeadEnabled == null) {this.#isRowsHeadEnabled  = false}
        this.#isRowsHeadEnabled = isRowsHeadEnabled;
        this.#createTable ();
    }

    #createTable()
    {

        let table = this.#createHead();
        table += this.#createRow();

        let tableCurrent = this.#ctx;

        tableCurrent.classList.add(...this.#classList);

        tableCurrent.innerHTML = table;

       //Добавление даты к элементам по DateIncTableElementId + добавение даты к заголову страницы
       let tableCurrentId = tableCurrent.id;
       createDateHead(tableCurrentId);
       createDateHeadСhart(tableCurrentId);
       createDateHeadTable(tableCurrentId);
    }

    #createHead()
    {
        let cells ='<thead ';

        if (this.#headClassList != null)
        {
            cells += `class=\"${this.#headClassList.toString().replace(',', ' ')}\">`;
        } else cells += '>';

        cells += '<tr class=\"table-secondary\">';

        for(let key of this.#content.keys())
        {
            cells +=  `<th scope=\"col\">${key.charAt(0)}${key.slice(1).toLowerCase()}</th> `;
        }

        cells += '</thead></tr>';

        return cells;
    }

       #createRow()
    {
        let tmp = new Map(this.#content);
        let body = '<tbody>'
        let rowsCnt =  tmp.values().next().value.length;
        let rowHeadsData = new Array();

        if(this.#isRowsHeadEnabled)
        {
            let rowHeads = tmp.entries().next();
            for (const i of rowHeads.value[1])
            {
                rowHeadsData.push(i);
            }
            tmp.delete(rowHeads.value[0]);
        }

        for(let i = 1; i <= rowsCnt; ++i)
        {

            if (rowHeadsData.length > 0)
            {
                if (rowHeadsData[i - 1].toLowerCase().includes("всего"))
                	body += `<tr class=\"table-secondary\"><th scope = \"row\">${rowHeadsData[i - 1]}</th>`
                else if (rowHeadsData[i - 1].toLowerCase().includes("итого"))
                	body += `<tr class=\"table-secondary\"><th scope = \"row\">${rowHeadsData[i - 1]}</th>`
                else
                	body += `<tr><th scope = \"row\">${rowHeadsData[i - 1]}</th>`;
            }

            for(let val of tmp.values())
            {
                body += `<td>${val[i - 1]}</td>`;
            }
            body += '</tr>'
        }
        body += '</tbody>';
        return body;
    }

    static createTable (ctx, element, classList, headClassList, isRowsHeadEnabled)
    {
        let t = new this(ctx, element.data, classList, headClassList, isRowsHeadEnabled);
        return t

    }

    static recreate(element)
    {
        const ctx = document.getElementById(element.id);
        const classList = ctx.classList;
        const children = ctx.childNodes;

        const isRowsHeadEnabled =  children.item(1).firstChild.querySelectorAll("th").length > 0;

        const headClassList = children.item(0).classList;

        while (ctx.firstChild)
        {
            ctx.removeChild(ctx.firstChild);
        }

       Table.createTable(ctx, element, classList, headClassList, isRowsHeadEnabled);

    }

    static  fetchData(url, element)
    {
        const splitUrl = url.split("?");

        const newUrl = splitUrl[0]
            + "/"
            + element.id
            + ((splitUrl.length > 1) ? '?' + splitUrl[1] : "");

        const request = new XMLHttpRequest();
        request.open("GET", newUrl, true);

        request.send();

        request.onload = async function ()
        {
            if (request.readyState === 4)
                    {
                        if (request.status === 200)
                        {
                            var data = await JSON.parse(this.response);

                            Table.recreate(data);

                        }else
                        {
                            console.error(request.statusText);
                        }
            }
        }
    }


}

/**
 * Creates an array with month and year values from @param {number} tableCurrentId to now.
 */

function createDateHead(tableCurrentId)
{
	  //console.log('Зашли в функцию createDateHead');
      //console.log(tableCurrentId);

   //Добавление даты в заголовок
        if (tableCurrentId === 'DateInc') {
            document.getElementById('app-current-date').innerHTML= document.getElementById('DateInc').innerHTML;
            let t = document.getElementById("DateInc").parentNode.parentNode;
            if (typeof(t) != 'undefined' && t != null){
	           t.style.display = "none";
            }
        }

}

//Добавление даты к заголовкам элементов Графики
/**
 * Creates an array with month and year values from @param {number} tableCurrentId to now.
 */
function createDateHeadСhart(tableCurrentId)
{
	  //console.log('Зашли в функцию createDateHeadСhart');
      //console.log(tableCurrentId);


      if (tableCurrentId === 'DateIncTableElementId') {
            //console.log(document.getElementById('DateIncTableElementId'));
            //Собираем данные из таблицы в массив
            let arr = [];
            let table = document.getElementById(tableCurrentId);
            for (let r = 0, n = table.rows.length; r < n; r++) {
                arr[r] = [];
                for (let c = 0, m = table.rows[r].cells.length; c < m; c++) {
        		    arr[r][c] = table.rows[r].cells[c].innerHTML;
        		    //console.log(table.rows[r].cells[c].innerHTML);
                }
            }
         //console.log(arr);
         //console.log(arr.length);


         //Перебираем элементы массива
         //Если в DOM есть нужный нам эллемент, то в заголовок добавляем дату
           for (let i = 1; i < arr.length; i++)
           {
	         //console.log(arr[i][0]);
             //console.log(arr[i][1]);
	         //console.log(document.getElementById(arr[i][0]));
             let element =  document.getElementById(arr[i][0]);
                if (typeof(element) != 'undefined' && element != null){
	                 let elemenParent = document.getElementById(arr[i][0]).parentNode;
                        if (typeof(elemenParent) != 'undefined' && elemenParent != null){
	                       let elemenParentPreviousSiblin = document.getElementById(arr[i][0]).parentNode.previousSibling;
                              if (typeof(elemenParentPreviousSiblin) != 'undefined' && elemenParentPreviousSiblin != null){
	                              let Text =' '+ arr[i][1];
                                  //Text = document.getElementById(arr[i][0]).parentNode.previousSibling.innerText + ' ' + arr[i][1];
                                  document.getElementById(arr[i][0]).parentNode.previousSibling.childNodes[0].childNodes[1].replaceWith(Text);
                              }
                        }
                 }
             }

   //Прячем саму таблицу DateIncTableElementId
         let x = document.getElementById(tableCurrentId).parentNode.parentNode;
         if (typeof(x) != 'undefined' && x != null){
	        x.style.display = "none";
         }
   }
}

//Добавление даты к заголовкам элементов Таблицы
/**
 * Creates an array with month and year values from @param {number} tableCurrentId to now.
 */
function createDateHeadTable(tableCurrentId)
{
	  //console.log('Зашли в функцию createDateHeadTable');
      //console.log(tableCurrentId);


      if (tableCurrentId === 'DateIncTableElementIdT') {
            //console.log(document.getElementById('DateIncTableElementIdT'));
            //Собираем данные из таблицы в массив
            let arr = [];
            let table = document.getElementById(tableCurrentId);
            for (let r = 0, n = table.rows.length; r < n; r++) {
                arr[r] = [];
                for (let c = 0, m = table.rows[r].cells.length; c < m; c++) {
        		    arr[r][c] = table.rows[r].cells[c].innerHTML;
        		    //console.log(table.rows[r].cells[c].innerHTML);
                }
            }
         //console.log(arr);
         //console.log(arr.length);


         //Перебираем элементы массива
         //Если в DOM есть нужный нам эллемент, то в заголовок добавляем дату
           for (let i = 1; i < arr.length; i++)
           {
	         //console.log(arr[i][0]);
             //console.log(arr[i][1]);
	         //console.log(document.getElementById(arr[i][0]));
             let element =  document.getElementById(arr[i][0]);
                if (typeof(element) != 'undefined' && element != null){
                   let elemenParentPreviousSiblin = document.getElementById(arr[i][0]).previousSibling;
                   if (typeof(elemenParentPreviousSiblin) != 'undefined' && elemenParentPreviousSiblin != null){
	                  let Text = ' '+arr[i][1];
                      //Text = document.getElementById(arr[i][0]).previousSibling.innerText + ' ' + arr[i][1];
                      if (arr[i][1] != 'null')
                      	{
							  document.getElementById(arr[i][0]).previousSibling.childNodes[0].childNodes[1].replaceWith(Text);
						}
                    }
                 }
             }

   //Прячем саму таблицу DateIncTableElementIdT
         let t = document.getElementById(tableCurrentId).parentNode.parentNode;
         if (typeof(t) != 'undefined' && t != null){
	        t.style.display = "none";
         }
   }
}