package com.example.hoaxnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_edukasi.*
import java.util.ArrayList

class EdukasiActivity : AppCompatActivity() {

    val edukasiList = ArrayList<isi_Edukasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edukasi)

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val eduAdapter = isi_EdukasiAdapter(edukasiList)
        edukasiRV.adapter = eduAdapter
        edukasiRV.setHasFixedSize(true)

    }

    private fun initData() {
        edukasiList.add(isi_Edukasi(
            "Hati-hati dengan judul provokatif",
            "Berita hoax seringkali menggunakan judul sensasional yang provokatif, misalnya dengan langsung menudingkan jari ke pihak tertentu. Isinya pun bisa diambil dari berita media resmi, hanya saja diubah-ubah agar menimbulkan persepsi sesuai yang dikehendaki sang pembuat hoaks."
            )
        )
        edukasiList.add(
            isi_Edukasi(
            "Cermati alamat situs",
            "Untuk informasi yang diperoleh dari website atau mencantumkan link, cermatilah alamat URL situs dimaksud. Berita yang berasal dari situs media yang sudah terverifikasi Dewan Pers akan lebih mudah diminta pertanggungjawabannya."
            )
        )
        edukasiList.add(isi_Edukasi(
            "Periksa Fakta",
            "Perhatikan dari mana berita berasal dan siapa sumbernya? Apakah dari institusi resmi seperti KPK atau Polri?  Perhatikan keberimbangan sumber berita. Jika hanya ada satu sumber, pembaca tidak bisa mendapatkan gambaran yang utuh.\n" +
                    "\n" +
                    "Hal lain yang perlu diamati adalah perbedaan antara berita yang dibuat berdasarkan fakta dan opini. Fakta adalah peristiwa yang terjadi dengan kesaksian dan bukti, sementara opini adalah pendapat dan kesan dari penulis berita, sehingga memiliki kecenderungan untuk bersifat subyektif."
            )
        )
        edukasiList.add(
            isi_Edukasi(
            "Cek Keaslian foto",
                "Di era teknologi digital saat ini , bukan hanya konten berupa teks yang bisa dimanipulasi, melainkan juga konten lain berupa foto atau video. Ada kalanya pembuat berita palsu juga mengedit foto untuk memprovokasi pembaca.\n" +
                        "\n" +
                        "Cara untuk mengecek keaslian foto bisa dengan memanfaatkan mesin pencari Google, yakni dengan melakukan drag-and-drop ke kolom pencarian Google Images. Hasil pencarian akan menyajikan gambar-gambar serupa yang terdapat di internet sehingga bisa dibandingkan."
            )
        )
        edukasiList.add(
            isi_Edukasi(
            "Ikut serta di grup diskusi anti-hoax",
                "Di Facebook terdapat sejumlah fanpage dan grup diskusi anti-hoax, misalnya Forum Anti Fitnah, Hasut, dan Hoax (FAFHH), Fanpage & Group Indonesian Hoax Buster, Fanpage Indonesian Hoaxes, dan Grup Sekoci.\n" +
                        "\n" +
                        "Di grup-grup diskusi ini, warganet bisa ikut bertanya apakah suatu informasi merupakan hoax atau bukan, sekaligus melihat klarifikasi yang sudah diberikan oleh orang lain. Semua anggota bisa ikut berkontribusi sehingga grup berfungsi layaknya crowdsourcing yang memanfaatkan tenaga banyak orang."
            )
        )

    }
}