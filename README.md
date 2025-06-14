 Sistem Pendataan Pelayanan Pasien Klinik
![image](https://github.com/user-attachments/assets/cde48aea-0aa8-410e-8d79-e56364344f76)

Aplikasi berbasis web yang digunakan untuk mendata pasien yang mendaftar di klinik, baik pasien umum maupun pasien BPJS. Aplikasi ini dikembangkan menggunakan Java Servlet (Backend), HTML + JavaScript (Frontend), dan MySQL (Database).

 ✨ Fitur Utama

- Pendaftaran pasien baru (Umum & BPJS)
- Penyimpanan data pasien ke database
- Menampilkan daftar seluruh pasien
- Mengedit dan menghapus data pasien
- RESTful API berbasis Servlet (`/api/patients`)
- Antarmuka web sederhana dan responsif

 🧩 Teknologi yang Digunakan

- Backend: Java Servlet + JDBC
- Frontend: HTML + JavaScript
- Database: MySQL
- Arsitektur: MVC (Model-View-Controller)

 ⚙️ Struktur Folder

AdminPelayananPasien/
├── src/
│ ├── model/ # Kelas Patient dan koneksi database
│ ├── controller/ # Servlet untuk handling request
├── web/
│ └── index.html # Tampilan antarmuka
├── WEB-INF/
│ └── web.xml # Konfigurasi servlet

 👨‍💻 Cara Menjalankan

1. Clone repositori ini
2. Import ke IDE Java (misal: IntelliJ IDEA / Eclipse)
3. Siapkan MySQL dan buat database `klinik`
4. Sesuaikan konfigurasi koneksi pada `DatabaseConnection.java`
5. Jalankan project menggunakan server seperti Apache Tomcat
6. Akses aplikasi via browser di `http://localhost:8080/your-project-name/`

 📚 Dokumentasi API

- `GET /api/patients` - Menampilkan semua data pasien
- `POST /api/patients` - Menambahkan pasien baru
- `PUT /api/patients?id={id}` - Mengedit data pasien
- `DELETE /api/patients?id={id}` - Menghapus data pasien

 👥 Kelompok 5

- Natasha Eva Dwi Nurdiyanti (2311110005)  
- Avrilia Viananda Nagita (2311110010)  
- Rifka Annisa Swasthi (2311110040)

📌 Catatan

Proyek ini merupakan bagian dari tugas akhir matakuliah pengembangan perangkat lunak. Aplikasi masih dapat dikembangkan lebih lanjut dengan fitur tambahan seperti login, pencarian data, dan laporan PDF.

