// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/sanphamController.js
const db = require('../models/queries');

async function getAll(req, res) {
  const rows = await db.query('SELECT * FROM SanPham ORDER BY id DESC');
  res.json(rows);
}

async function getById(req, res) {
  const id = req.params.id;
  const rows = await db.query('SELECT * FROM SanPham WHERE id = ?', [id]);
  res.json(rows[0] || null);
}

async function create(req, res) {
  const { ma, ten, mo_ta, don_gia, ton_kho, ngay_san_xuat, ngay_het_han } = req.body;
  const result = await db.query(
    'INSERT INTO SanPham (ma, ten, mo_ta, don_gia, ton_kho, ngay_san_xuat, ngay_het_han) VALUES (?, ?, ?, ?, ?, ?, ?)',
    [ma, ten, mo_ta, don_gia || 0, ton_kho || 0, ngay_san_xuat || null, ngay_het_han || null]
  );
  res.json({ id: result.insertId });
}

async function update(req, res) {
  const id = req.params.id;
  const data = req.body;
  const fields = [];
  const params = [];
  for (const k in data) {
    fields.push(`${k} = ?`);
    params.push(data[k]);
  }
  params.push(id);
  await db.query(`UPDATE SanPham SET ${fields.join(', ')} WHERE id = ?`, params);
  res.json({ ok: true });
}

async function del(req, res) {
  const id = req.params.id;
  await db.query('DELETE FROM SanPham WHERE id = ?', [id]);
  res.json({ ok: true });
}

async function lowStock(req, res) {
  const threshold = parseInt(req.query.t || '5', 10);
  const rows = await db.query('SELECT * FROM SanPham WHERE ton_kho <= ?', [threshold]);
  res.json(rows);
}

module.exports = { getAll, getById, create, update, del, lowStock };