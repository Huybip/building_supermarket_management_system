// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/nhanvienController.js
const db = require('../models/queries');
const bcrypt = require('bcrypt');
const { sign } = require('../utils/jwt');

async function create(req, res) {
  const { username, mat_khau, ho_ten, role, ca_lam } = req.body;
  const hashed = await bcrypt.hash(mat_khau, 10);
  const result = await db.query('INSERT INTO NhanVien (username, mat_khau, ho_ten, role, ca_lam) VALUES (?, ?, ?, ?, ?)', [username, hashed, ho_ten, role || 'staff', ca_lam || null]);
  res.json({ id: result.insertId });
}

async function login(req, res) {
  const { username, mat_khau } = req.body;
  const rows = await db.query('SELECT * FROM NhanVien WHERE username = ?', [username]);
  const user = rows[0];
  if (!user) return res.status(401).json({ message: 'Invalid' });
  const ok = await bcrypt.compare(mat_khau, user.mat_khau);
  if (!ok) return res.status(401).json({ message: 'Invalid' });
  const token = sign({ id: user.id, role: user.role, username: user.username });
  res.json({ token });
}

async function list(req, res) {
  const rows = await db.query('SELECT id, username, ho_ten, role, ca_lam FROM NhanVien ORDER BY id DESC');
  res.json(rows);
}

module.exports = { create, login, list };