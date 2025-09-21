// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/khachhang.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/khachhangController');
const { authenticate } = require('../middleware/auth');

router.post('/register', ctrl.register);
router.post('/login', ctrl.login);
router.post('/change-password', authenticate, ctrl.changePassword);
router.get('/:id', authenticate, ctrl.getById);

module.exports = router;