// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/sanpham.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/sanphamController');
const { authenticate, authorize } = require('../middleware/auth');

router.get('/', authenticate, ctrl.getAll);
router.get('/low', authenticate, ctrl.lowStock);
router.get('/:id', authenticate, ctrl.getById);
router.post('/', authenticate, authorize('admin'), ctrl.create);
router.put('/:id', authenticate, authorize('admin'), ctrl.update);
router.delete('/:id', authenticate, authorize('admin'), ctrl.del);

module.exports = router;