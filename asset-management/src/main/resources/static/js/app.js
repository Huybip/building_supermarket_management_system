/**
 * Asset Management System - JavaScript Application
 * API Integration and Frontend Logic
 */

const API_BASE_URL = '/asset-management/api';

// ============================
// Page Initialization
// ============================
document.addEventListener('DOMContentLoaded', function() {
    console.log('Asset Management System initialized');
    initializeNavigation();
    loadDashboardData();
    loadAssets();
});

// ============================
// Navigation Management
// ============================
function initializeNavigation() {
    const sidebarLinks = document.querySelectorAll('.sidebar-menu a');
    
    sidebarLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href').substring(1);
            showSection(targetId);
        });
    });
}

function showSection(sectionId) {
    const sections = document.querySelectorAll('.content-section');
    sections.forEach(section => section.classList.remove('active'));
    document.getElementById(sectionId).classList.add('active');
}

// ============================
// Dashboard
// ============================
function loadDashboardData() {
    // Fetch total assets
    fetch(`${API_BASE_URL}/assets/active`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('totalAssets').textContent = data.length;
        })
        .catch(error => console.error('Error loading total assets:', error));

    // Fetch damaged assets
    fetch(`${API_BASE_URL}/assets/status/DAMAGED`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('damagedAssets').textContent = data.length;
        })
        .catch(error => console.error('Error loading damaged assets:', error));

    // Fetch maintenance assets
    fetch(`${API_BASE_URL}/assets/status/MAINTENANCE`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('maintenanceAssets').textContent = data.length;
        })
        .catch(error => console.error('Error loading maintenance assets:', error));

    // Fetch total value (sample - would need specific API)
    document.getElementById('totalValue').textContent = '1,250,000,000 VNĐ';
}

// ============================
// Assets Management
// ============================
function loadAssets() {
    fetch(`${API_BASE_URL}/assets`)
        .then(response => response.json())
        .then(data => {
            displayAssets(data);
        })
        .catch(error => {
            console.error('Error loading assets:', error);
            document.getElementById('assetsTable').innerHTML = 
                '<tr><td colspan="7" class="text-center">Lỗi tải dữ liệu</td></tr>';
        });
}

function displayAssets(assets) {
    const tableBody = document.getElementById('assetsTable');
    
    if (assets.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="7" class="text-center">Không có dữ liệu</td></tr>';
        return;
    }

    let html = '';
    assets.forEach(asset => {
        const statusBadge = getStatusBadge(asset.status);
        const categoryName = getCategoryName(asset.category);
        const currentValue = formatCurrency(asset.currentValue);
        
        html += `
            <tr>
                <td><strong>${asset.assetCode}</strong></td>
                <td>${asset.assetName}</td>
                <td>${categoryName}</td>
                <td>${statusBadge}</td>
                <td>${currentValue}</td>
                <td>${asset.department.departmentName}</td>
                <td>
                    <button class="btn-action" onclick="editAsset(${asset.id})" title="Sửa">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn-action btn-danger" onclick="deleteAsset(${asset.id})" title="Xóa">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    });

    tableBody.innerHTML = html;
}

function getStatusBadge(status) {
    const statusMap = {
        'ACTIVE': '<span class="badge badge-success">Đang Sử Dụng</span>',
        'MAINTENANCE': '<span class="badge badge-warning">Bảo Trì</span>',
        'DAMAGED': '<span class="badge badge-danger">Hỏng</span>',
        'DISPOSED': '<span class="badge badge-secondary">Đã Thanh Lý</span>',
        'STORED': '<span class="badge badge-info">Lưu Kho</span>'
    };
    return statusMap[status] || status;
}

function getCategoryName(category) {
    const categoryMap = {
        'ELECTRONICS': 'Thiết Bị Điện Tử',
        'FURNITURE': 'Nội Thất',
        'MACHINERY': 'Máy Móc',
        'BUILDING': 'Xây Dựng',
        'VEHICLE': 'Phương Tiện',
        'OFFICE_SUPPLIES': 'Văn Phòng Phẩm',
        'OTHER': 'Khác'
    };
    return categoryMap[category] || category;
}

function formatCurrency(value) {
    if (!value) return '0 VNĐ';
    return new Intl.NumberFormat('vi-VN', { 
        style: 'currency', 
        currency: 'VND' 
    }).format(value);
}

// ============================
// Asset Form Management
// ============================
function openAssetForm() {
    document.getElementById('assetModal').classList.add('active');
    document.getElementById('assetForm').reset();
}

function closeAssetForm() {
    document.getElementById('assetModal').classList.remove('active');
}

function editAsset(assetId) {
    alert('Chức năng sửa sẽ được triển khai');
}

function deleteAsset(assetId) {
    if (confirm('Bạn có chắc chắn muốn xóa tài sản này?')) {
        fetch(`${API_BASE_URL}/assets/${assetId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Xóa thành công');
                loadAssets();
            } else {
                alert('Lỗi khi xóa');
            }
        })
        .catch(error => console.error('Error:', error));
    }
}

// ============================
// Asset Form Submission
// ============================
document.addEventListener('DOMContentLoaded', function() {
    const assetForm = document.getElementById('assetForm');
    if (assetForm) {
        assetForm.addEventListener('submit', function(e) {
            e.preventDefault();
            submitAssetForm();
        });
    }
});

function submitAssetForm() {
    const formData = {
        assetCode: document.getElementById('assetCode').value,
        assetName: document.getElementById('assetName').value,
        description: document.getElementById('assetDescription').value,
        category: document.getElementById('assetCategory').value,
        originalValue: parseFloat(document.getElementById('originalValue').value),
        purchaseDate: document.getElementById('purchaseDate').value,
        warrantyDate: document.getElementById('purchaseDate').value,
        status: 'ACTIVE',
        location: 'Chưa xác định',
        departmentId: 1  // Default department
    };

    fetch(`${API_BASE_URL}/assets`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            alert('Thêm tài sản thành công');
            closeAssetForm();
            loadAssets();
        } else {
            alert('Lỗi khi thêm tài sản');
        }
    })
    .catch(error => console.error('Error:', error));
}

// ============================
// Reports Export
// ============================
function exportReport() {
    alert('Chức năng xuất báo cáo sẽ được triển khai');
    // In a real application, this would generate and download a PDF/Excel file
}

// ============================
// Utility Functions
// ============================
function setupEventListeners() {
    // Modal close
    const modal = document.getElementById('assetModal');
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            closeAssetForm();
        }
    });

    // Search and filter
    document.getElementById('searchInput').addEventListener('input', function() {
        // Filter implementation
    });

    document.getElementById('categoryFilter').addEventListener('change', function() {
        // Filter implementation
    });

    document.getElementById('statusFilter').addEventListener('change', function() {
        // Filter implementation
    });
}

// Initialize event listeners on page load
document.addEventListener('DOMContentLoaded', setupEventListeners);
