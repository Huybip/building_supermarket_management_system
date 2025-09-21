// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/hoadon.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/hoadonController');
const { authenticate, authorize } = require('../middleware/auth');

router.post('/', authenticate, authorize('admin','staff'), ctrl.createInvoice);
router.get('/:id', authenticate, ctrl.getInvoice);

module.exports = router;