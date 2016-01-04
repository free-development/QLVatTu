function parseDate(date) {
	var temp = date.split("-");
	var dateResult = temp[2] + '/' + temp[1] + '/' + temp[0];
	return dateResult;
}