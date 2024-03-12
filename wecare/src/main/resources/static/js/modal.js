$(document).ready(function() {
	$("td button.view-button").click(function() {
		let responseText = $(this).attr("data-response");
		showResponseModal(responseText);
	});

	function showResponseModal(responseText) {
		if (responseText) {
			$("#responsePlaceholder").text(responseText);
		} else {
			$("#responsePlaceholder").text("No response yet.");
		}
		$("#exampleModalCenter").modal("show");
	}
});