// filepath: /workspaces/building_supermarket_management_system/frontend/js/script.js
// simple frontend to interact with APIs
const API = 'http://localhost:3000';

document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('loginForm');
  if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      const username = document.getElementById('username').value;
      const password = document.getElementById('password').value;
      const role = document.getElementById('role').value;
      try {
        if (role === 'customer') {
          const res = await fetch(`${API}/api/khachhang/login`, { method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify({ email: username, mat_khau: password }) });
          const data = await res.json();
          if (data.token) { localStorage.setItem('token', data.token); alert('Logged in'); }
        } else {
          const res = await fetch(`${API}/api/nhanvien/login`, { method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify({ username, mat_khau: password }) });
          const data = await res.json();
          if (data.token) { localStorage.setItem('token', data.token); alert('Logged in'); }
        }
      } catch (err) { alert('Error'); }
    });
  }

  // POS page
  const items = [];
  const itemsUl = document.getElementById('items');
  const addItemBtn = document.getElementById('addItem');
  if (addItemBtn) {
    addItemBtn.addEventListener('click', () => {
      const pid = document.getElementById('pid').value;
      const qty = parseInt(document.getElementById('qty').value, 10) || 1;
      items.push({ sanpham_id: parseInt(pid,10), so_luong: qty, don_gia: 10000 });
      renderItems();
    });
  }
  function renderItems() {
    if (!itemsUl) return;
    itemsUl.innerHTML = '';
    items.forEach((it, i) => {
      const li = document.createElement('li');
      li.textContent = `SP:${it.sanpham_id} x ${it.so_luong} - ${it.don_gia}`;
      itemsUl.appendChild(li);
    });
  }
  const createInvoiceBtn = document.getElementById('createInvoice');
  if (createInvoiceBtn) {
    createInvoiceBtn.addEventListener('click', async () => {
      const token = localStorage.getItem('token');
      const res = await fetch(`${API}/api/hoadon`, {
        method: 'POST',
        headers: { 'Content-Type':'application/json', 'Authorization': 'Bearer ' + token },
        body: JSON.stringify({ ma: 'HD'+Date.now(), items, phuong_thuc_thanh_toan: 'tienmat' })
      });
      const data = await res.json();
      alert('Invoice created: ' + JSON.stringify(data));
    });
  }

  // Reports
  const exportDoanhthu = document.getElementById('exportDoanhthu');
  if (exportDoanhthu) {
    exportDoanhthu.addEventListener('click', () => {
      window.open('http://localhost:5000/report/doanhthu?from=2023-01-01&to=2025-12-31');
    });
  }
  const exportTonkho = document.getElementById('exportTonkho');
  if (exportTonkho) {
    exportTonkho.addEventListener('click', () => {
      window.open('http://localhost:5000/report/ton-kho');
    });
  }
});