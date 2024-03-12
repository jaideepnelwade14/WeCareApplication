/* Custom Logout Dialog Event */
function confirmLogout(event) {
	event.preventDefault();

	const dialogContainer = document.createElement('div');
	dialogContainer.classList.add('custom-dialog-container');

	const dialog = document.createElement('div');
	dialog.classList.add('custom-dialog');

	const message = document.createElement('p');
	message.textContent = 'Are you sure you want to logout?';

	const confirmBtn = document.createElement('button1');
	confirmBtn.textContent = 'Yes';
	confirmBtn.addEventListener('click', function() {
		window.location.href = "/logout";
		dialogContainer.remove();
	});

	const cancelBtn = document.createElement('button2');
	cancelBtn.textContent = 'No';
	cancelBtn.addEventListener('click', function() {
		dialogContainer.remove();
	});

	dialog.appendChild(message);
	dialog.appendChild(confirmBtn);
	dialog.appendChild(cancelBtn);

	dialogContainer.appendChild(dialog);

	document.body.appendChild(dialogContainer);
}

/* Custom Edit Jurisdiction Dialog Event */
function confirmEditJury(event) {
	event.preventDefault();

	const juryId = event.currentTarget.getAttribute('data-jury-id');

	const dialogContainer = document.createElement('div');
	dialogContainer.classList.add('custom-dialog-container');

	const dialog = document.createElement('div');
	dialog.classList.add('custom-dialog');

	const message = document.createElement('p');
	message.textContent = 'Are you sure you want to edit this jurisdiction?';

	const confirmBtn = document.createElement('button1');
	confirmBtn.textContent = 'Yes';
	confirmBtn.addEventListener('click', function() {
		window.location.href = "/wecare/admin/jurisdictions/edit-jury?id=" + juryId;
		dialogContainer.remove();
	});

	const cancelBtn = document.createElement('button2');
	cancelBtn.textContent = 'No';
	cancelBtn.addEventListener('click', function() {
		dialogContainer.remove();
	});

	dialog.appendChild(message);
	dialog.appendChild(confirmBtn);
	dialog.appendChild(cancelBtn);

	dialogContainer.appendChild(dialog);

	document.body.appendChild(dialogContainer);
}

/* Custom Delete Jurisdiction Dialog Event */
function confirmDeleteJury(event) {
	event.preventDefault();

	const juryId = event.currentTarget.getAttribute('data-jury-id');

	const dialogContainer = document.createElement('div');
	dialogContainer.classList.add('custom-dialog-container');

	const dialog = document.createElement('div');
	dialog.classList.add('custom-dialog');

	const message = document.createElement('p');
	message.textContent = 'Are you sure you want to delete this jurisdiction?';

	const confirmBtn = document.createElement('button1');
	confirmBtn.textContent = 'Yes';
	confirmBtn.addEventListener('click', function() {
		window.location.href = "/wecare/admin/jurisdictions/delete-jury?id=" + juryId;
		dialogContainer.remove();
	});

	const cancelBtn = document.createElement('button2');
	cancelBtn.textContent = 'No';
	cancelBtn.addEventListener('click', function() {
		dialogContainer.remove();
	});

	dialog.appendChild(message);
	dialog.appendChild(confirmBtn);
	dialog.appendChild(cancelBtn);

	dialogContainer.appendChild(dialog);

	document.body.appendChild(dialogContainer);
}

/* Custom Edit Officer Dialog Event */
function confirmEditOfficer(event) {
	event.preventDefault();

	const officerId = event.currentTarget.getAttribute('data-officer-id');

	const dialogContainer = document.createElement('div');
	dialogContainer.classList.add('custom-dialog-container');

	const dialog = document.createElement('div');
	dialog.classList.add('custom-dialog');

	const message = document.createElement('p');
	message.textContent = 'Are you sure you want to edit this Officer?';

	const confirmBtn = document.createElement('button1');
	confirmBtn.textContent = 'Yes';
	confirmBtn.addEventListener('click', function() {
		window.location.href = "/wecare/admin/officers/edit-officer?id=" + officerId;
		dialogContainer.remove();
	});

	const cancelBtn = document.createElement('button2');
	cancelBtn.textContent = 'No';
	cancelBtn.addEventListener('click', function() {
		dialogContainer.remove();
	});

	dialog.appendChild(message);
	dialog.appendChild(confirmBtn);
	dialog.appendChild(cancelBtn);

	dialogContainer.appendChild(dialog);

	document.body.appendChild(dialogContainer);
}

/* Custom Delete Officer Dialog Event */
function confirmDeleteOfficer(event) {
	event.preventDefault();

	const officerId = event.currentTarget.getAttribute('data-officer-id');

	const dialogContainer = document.createElement('div');
	dialogContainer.classList.add('custom-dialog-container');

	const dialog = document.createElement('div');
	dialog.classList.add('custom-dialog');

	const message = document.createElement('p');
	message.textContent = 'Are you sure you want to delete this Officer?';

	const confirmBtn = document.createElement('button1');
	confirmBtn.textContent = 'Yes';
	confirmBtn.addEventListener('click', function() {
		window.location.href = "/wecare/admin/officers/delete-officer?id=" + officerId;
		dialogContainer.remove();
	});

	const cancelBtn = document.createElement('button2');
	cancelBtn.textContent = 'No';
	cancelBtn.addEventListener('click', function() {
		dialogContainer.remove();
	});

	dialog.appendChild(message);
	dialog.appendChild(confirmBtn);
	dialog.appendChild(cancelBtn);

	dialogContainer.appendChild(dialog);

	document.body.appendChild(dialogContainer);
}

/* Success Message Popup Event */
document.addEventListener('DOMContentLoaded', function() {
	const popup = document.getElementById('popup');

	if (popup.innerText.trim() !== '') {
		popup.style.display = 'block';

		setTimeout(function() {
			popup.style.display = 'none';
		}, 8000);
	}
});

/* Failure Message Popup Event */
document.addEventListener('DOMContentLoaded', function() {
	const popup1 = document.getElementById('popup1');

	if (popup1.innerText.trim() !== '') {
		popup1.style.display = 'block';

		setTimeout(function() {
			popup1.style.display = 'none';
		}, 8000);
	}
});

function updateWards() {
	const selectedArea = document.getElementById("area").value;

	$.ajax({
		type: "GET",
		url: "/wecare/user/wards?area=" + encodeURIComponent(selectedArea),
		success: function(wards) {
			const wardDropdown = document.getElementById("ward");
			wardDropdown.innerHTML = ""; // Clear existing options

			wards.forEach(function(ward) {
				const option = document.createElement("option");
				option.value = ward;
				option.text = ward;
				wardDropdown.appendChild(option);
			});

			updateLayouts();
		},
		error: function() {
			console.error('Failed to fetch wards.');
		}
	});
}

function updateLayouts() {
	const selectedWard = document.getElementById("ward").value;

	$.ajax({
		type: "GET",
		url: "/wecare/user/layouts?ward=" + encodeURIComponent(selectedWard),
		success: function(layouts) {
			const layoutDropdown = document.getElementById("layout");
			layoutDropdown.innerHTML = "";

			layouts.forEach(function(layout) {
				const option = document.createElement("option");
				option.value = layout;
				option.text = layout;
				layoutDropdown.appendChild(option);
			});
		},
		error: function() {
			console.error('Failed to fetch layouts.');
		}
	});
}

function openModal() {
	document.getElementById('myModal').style.display = 'block';
}

function closeModal() {
	document.getElementById('myModal').style.display = 'none';
}

window.onclick = function(event) {
	const modal = document.getElementById('myModal');
	if (event.target == modal) {
		modal.style.display = 'none';
	}
};

/* Validating New password and Confirm password for ChangePassword pages */
function validatePassword() {
	let password = document.getElementById("newPassword").value;
	let confirmPassword = document.getElementById("confirmPassword").value;

	if (password !== confirmPassword) {
		document.getElementById("confirmPassword").value = "";
		document.getElementById("passwordmessage").innerHTML = "New Password and Confirm Password do not Match !";

		return false;
	} else {
		document.getElementById("passwordmessage").innerHTML = "";

		return true;
	}
}

window.onload = function() {
	let confirmPasswordInput = document.getElementById("confirmPassword");
	confirmPasswordInput.addEventListener("blur", function() {
		validatePassword();
	});
};

/* Disable Save button in addJurisdiction Page */
function disableSaveButton() {
	const saveButton = document.getElementById('saveButton');
	saveButton.disabled = true;
}

/* Filter Complaints by Status */ 
function filterTableByStatus(selectedStatus) {
    $('#complaintTable tbody tr').show();

    if (selectedStatus !== 'ALL') {
        $('#complaintTable tbody tr[data-status]').each(function () {
            let rowStatus = $(this).data('status');
            if (rowStatus !== selectedStatus) {
                $(this).hide();
            }
        });
    }
}