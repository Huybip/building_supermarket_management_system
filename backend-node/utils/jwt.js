// filepath: /workspaces/building_supermarket_management_system/backend-node/utils/jwt.js
const jwt = require('jsonwebtoken');
const secret = process.env.JWT_SECRET || 'secret_key';

function sign(payload, expiresIn = '8h') {
  return jwt.sign(payload, secret, { expiresIn });
}

function verify(token) {
  try {
    return jwt.verify(token, secret);
  } catch (err) {
    return null;
  }
}

module.exports = { sign, verify };