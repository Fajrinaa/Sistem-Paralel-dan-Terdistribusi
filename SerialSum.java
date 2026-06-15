// Library bawaan Java (java.util) untuk membuat angka acak
import java.util.Random;

public class SerialSum {
    public static void main(String[] args) {

        int ukuran = 50_000_000; // ukuran array besar

        // 1. Buat array berisi data angka acak
        int[] data = new int[ukuran];
        Random rand = new Random();
        for (int i = 0; i < ukuran; i++) {
            data[i] = rand.nextInt(1000); // angka acak 0 - 999
        }

        System.out.println("=== PENJUMLAHAN SECARA SERIAL ===");
        System.out.println("Ukuran array : " + ukuran);

        // 3. Catat waktu mulai eksekusi
        long waktuMulai = System.nanoTime();

        // 2. Jumlahkan array dari awal sampai akhir
        long total = 0;
        for (int i = 0; i < data.length; i++) {
            total += data[i];
        }

        // 3. Catat waktu selesai eksekusi
        long waktuSelesai = System.nanoTime();
        double durasiMs = (waktuSelesai - waktuMulai) / 1_000_000.0;

        System.out.println("Hasil penjumlahan : " + total);
        System.out.println("Waktu eksekusi    : " + durasiMs + " ms");
    }
}
