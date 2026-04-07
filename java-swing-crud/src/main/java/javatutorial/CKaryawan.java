package javatutorial;

import jakarta.persistence.*; // Pakai jakarta karena kita pakai Hibernate 6+

@Entity
@Table(name = "karyawan")
public class CKaryawan {

    @Id
    @Column(name = "nip")
    private String nip;

    @Column(name = "nama")
    private String nama;

    @Column(name = "temp_lahir")
    private String temp_lahir;

    @Column(name = "tgl_lahir")
    private String tgl_lahir;

    @Column(name = "jabatan")
    private String jabatan;

    // WAJIB: Constructor kosong untuk Hibernate
    public CKaryawan() {
    }

    // SETTER & GETTER (Wajib ada supaya Hibernate bisa baca data)
    public void setNip(String value) { this.nip = value; }
    public String getNip() { return this.nip; }

    public void setNama(String value) { this.nama = value; }
    public String getNama() { return this.nama; }

    public void setTempatLahir(String value) { this.temp_lahir = value; }
    public String getTempatLahir() { return this.temp_lahir; }

    public void setTglLahir(String value) { this.tgl_lahir = value; }
    public String getTglLahir() { return this.tgl_lahir; }

    public void setJabatan(String value) { this.jabatan = value; }
    public String getJabatan() { return this.jabatan; }
}