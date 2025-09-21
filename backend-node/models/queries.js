// filepath: /workspaces/building_supermarket_management_system/backend-node/models/queries.js
const pool = require('../config/db');
const bcrypt = require('bcrypt');

async function query(sql, params) {
  const [rows] = await pool.execute(sql, params);
  return rows;
}

module.exports = { query };