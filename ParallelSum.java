// Library bawaan Java (java.util) untuk membuat angka acak
import java.util.Random;
// Library untuk membuat list dinamis, dipakai menyimpan kumpulan tugas (Future)
import java.util.ArrayList;
import java.util.List;
// Library java.util.concurrent: kumpulan tools untuk menjalankan
// proses secara paralel menggunakan beberapa thread
import java.util.concurrent.Callable;    // "bentuk" tugas yang dijalankan thread & mengembalikan hasil
import java.util.concurrent.ExecutorService; // pengelola/kumpulan thread (thread pool)
import java.util.concurrent.Executors;       // pembuat ExecutorService
import java.util.concurrent.Future;          // tempat mengambil hasil dari tugas yang sudah dijalankan thread

public class ParallelSum {
    public static void main(String[] args) throws Exception {

        int ukuran = 50_000_000; // ukuran array besar (sama dengan SerialSum)
        int jumlahThread = Runtime.getRuntime().availableProcessors();

        // 1. Buat array berisi data angka acak
        int[] data = new int[ukuran];
        Random rand = new Random();
        for (int i = 0; i < ukuran; i++) {
            data[i] = rand.nextInt(1000); // angka acak 0 - 999
        }

        System.out.println("=== PENJUMLAHAN SECARA PARALEL ===");
        System.out.println("Ukuran array  : " + ukuran);
        System.out.println("Jumlah thread : " + jumlahThread);

        // 3. Catat waktu mulai eksekusi
        long waktuMulai = System.nanoTime();

        ExecutorService executor = Executors.newFixedThreadPool(jumlahThread);
        List<Future<Long>> hasilTugas = new ArrayList<>();

        int ukuranBagian = ukuran / jumlahThread;

        // 2. Bagi array menjadi beberapa bagian dan jumlahkan tiap bagian
        //    secara paralel menggunakan beberapa thread
        for (int t = 0; t < jumlahThread; t++) {
            final int awal = t * ukuranBagian;
            final int akhir = (t == jumlahThread - 1) ? ukuran : awal + ukuranBagian;

            Callable<Long> tugas = () -> {
                long subTotal = 0;
                for (int i = awal; i < akhir; i++) {
                    subTotal += data[i];
                }
                return subTotal;
            };
            hasilTugas.add(executor.submit(tugas));
        }

        // Gabungkan hasil dari semua thread
        long total = 0;
        for (Future<Long> f : hasilTugas) {
            total += f.get();
        }
        executor.shutdown();

        // 3. Catat waktu selesai eksekusi
        long waktuSelesai = System.nanoTime();
        double durasiMs = (waktuSelesai - waktuMulai) / 1_000_000.0;

        System.out.println("Hasil penjumlahan : " + total);
        System.out.println("Waktu eksekusi    : " + durasiMs + " ms");
    }
}
