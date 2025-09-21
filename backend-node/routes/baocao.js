// filepath: /workspaces/building_supermarket_management_system/backend-node/routes/baocao.js
const express = require('express');
const router = express.Router();
const ctrl = require('../controllers/baocaoController');
const { authenticate, authorize } = require('../middleware/auth');

router.get('/doanhthu', authenticate, authorize('admin'), ctrl.doanhthu);
router.get('/sanpham-banchay', authenticate, authorize('admin'), ctrl.sanphamBanChay);
router.get('/tonkho', authenticate, authorize('admin'), ctrl.tonKho);
router.get('/nhanvien', authenticate, authorize('admin'), ctrl.nhanVienPerf);

module.exports = router;