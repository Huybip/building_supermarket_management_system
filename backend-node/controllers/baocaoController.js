// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/baocaoController.js
const db = require('../models/queries');

async function doanhthu(req, res) {
  const { from, to } = req.query;
  const rows = await db.query('SELECT DATE(created_at) as ngay, SUM(tong_tien) as doanh_thu FROM HoaDon WHERE created_at BETWEEN ? AND ? GROUP BY DATE(created_at)', [from, to]);
  res.json(rows);
}

async function sanphamBanChay(req, res) {
  const rows = await db.query('SELECT s.id, s.ten, SUM(c.so_luong) as so_luong_ban FROM ChiTietHoaDon c JOIN SanPham s ON c.sanpham_id = s.id GROUP BY s.id ORDER BY so_luong_ban DESC LIMIT 20');
  res.json(rows);
}

async function tonKho(req, res) {
  const rows = await db.query('SELECT id, ma, ten, ton_kho, ngay_het_han FROM SanPham');
  res.json(rows);
}

async function nhanVienPerf(req, res) {
  const rows = await db.query('SELECT n.id, n.ho_ten, COUNT(h.id) as so_hoa_don, SUM(h.tong_tien) as doanh_thu FROM NhanVien n LEFT JOIN HoaDon h ON n.id = h.nhan_vien_id GROUP BY n.id');
  res.json(rows);
}

module.exports = { doanhthu, sanphamBanChay, tonKho, nhanVienPerf };