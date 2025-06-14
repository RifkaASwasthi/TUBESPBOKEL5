
let patients = [
    {
        id: 1,
        rmNumber: "RM20250001",
        name: "Budi Santoso",
        age: 35,
        address: "Jl. Merdeka No. 12, Jakarta",
        phone: "081234567890",
        type: "BPJS",
        bpjsNumber: "1234567890123",
        bpjsClass: "1",
        complaint: "Demam tinggi dan batuk",
        registrationDate: "2025-01-15"
    },
    {
        id: 2,
        rmNumber: "RM20250002",
        name: "Ani Wijaya",
        age: 28,
        address: "Jl. Sudirman No. 45, Bandung",
        phone: "082345678901",
        type: "Umum",
        complaint: "Sakit kepala dan mual",
        registrationDate: "2025-01-16"
    },
    {
        id: 3,
        rmNumber: "RM20250003",
        name: "Citra Dewi",
        age: 42,
        address: "Jl. Gatot Subroto No. 8, Surabaya",
        phone: "083456789012",
        type: "BPJS",
        bpjsNumber: "2345678901234",
        bpjsClass: "2",
        complaint: "Nyeri dada",
        registrationDate: "2025-01-17"
    },
    {
        id: 4,
        rmNumber: "RM20250004",
        name: "Dodi Pratama",
        age: 50,
        address: "Jl. Diponegoro No. 33, Medan",
        phone: "084567890123",
        type: "Umum",
        complaint: "Asam lambung",
        registrationDate: "2025-01-18"
    },
    {
        id: 5,
        rmNumber: "RM20250005",
        name: "Eka Putri",
        age: 25,
        address: "Jl. Thamrin No. 67, Yogyakarta",
        phone: "085678901234",
        type: "BPJS",
        bpjsNumber: "3456789012345",
        bpjsClass: "3",
        complaint: "Flu dan pilek",
        registrationDate: "2025-01-19"
    }
];

// Current page for pagination
let currentPage = 1;
const patientsPerPage = 5;

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    showPatientList();
    loadPatients();
});

// Navigation functions
function showPatientList() {
    document.getElementById('patientListSection').style.display = 'block';
    document.getElementById('patientForm').classList.remove('active');
    document.getElementById('statsSection').style.display = 'none';
    updateNavigation('list');
}

function showAddForm() {
    document.getElementById('patientListSection').style.display = 'block';
    document.getElementById('patientForm').classList.add('active');
    document.getElementById('statsSection').style.display = 'none';
    
    // Reset form
    document.getElementById('patientDataForm').reset();
    document.getElementById('patientId').value = '';
    document.getElementById('formTitle').textContent = 'Tambah Pasien Baru';
    updateNavigation('add');
}

function showEditForm(patientId) {
    const patient = patients.find(p => p.id === patientId);
    if (!patient) return;

    document.getElementById('patientListSection').style.display = 'block';
    document.getElementById('patientForm').classList.add('active');
    document.getElementById('statsSection').style.display = 'none';
    
    // Fill form with patient data
    document.getElementById('patientId').value = patient.id;
    document.getElementById('patientName').value = patient.name;
    document.getElementById('patientAge').value = patient.age;
    document.getElementById('patientAddress').value = patient.address;
    document.getElementById('patientPhone').value = patient.phone;
    document.getElementById('patientType').value = patient.type;
    document.getElementById('patientComplaint').value = patient.complaint;
    
    if (patient.type === 'BPJS') {
        document.getElementById('bpjsNumber').value = patient.bpjsNumber;
        document.getElementById('bpjsClass').value = patient.bpjsClass;
        document.getElementById('bpjsNumberGroup').style.display = 'block';
        document.getElementById('bpjsClassGroup').style.display = 'block';
    } else {
        document.getElementById('bpjsNumberGroup').style.display = 'none';
        document.getElementById('bpjsClassGroup').style.display = 'none';
    }
    
    document.getElementById('formTitle').textContent = 'Edit Data Pasien';
    updateNavigation('edit');
}

function showStats() {
    document.getElementById('patientListSection').style.display = 'none';
    document.getElementById('patientForm').classList.remove('active');
    document.getElementById('statsSection').style.display = 'block';
    updateNavigation('stats');
    updateStatistics();
}

function updateNavigation(active) {
    const navLinks = document.querySelectorAll('.nav-menu a');
    navLinks.forEach(link => link.classList.remove('active'));
    
    if (active === 'list') {
        navLinks[0].classList.add('active');
    } else if (active === 'add' || active === 'edit') {
        navLinks[1].classList.add('active');
    } else if (active === 'stats') {
        navLinks[2].classList.add('active');
    }
}

// Patient CRUD operations
function loadPatients(page = 1, searchTerm = '') {
    currentPage = page;
    const tableBody = document.getElementById('patientTableBody');
    tableBody.innerHTML = '';
    
    // Filter patients if search term exists
    let filteredPatients = patients;
    if (searchTerm) {
        const term = searchTerm.toLowerCase();
        filteredPatients = patients.filter(patient => 
            patient.name.toLowerCase().includes(term) || 
            patient.rmNumber.toLowerCase().includes(term) ||
            (patient.type === 'BPJS' && patient.bpjsNumber.includes(term)) ||
            patient.phone.includes(term)
        );
    }
    
    // Calculate pagination
    const totalPages = Math.ceil(filteredPatients.length / patientsPerPage);
    const startIndex = (page - 1) * patientsPerPage;
    const paginatedPatients = filteredPatients.slice(startIndex, startIndex + patientsPerPage);
    
    // Populate table
    paginatedPatients.forEach(patient => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${patient.rmNumber}</td>
            <td>${patient.name}</td>
            <td>${patient.age} tahun</td>
            <td>${patient.type}</td>
            <td>${patient.type === 'BPJS' ? patient.bpjsNumber : '-'}</td>
            <td>${patient.phone}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-edit" onclick="showEditForm(${patient.id})">Edit</button>
                    <button class="btn btn-delete" onclick="deletePatient(${patient.id})">Hapus</button>
                </div>
            </td>
        `;
        tableBody.appendChild(row);
    });
    
    // Update pagination
    updatePagination(totalPages, searchTerm);
}

function updatePagination(totalPages, searchTerm = '') {
    const paginationDiv = document.getElementById('pagination');
    paginationDiv.innerHTML = '';
    
    if (totalPages <= 1) return;
    
    for (let i = 1; i <= totalPages; i++) {
        const pageBtn = document.createElement('button');
        pageBtn.textContent = i;
        if (i === currentPage) {
            pageBtn.classList.add('active');
        }
        pageBtn.onclick = () => loadPatients(i, searchTerm);
        paginationDiv.appendChild(pageBtn);
    }
}

function searchPatients() {
    const searchTerm = document.getElementById('searchInput').value;
    loadPatients(1, searchTerm);
}

function toggleBPJSFields() {
    const patientType = document.getElementById('patientType').value;
    if (patientType === 'BPJS') {
        document.getElementById('bpjsNumberGroup').style.display = 'block';
        document.getElementById('bpjsClassGroup').style.display = 'block';
        document.getElementById('bpjsNumber').required = true;
    } else {
        document.getElementById('bpjsNumberGroup').style.display = 'none';
        document.getElementById('bpjsClassGroup').style.display = 'none';
        document.getElementById('bpjsNumber').required = false;
    }
}

// Form submission
document.getElementById('patientDataForm').addEventListener('submit', function(e) {
    e.preventDefault();
    savePatient();
});

function savePatient() {
    const patientId = document.getElementById('patientId').value;
    const patientType = document.getElementById('patientType').value;
    
    const patientData = {
        name: document.getElementById('patientName').value,
        age: document.getElementById('patientAge').value,
        address: document.getElementById('patientAddress').value,
        phone: document.getElementById('patientPhone').value,
        type: patientType,
        complaint: document.getElementById('patientComplaint').value,
        registrationDate: new Date().toISOString().split('T')[0] // Current date
    };
    
    if (patientType === 'BPJS') {
        patientData.bpjsNumber = document.getElementById('bpjsNumber').value;
        patientData.bpjsClass = document.getElementById('bpjsClass').value;
    }
    
    if (patientId) {
        // Update existing patient
        const index = patients.findIndex(p => p.id === patientId);
        if (index !== -1) {
            patients[index] = { ...patients[index], ...patientData };
        }
    } else {
        // Add new patient
        const newId = patients.length > 0 ? Math.max(...patients.map(p => p.id)) + 1 : 1;
        const rmNumber = `RM${new Date().getFullYear()}${String(newId).padStart(4, '0')}`;
        
        patients.push({
            id: newId,
            rmNumber: rmNumber,
            ...patientData
        });
    }
    
    // Reload patient list
    loadPatients(currentPage);
    showPatientList();
    
    // Show success message
    alert(`Data pasien ${patientId ? 'diperbarui' : 'ditambahkan'} dengan sukses!`);
}

function deletePatient(patientId) {
    if (!confirm('Apakah Anda yakin ingin menghapus data pasien ini?')) {
        return;
    }
    
    patients = patients.filter(patient => patient.id !== patientId);
    loadPatients(currentPage);
    
    // Show success message
    alert('Data pasien berhasil dihapus!');
}

// Statistics functions
function updateStatistics() {
    // Update counts
    document.getElementById('totalPatients').textContent = patients.length;
    document.getElementById('generalPatients').textContent = patients.filter(p => p.type === 'Umum').length;
    document.getElementById('bpjsPatients').textContent = patients.filter(p => p.type === 'BPJS').length;
    
    // Update age distribution chart
    updateAgeChart();
}

function updateAgeChart() {
    const ctx = document.getElementById('ageChart').getContext('2d');
    
    // Group patients by age ranges
    const ageRanges = [
        { label: '0-10', min: 0, max: 10 },
        { label: '11-20', min: 11, max: 20 },
        { label: '21-30', min: 21, max: 30 },
        { label: '31-40', min: 31, max: 40 },
        { label: '41-50', min: 41, max: 50 },
        { label: '51-60', min: 51, max: 60 },
        { label: '61+', min: 61, max: 120 }
    ];
    
    const data = ageRanges.map(range => {
        return patients.filter(p => p.age >= range.min && p.age <= range.max).length;
    });
    
    const labels = ageRanges.map(range => range.label);
    
    // Destroy previous chart if exists
    if (window.ageChart) {
        window.ageChart.destroy();
    }
    
    // Create new chart
    window.ageChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Jumlah Pasien',
                data: data,
                backgroundColor: 'rgba(102, 126, 234, 0.7)',
                borderColor: 'rgba(102, 126, 234, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        precision: 0
                    }
                }
            }
        }
    });
}