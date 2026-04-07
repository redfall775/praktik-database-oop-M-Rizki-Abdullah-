# Latihan CRUD Karyawan dengan Java Swing - Praktik OOP Java

Aplikasi desktop sederhana untuk mengelola data karyawan menggunakan Java Swing dan MySQL. Dibuat sebagai tutorial belajar Java dengan GUI dan database.

## Deskripsi Proyek

Proyek ini dibuat untuk mendemonstrasikan implementasi CRUD (Create, Read, Update, Delete) operations pada aplikasi Java desktop. Menggunakan:

- **Java Swing** untuk antarmuka grafis
- **MySQL** sebagai database
- **Maven** untuk manajemen dependensi dan build
- **FlatLaf** untuk tema modern pada UI

## Fitur

- ✅ Tambah data karyawan baru
- ✅ Lihat semua data karyawan dalam tabel
- ✅ Edit data karyawan yang ada
- ✅ Hapus data karyawan
- ✅ Pencarian data berdasarkan NIP (lanjutan)
- ✅ Validasi input dasar (lanjutan
- ✅ Tema UI modern dengan FlatLaf
- ✅ Klik row tabel untuk mengisi form otomatis
- ✅ Validasi tanggal lahir (tahun 1900-2100, bulan 1-12, hari 1-31)

## Prasyarat

Sebelum menjalankan aplikasi, pastikan sistem Anda memiliki:

### 1. Java Development Kit (JDK) 21
- **Versi**: Java 21 LTS (Long Term Support)
- **Cara Install**:
  ```bash
  # Ubuntu/Debian
  sudo apt update
  sudo apt install openjdk-21-jdk

  # CentOS/RHEL/Fedora
  sudo yum install java-21-openjdk-devel

  # Arch Linux
  sudo pacman -S jdk21-openjdk

  # macOS (dengan Homebrew)
  brew install openjdk@21

  # Windows: Download dari https://adoptium.net/temurin/releases/
  ```
- **Verifikasi**: `java -version` harus menampilkan Java 21.x.x

### 2. Apache Maven
- **Versi Minimum**: 3.6.x
- **Cara Install**:
  ```bash
  # Ubuntu/Debian
  sudo apt install maven

  # CentOS/RHEL
  sudo yum install maven

  # macOS
  brew install maven

  # Windows:
  Download bin dari https://dlcdn.apache.org/maven/maven-3/3.9.14/binaries/apache-maven-3.9.14-bin.zip
  jangan lupa di extract dulu zipnya
  buka start menu dan cari "Edit the system environment variables"
  buka tab "advance", tekan "Environment Variables"
  di bagian bawah box "system variable" teken tulisan "new "
  masukan "MAVEN_HOME" sebagai nama variabel, dan lokasi dari si maven sebagai valuenya
  terus masih di "system variabel" teken yang ada tulisan "path" terus teken tombol "edit" di bawah
  teken "new" terus tulis "%MAVEN_HOME%\bin"
  kalau masih kurang paham bisa cek https://phoenixnap.com/kb/install-maven-windows
  ```
- **Verifikasi**: `mvn -version`

### 3. MySQL Server
- **Versi Minimum**: 5.7 atau 8.0
- **Cara Install**:
  ```bash
  # Ubuntu/Debian
  sudo apt install mysql-server
  sudo systemctl start mysql
  sudo mysql_secure_installation

  # CentOS/RHEL
  sudo yum install mysql-server
  sudo systemctl start mysqld
  sudo mysql_secure_installation

  # macOS
  brew install mysql
  brew services start mysql

  # Windows: Download dari https://dev.mysql.com/downloads/mysql/
  ```
- **Verifikasi**: `mysql --version`

### 4. MySQL Connector/J
Sudah termasuk dalam `pom.xml` sebagai dependensi Maven:
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

## Instalasi

1. **Clone atau Download Proyek**
   ```bash
   git clone <repository-url>
   cd crud-swing-karyawan
   ```

2. **Build Proyek**
   ```bash
   mvn clean compile
   ```

3. **Setup Database**
   - Login ke MySQL:
     ```bash
     mysql -u root -p
     ```
   - Buat database dan tabel:
     ```sql
     CREATE DATABASE karyawan_db; 
     USE karyawan_db;

     CREATE TABLE karyawan (
         nip VARCHAR(20) PRIMARY KEY,
         nm_kar VARCHAR(100) NOT NULL,
         tem_lhr VARCHAR(50),
         tgl_lhr DATE,
         jabatan VARCHAR(50)
     );
     ```
   - Buat user database (opsional, tapi recommended):
     ```sql
     CREATE USER 'karyawan_user'@'localhost' IDENTIFIED BY 'password123';
     GRANT ALL PRIVILEGES ON karyawan_db.* TO 'karyawan_user'@'localhost';
     FLUSH PRIVILEGES;
     ```

## Konfigurasi

### Koneksi Database
Edit file `src/main/java/javatutorial/CConnection.java`:

```java
private String url = "jdbc:mysql://localhost:3306/karyawan_db";
private String user = "karyawan_user";  // Ganti dengan username MySQL Anda
private String password = "password123"; // Ganti dengan password MySQL Anda
```

### Port Database
Jika MySQL menggunakan port selain 3306, update URL koneksi.

## Cara Menjalankan

### Metode 1: Menggunakan Maven (Recommended)
```bash
mvn compile exec:java
```

### Metode 2: Manual Compile dan Run
```bash
# Compile
mvn compile

# Run (Windows PowerShell / Copas semua dalam 1 line)
mvn -q dependency:build-classpath "-Dmdep.outputFile=cp.txt"
$cp = Get-Content .\cp.txt
java -cp "target\classes;$cp" javatutorial.Main

# Run (Linux/macOS)
java -cp target/classes:$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q) javatutorial.Main
```

### Metode 3: Package ke JAR
```bash
mvn clean package
java -jar target/crud-swing-karyawan-1.0-SNAPSHOT.jar
```

## Struktur Proyek

```
crud-swing-karyawan/
├── pom.xml                          # Konfigurasi Maven
├── src/
│   └── main/
│       └── java/
│           └── javatutorial/
│               ├── Main.java         # Entry point aplikasi
│               ├── FormKaryawan.java # GUI utama (Swing)
│               ├── CKaryawan.java    # Model dan operasi database
│               └── CConnection.java  # Koneksi database
├── target/                          # Output kompilasi
└── README.md                        # Dokumentasi ini
```

## Troubleshooting

### Error: "Class not found"
- Pastikan semua dependensi terdownload: `mvn dependency:resolve`
- Untuk Windows PowerShell, gunakan pemisah classpath `;` (bukan `:`)

### Error: "Data truncation: Incorrect date value"
- Pastikan format tanggal benar: Tahun 1900-2100, Bulan 1-12, Hari 1-31
- Aplikasi akan menampilkan pesan error jika tanggal tidak valid
- Contoh: 1990-05-15 (bukan 1990-5-15 atau 1990-13-15)

### GUI tidak responsif
- Pastikan MySQL server berjalan
- Klik row di tabel untuk mengisi form otomatis
- Gunakan tombol Insert/Update/Delete setelah mengisi form

### Build gagal
- Bersihkan cache: `mvn clean`
- Update dependensi: `mvn dependency:purge-local-repository`

## Teknologi yang Digunakan

- **Java**: 21 LTS
- **Swing**: Untuk GUI
- **MySQL**: Database
- **Maven**: Build tool
- **FlatLaf**: UI theming
- **JDBC**: Database connectivity


## Dukungan

Jika ada pertanyaan atau masalah:
- Cek bagian Troubleshooting di atas
- Pastikan semua prasyarat terinstall dengan benar
- Verifikasi konfigurasi database

---

**Catatan**: Pastikan MySQL server selalu berjalan sebelum menjalankan aplikasi. Aplikasi ini menggunakan koneksi database langsung tanpa connection pooling untuk kesederhanaan.
