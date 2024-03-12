<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Content</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            color: #333;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        div {
            margin-bottom: 15px;
        }

        h4 {
            color: #007BFF;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th,
        td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        b {
            color: #555;
        }

        .remainder {
            color: red;
        }
        
        .name {
        	color: #007BFF;
        }
    </style>
</head>

<body>
    <div>
        <div>
            Hello <b class="name">${complaint.getUser().getFirstName()}</b>,
        </div>
        <br />
        <div>Updates Regarding your <b>Complaint ID:</b> ${complaint.complId}</div>
        <div>For the issue is <b>${complaint.issue}</b></div>
        <br />
        <div> Your status has been changed.</div>
        <br />
        <table>
            <tr>
                <th><b>Status:</b></th>
            </tr>
            <tr>
                <td>${complaint.status}</td>
            </tr>
        </table>
    </div>
    <br /><br />

    <h4>Best regards,</h4>
    <h4>WeCare</h4>
    </div>
</body>

</html>
