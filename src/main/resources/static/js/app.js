import {layout, intervals} from "./layout.js";
import {baseURL} from "./apiEndpoint.js";

const baseEndpoint = baseURL;
const startPage = "index";
export const contentBox = document.getElementById("content");
export let targetEndpoint = baseEndpoint + startPage;
export let data;

loadPageProperties();

changeEndpointOnCLick();

function changeEndpointOnCLick()
{
    document.addEventListener('click', function(event)
        {
            if(event.target.matches('.filter'))
            {
                event.preventDefault();
                if (event.target.id != startPage)
                {
                    const splitID = event.target.id.split('@');
                    targetEndpoint = baseEndpoint + splitID[0] + "?filter="+  splitID[1];
                } else targetEndpoint = baseEndpoint + startPage;
                    contentBox.firstElementChild.remove();

                for(const i of intervals)
                {
                    clearInterval(i);
                }
                loadPageProperties();
            }

        }

    )
    return targetEndpoint;
}

 function loadPageProperties()
{
    var request = new XMLHttpRequest();

        request.onload = async function getData()
            {
                if (request.readyState === 4)
                {
                    if (request.status === 200)
                    {
                        var data = await JSON.parse(this.response);

                        layout(data);
                    } else
                    {
                        console.error(request.statusText);
                    }
                }

            }

        request.open('GET', targetEndpoint, true);

        request.send();
}

