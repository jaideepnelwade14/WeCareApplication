<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: rgb(130, 253, 240);
      color: rgba(130, 253, 240);
      margin: 0;
      padding: 0;
    }

    .container {
      max-width: 600px;
      margin: 20px auto;
      padding: 20px;
      background-color: #fff;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .header {
      text-align: left;
      margin-bottom: 20px;
    }

    .complaint-details table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
    }

    .complaint-details th, .complaint-details td {
      border: 1px solid #ddd;
      padding: 8px;
      text-align: left;
    }

    .complaint-details th {
      background-color: #f2f2f2;
    }

    .officer-details {
      margin-top: 20px;
    }

    h4 {
      margin-top: 10px;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="header">
      <div>
        Hello,
        <h4 class="inline-heading">${user.firstName} ${user.lastName}</h4>
      </div>
      <div >Your complaint has been registered successfully!</div>
    </div>

    <div>
      Thank you, We appreciate you for notifying us about the issue.
      We promise to address the problem and provide a quicker solution.
    </div>
    <br/>

    <div class="complaint-details">
      <div>Your Complaint Details:</div>
      <table>
        <tr>
          <th>Complaint Id</th>
          <th>Type Of Issue</th>
          <th>Comments</th>
        </tr>
        <tr>
          <td>${complaint.complId}</td>
          <td>${complaint.issue}</td>
          <td>${complaint.comments}</td>
        </tr>
      </table>
    </div>
    <br/>

    <div class="complaint-details">
    <div>Officer assigned to your complaint :</div>
    <table>
        <tr>
          <th>Officer Name</th>
          <th>Officer Email</th>
        </tr>
        <tr>
          <td>${officer.officerName}</td>
          <td>${officer.email}</td>
        </tr>
      </table>
      <br/>
      </div>

    <h4>Best regards,</h4>
    <h4>WeCare</h4>
  </div>
</body>
</html>
