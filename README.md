# building_supermarket_management_system

Run instructions:

1. Import database
   - Open [db/supermarket.sql](db/supermarket.sql) in MySQL Workbench and execute to create schema.

2. Node.js backend
   - cd backend-node
   - copy `.env.example` to `.env` and fill DB credentials and JWT_SECRET
   - npm install
   - npm start (or npm run dev)

   APIs:
   - /api/sanpham → routes in [backend-node/routes/sanpham.js](backend-node/routes/sanpham.js)
   - /api/khachhang → [backend-node/routes/khachhang.js](backend-node/routes/khachhang.js)
   - /api/nhanvien → [backend-node/routes/nhanvien.js](backend-node/routes/nhanvien.js)
   - /api/hoadon → [backend-node/routes/hoadon.js](backend-node/routes/hoadon.js)
   - /api/nhacungcap → [backend-node/routes/nhacungcap.js](backend-node/routes/nhacungcap.js)
   - /api/baocao → [backend-node/routes/baocao.js](backend-node/routes/baocao.js)

3. Python report module
   - cd backend-python
   - pip install -r requirements.txt
   - set DB env vars or ensure MySQL accessible
   - python report.py
   - APIs:
     - /report/doanhthu
     - /report/ton-kho
     - /report/nhan-vien

4. Frontend
   - Open [frontend/index.html](frontend/index.html) in browser.
   - Frontend uses fetch against Node API (localhost:3000) and report service (localhost:5000).

Files of interest:
- SQL schema: [db/supermarket.sql](db/supermarket.sql)
- Node server: [backend-node/server.js](backend-node/server.js)
- Report module: [backend-python/report.py](backend-python/report.py)
- Frontend: [frontend/index.html](frontend/index.html), [frontend/dashboard.html](frontend/dashboard.html), [frontend/banhang.html](frontend/banhang.html), [frontend/baocao.html](frontend/baocao.html)