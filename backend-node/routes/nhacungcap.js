// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/nhacungcap.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/nhacungcapController');
const { authenticate, authorize } = require('../middleware/auth');

router.get('/', authenticate, ctrl.list);
router.post('/', authenticate, authorize('admin'), ctrl.create);

module.exports = router;