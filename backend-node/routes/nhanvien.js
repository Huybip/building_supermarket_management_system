// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/nhanvien.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/nhanvienController');
const { authenticate, authorize } = require('../middleware/auth');

router.post('/create', authenticate, authorize('admin'), ctrl.create);
router.post('/login', ctrl.login);
router.get('/', authenticate, authorize('admin'), ctrl.list);

module.exports = router;