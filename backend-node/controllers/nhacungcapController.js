// filepath: /workspaces/building_supermarket_management_system/backend-node/controllers/nhacungcapController.js
const db = require('../models/queries');

async function list(req, res) {
  const rows = await db.query('SELECT * FROM NhaCungCap ORDER BY id DESC');
  res.json(rows);
}

async function create(req, res) {
  const { ten, dia_chi, dien_thoai } = req.body;
  const r = await db.query('INSERT INTO NhaCungCap (ten, dia_chi, dien_thoai) VALUES (?, ?, ?)', [ten, dia_chi, dien_thoai]);
  res.json({ id: r.insertId });
}

module.exports = { list, create };