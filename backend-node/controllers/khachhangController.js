// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/khachhangController.js
const db = require('../models/queries');
const bcrypt = require('bcrypt');
const { sign } = require('../utils/jwt');

async function register(req, res) {
  const { email, mat_khau, ho_ten } = req.body;
  const hashed = await bcrypt.hash(mat_khau, 10);
  const result = await db.query('INSERT INTO KhachHang (email, mat_khau, ho_ten) VALUES (?, ?, ?)', [email, hashed, ho_ten]);
  res.json({ id: result.insertId });
}

async function login(req, res) {
  const { email, mat_khau } = req.body;
  const rows = await db.query('SELECT * FROM KhachHang WHERE email = ?', [email]);
  const user = rows[0];
  if (!user) return res.status(401).json({ message: 'Invalid' });
  const ok = await bcrypt.compare(mat_khau, user.mat_khau);
  if (!ok) return res.status(401).json({ message: 'Invalid' });
  const token = sign({ id: user.id, role: 'customer', email: user.email });
  res.json({ token });
}

async function changePassword(req, res) {
  const { id } = req.user;
  const { oldPass, newPass } = req.body;
  const rows = await db.query('SELECT * FROM KhachHang WHERE id = ?', [id]);
  const user = rows[0];
  if (!user) return res.status(404).json({ message: 'No user' });
  const ok = await bcrypt.compare(oldPass, user.mat_khau);
  if (!ok) return res.status(400).json({ message: 'Wrong password' });
  const hashed = await bcrypt.hash(newPass, 10);
  await db.query('UPDATE KhachHang SET mat_khau = ? WHERE id = ?', [hashed, id]);
  res.json({ ok: true });
}

async function getById(req, res) {
  const id = req.params.id;
  const rows = await db.query('SELECT id, email, ho_ten, dien_thoai, diem_tich_luy, the_thanh_vien FROM KhachHang WHERE id = ?', [id]);
  res.json(rows[0] || null);
}

module.exports = { register, login, changePassword, getById };