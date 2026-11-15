# GR2 - Hệ thống quản lý bán hàng, tồn kho và phân tích kinh doanh

Ứng dụng web cho cửa hàng nhỏ, gồm 4 nghiệp vụ: nhập hàng và tồn kho (tích hợp Excel),
bán hàng, phân tích và trực quan hóa dữ liệu, hóa đơn/biên nhận (PDF, email).

## Công nghệ
- Backend: Java 17, Spring Boot, Spring Data JPA, Spring Security, Flyway
- Database: MySQL 8 (schema `shop_manager`)
- Frontend: React, TypeScript, Vite, Ant Design

## Cấu trúc thư mục
- `backend/`: ứng dụng Spring Boot
- `frontend/`: giao diện React
- `docs/`: tài liệu, tiến độ (xem `docs/PROGRESS.md`)