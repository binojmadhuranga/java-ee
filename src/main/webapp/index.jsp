<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Doctor Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4">Doctor Management</h2>

    <!-- View All Button -->
    <div class="mb-3">
        <button class="btn btn-success" onclick="loadDoctors()">View All Doctors</button>
    </div>

    <!-- Doctor Form -->
    <form id="doctorForm" class="row g-3">
        <input type="hidden" id="doctorId">

        <div class="col-md-6">
            <label for="name" class="form-label">Name</label>
            <input type="text" class="form-control" id="name" required>
        </div>

        <div class="col-md-3">
            <label for="age" class="form-label">Age</label>
            <input type="number" class="form-control" id="age" required>
        </div>

        <div class="col-md-6">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" required>
        </div>

        <div class="col-md-6">
            <label for="specility" class="form-label">Speciality</label>
            <input type="text" class="form-control" id="specility" required>
        </div>

        <div class="col-12">
            <button type="submit" class="btn btn-primary" id="addBtn">Add Doctor</button>
            <button type="submit" class="btn btn-warning d-none" id="updateBtn">Update Doctor</button>
            <button type="button" class="btn btn-secondary" onclick="clearForm()">Clear</button>
        </div>
    </form>

    <!-- Doctor Table -->
    <hr class="my-4">
    <h4>All Doctors</h4>
    <table class="table table-bordered table-striped" id="doctorTable">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Email</th>
            <th>Speciality</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody id="doctorTableBody">
        </tbody>
    </table>
</div>

<script>
    const baseUrl = "doctor";  // no slash needed if servlet is directly under context

    const doctorForm = document.getElementById("doctorForm");
    const addBtn = document.getElementById("addBtn");
    const updateBtn = document.getElementById("updateBtn");

    document.addEventListener("DOMContentLoaded", loadDoctors);

    doctorForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const id = document.getElementById("doctorId").value;
        const doctor = {
            id: parseInt(id || 0),
            name: document.getElementById("name").value,
            age: parseInt(document.getElementById("age").value),
            email: document.getElementById("email").value,
            speciality: document.getElementById("speciality").value
        };

        const method = id ? "PUT" : "POST";

        await fetch(baseUrl, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(doctor)
        });

        clearForm();
        loadDoctors();
    });



    async function loadDoctors() {
      try {
    const response = await fetch(baseUrl);
    if (!response.ok) throw new Error("Failed to load");

    const doctors = await response.json();
    const tbody = document.getElementById("doctorTableBody");
    tbody.innerHTML = "";

    doctors.forEach(doctor => {
        const row = document.createElement("tr");

        row.innerHTML =
            "<td>" + doctor.id + "</td>" +
            "<td>" + doctor.name + "</td>" +
            "<td>" + doctor.age + "</td>" +
            "<td>" + doctor.email + "</td>" +
            "<td>" + doctor.specility + "</td>" +
            "<td>" +
                "<button class='btn btn-sm btn-warning me-1' onclick='editDoctor(" + JSON.stringify(doctor) + ")'>Update</button>" +
                "<button class='btn btn-sm btn-danger' onclick='deleteDoctor(" + doctor.id + ")'>Delete</button>" +
            "</td>";

        tbody.appendChild(row);
    });
} catch (err) {
    console.error(err);
    alert("Error loading doctors");
}

    }

    function editDoctor(doctor) {
        document.getElementById("doctorId").value = doctor.id;
        document.getElementById("name").value = doctor.name;
        document.getElementById("age").value = doctor.age;
        document.getElementById("email").value = doctor.email;
        document.getElementById("specility").value = doctor.specility;

        addBtn.classList.add("d-none");
        updateBtn.classList.remove("d-none");
    }

    async function deleteDoctor(id) {
        if (confirm("Delete this doctor?")) {
            await fetch(`${baseUrl}?id=${id}`, { method: "DELETE" });
            loadDoctors();
        }
    }

    function clearForm() {
        doctorForm.reset();
        document.getElementById("doctorId").value = "";
        addBtn.classList.remove("d-none");
        updateBtn.classList.add("d-none");
    }
</script>
</body>
</html>
