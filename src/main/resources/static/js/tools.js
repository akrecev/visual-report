function currentDate()
{
	locale = "ru-RU";

	var currDate = new Date();
	    year = currDate.getFullYear();
	    month = currDate.toLocaleString(locale, {month: "long"});
	    day = currDate.getDate();

	var today = day + "  " + month + "  " + year;

	return today;
}


/**
 * Creates an array with time values from @param {number} startHour to @param {number} stopHour with @param {number} offset.
 * @param offset in minutes
 */

function workingHoursTimeline(startHour, stopHour, offset)
{
	var start = new Date(0,0);
	start.setMinutes(+startHour*60);

	var stop = new Date(0,0);
	stop.setMinutes((+stopHour * 60) - offset);

	let timeline = [start.toTimeString().slice(0, 5)];

	while (start.getTime() < stop.getTime())
	{
		start.setMinutes(start.getMinutes() + offset);
		timeline.push(start.toTimeString().slice(0, 5));
	}

	stop.setMinutes(stop.getMinutes() + offset)

	timeline.push(stop.toTimeString().slice(0, 5))

	return timeline;

}

/**
 * Creates an array with month and year values from @param {number} offsetBackwards to now.
 */
function monthTimeline(offsetBackwards)
{
	locale = "ru-RU";

	var currDate = new Date();

	var month = currDate.getMonth();

	var result = [];

	for (let i = offsetBackwards; i >=0; i--)
	{
		currDate.setMonth(month - i);
		result.push(currDate.toLocaleString(locale
			, {month: "short"
			, year:"2-digit"}));
	}

	return result;

}
