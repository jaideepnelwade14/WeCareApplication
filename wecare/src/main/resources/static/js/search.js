function filterTableJurisdiction() {
	let td = 0;
	let txtValue = "";
	const input = document.getElementById("searchInput");
	const filter = input.value.toUpperCase();
	const table = document.querySelector("table");
	const tr = table.getElementsByTagName("tr");

	for (let i = 1; i < tr.length; i++) {
		const tds = tr[i].getElementsByTagName("td");
		let matchFound = false;

		for (let j = 1; j < tds.length - 1; j++) {
			td = tds[j];
			if (td) {
				txtValue = td.textContent || td.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					matchFound = true;
					break;
				}
			}
		}
 
		if (matchFound) {
			tr[i].style.display = "";
		} else {
			tr[i].style.display = "none";
		}
	}
}

function filterTableOfficer() {
	let td = 0;
	let txtValue = "";
	const input = document.getElementById("searchInput");
	const filter = input.value.toUpperCase();
	const table = document.querySelector("table");
	const tr = table.getElementsByTagName("tr");

	for (let i = 1; i < tr.length; i++) {
		let matchFound = false;

		for (let j = 1; j < tr[i].cells.length; j++) {
			td = tr[i].cells[j];
			if (td) {
				txtValue = td.textContent || td.innerText;
				if (txtValue.toUpperCase().indexOf(filter) > -1) {
					matchFound = true;
					break;
				}
			}
		}

		if (matchFound) {
			tr[i].style.display = "";
		} else {
			tr[i].style.display = "none";
		}
	}
}