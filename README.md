## 1. Thông tin cơ bản
Website quản lý học sinh cho phép thực hiện đầy đủ được các thao tác theo đề bài như sau:
- **Quản lý danh sách học sinh**
  - Xem danh sách tất cả học sinh có trong hệ thống
  - Thêm học sinh (id, tên, grade, birthday, address, notes)
  - Sắp xếp danh sách (theo tên A – Z, theo Grade)
  - Search (tra cứu theo tên học sinh)
  - Edit thông tin, update lại học sinh trong danh sách.
  - Xóa học sinh khỏi danh sách, đồng thời xóa luôn trong Course list có liên quan đến học sinh này
  - Xem danh sách khóa học mà học sinh đã đăng ký (tất cả, theo năm, sắp xếp)
  - Xem bảng điểm của 1 học sinh (xem theo năm, tính điểm trung bình)
  
- **Quản lý danh sách khóa học**
  - Xem toàn bộ khóa học có trong hệ thống
  - Thêm khóa học (id, name, lecture, year, notes)
  - Sắp xếp khóa học (theo tên A - Z)
  - Search (tra cứu theo tên khóa học)
  - Đối với mỗi khóa học có thẻ Edit thông tin, xóa khóa học cùng với thông tin các học sinh đã đăng ký
  - Xem chi tiết khóa học gồm (danh sách học sinh, ngày đăng ký, …)
    - Thêm học sinh vào khóa học này
    - Sắp xếp danh sách
    - Xóa học sinh ra khỏi khóa học
    - Nhập điểm cho học sinh trong khóa học


## 2. Bố trí chương trình và cách chạy chương trình
Chương trình sử dụng các công nghệ: **Servlet**, **JDBC (SQL Server)**, **JSP**, **TomcatServer 10.1.7**.

Chương trình được viết trên IDE: **Inteliji 2023.1**

Để sử dụng chương trình cần cài đặt các thư viện đã có trong file pom.xml sau:
- JDK 15
- Maven fasterxml.jackson.core: để xử lí json
- Maven mssql-jdbc-9.2.1.jre15: drive jdbc sử dụng SQL server
- Jakarta: hệ thống tự tạo
- Javax.servlet-api : Servlet
- Javax.servlet:jstl:1.2: thư viện JSP
- Org.junit.platform: hệ thống tự tạo
- Org.json: để xử lí json

Để chạy chương trình:
- Để chạy trong web ta cần chạy với server Tomcat và kết nối với SQL server với database trong thư mục **Data/script.sql** với database name **StudentManagement Web**.
- Sau khi tạo database theo file script mở file resources/db.properties để điền thông tin: **url, username, password**
- Chạy server Tomcat để trải nghiệmmm
