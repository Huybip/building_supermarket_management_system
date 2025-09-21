// filepath: /workspaces/building_supermarket_management_system/backend-node/server.js
const express = require('express');
const dotenv = require('dotenv');
const cors = require('cors');
dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

const sanphamRoute = require('./routes/sanpham');
const khachhangRoute = require('./routes/khachhang');
const nhanvienRoute = require('./routes/nhanvien');
const hoadonRoute = require('./routes/hoadon');
const nhacungcapRoute = require('./routes/nhacungcap');
const baocaoRoute = require('./routes/baocao');

app.use('/api/sanpham', sanphamRoute);
app.use('/api/khachhang', khachhangRoute);
app.use('/api/nhanvien', nhanvienRoute);
app.use('/api/hoadon', hoadonRoute);
app.use('/api/nhacungcap', nhacungcapRoute);
app.use('/api/baocao', baocaoRoute);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Node API running on port ${PORT}`);
});