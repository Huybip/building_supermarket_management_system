// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/hoadonController.js
const db = require('../models/queries');

async function createInvoice(req, res) {
  const { ma, khach_hang_id, nhan_vien_id, items, phuong_thuc_thanh_toan } = req.body;
  // items: [{sanpham_id, so_luong, don_gia}]
  let tong = 0;
  for (const it of items) tong += it.so_luong * it.don_gia;
  const result = await db.query('INSERT INTO HoaDon (ma, khach_hang_id, nhan_vien_id, tong_tien, phuong_thuc_thanh_toan) VALUES (?, ?, ?, ?, ?)', [ma, khach_hang_id || null, nhan_vien_id || null, tong, phuong_thuc_thanh_toan || 'tienmat']);
  const hoadon_id = result.insertId;
  for (const it of items) {
    const thanh_tien = it.so_luong * it.don_gia;
    await db.query('INSERT INTO ChiTietHoaDon (hoadon_id, sanpham_id, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?)', [hoadon_id, it.sanpham_id, it.so_luong, it.don_gia, thanh_tien]);
    // Trigger in DB will reduce stock
  }
  // update customer points
  if (khach_hang_id) {
    const points = Math.floor(tong / 1000);
    await db.query('UPDATE KhachHang SET diem_tich_luy = diem_tich_luy + ? WHERE id = ?', [points, khach_hang_id]);
  }
  res.json({ id: hoadon_id, tong });
}

async function getInvoice(req, res) {
  const id = req.params.id;
  const hd = await db.query('SELECT * FROM HoaDon WHERE id = ?', [id]);
  if (!hd[0]) return res.status(404).json({ message: 'Not found' });
  const items = await db.query('SELECT c.*, s.ten FROM ChiTietHoaDon c LEFT JOIN SanPham s ON c.sanpham_id = s.id WHERE c.hoadon_id = ?', [id]);
  res.json({ invoice: hd[0], items });
}

module.exports = { createInvoice, getInvoice };