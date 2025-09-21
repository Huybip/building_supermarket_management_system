from flask import Flask, request, send_file, jsonify
import io
import pandas as pd
import mysql.connector
from reportlab.pdfgen import canvas
import os

app = Flask(__name__)

DB_CONFIG = {
    'host': os.getenv('DB_HOST','localhost'),
    'user': os.getenv('DB_USER','root'),
    'password': os.getenv('DB_PASSWORD',''),
    'database': os.getenv('DB_NAME','supermarket')
}

def get_conn():
    return mysql.connector.connect(**DB_CONFIG)

@app.route('/report/doanhthu')
def report_doanhthu():
    from_date = request.args.get('from')
    to_date = request.args.get('to')
    conn = get_conn()
    cur = conn.cursor(dictionary=True)
    cur.execute("SELECT DATE(created_at) as ngay, SUM(tong_tien) as doanh_thu FROM HoaDon WHERE created_at BETWEEN %s AND %s GROUP BY DATE(created_at)", (from_date, to_date))
    rows = cur.fetchall()
    df = pd.DataFrame(rows)
    output = io.BytesIO()
    with pd.ExcelWriter(output, engine='openpyxl') as writer:
        df.to_excel(writer, index=False, sheet_name='doanhthu')
    output.seek(0)
    return send_file(output, download_name='doanhthu.xlsx', as_attachment=True, mimetype='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')

@app.route('/report/ton-kho')
def report_tonkho():
    conn = get_conn()
    cur = conn.cursor(dictionary=True)
    cur.execute("SELECT id, ma, ten, ton_kho, ngay_het_han FROM SanPham")
    rows = cur.fetchall()
    df = pd.DataFrame(rows)
    # simple pdf generation
    pdf_buf = io.BytesIO()
    p = canvas.Canvas(pdf_buf)
    p.drawString(50, 800, "Bao cao ton kho")
    y = 780
    for r in rows:
        line = f"{r['id']} - {r['ma']} - {r['ten']} - {r['ton_kho']}"
        p.drawString(50, y, line)
        y -= 15
        if y < 50:
            p.showPage()
            y = 800
    p.save()
    pdf_buf.seek(0)
    return send_file(pdf_buf, download_name='tonkho.pdf', as_attachment=True, mimetype='application/pdf')

@app.route('/report/nhan-vien')
def report_nhanvien():
    conn = get_conn()
    cur = conn.cursor(dictionary=True)
    cur.execute("SELECT n.id, n.ho_ten, COUNT(h.id) as so_hoa_don, SUM(h.tong_tien) as doanh_thu FROM NhanVien n LEFT JOIN HoaDon h ON n.id = h.nhan_vien_id GROUP BY n.id")
    rows = cur.fetchall()
    df = pd.DataFrame(rows)
    output = io.BytesIO()
    with pd.ExcelWriter(output, engine='openpyxl') as writer:
        df.to_excel(writer, index=False, sheet_name='nhanvien')
    output.seek(0)
    return send_file(output, download_name='nhanvien.xlsx', as_attachment=True, mimetype='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')

if __name__ == '__main__':
    app.run(port=5000, debug=True)