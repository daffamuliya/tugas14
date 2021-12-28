package Database;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class transaksi extends barang {

    Scanner input = new Scanner(System.in);


    public void noFaktur() {
        System.out.println("No Faktur  = ");
        noFaktur = input.nextInt();
    }


    public void namaBarang() {
        System.out.println("Nama Barang  = ");
        namaBarang = input.next();
        namaBarang = namaBarang.toUpperCase();
    }


    public void hargaBarang() {
        System.out.println("Harga Barang  = ");
        hargaBarang = input.nextInt();
    }


    public void jumlah() {
        System.out.println("Jumlah Barang  = ");
        jumlah = input.nextInt();
    }

    @Override
    public void subTotal() {

        subTotal = hargaBarang * jumlah;
    }

    @Override
    public void discount() {
        if (subTotal > 50000 && subTotal < 10000 ){ //Jika pembelian barang besar dari 50 ribu dan kecil dari 100 ribu
            discount = subTotal * 5/100; //besar diskon 5 persen
        }
        else if (subTotal >= 100000){ //Jika pembelian barang diatas atau sebesar 100 ribu rupiah
            discount = subTotal * 20/100;  //besar diskon 20 persen
        }

        else if (subTotal >= 300000 ){ //Jika pembelian barang diatas atau sebesar 300 ribu rupiah
            discount = subTotal * 30/100;  //besar diskon 30 persen
        }

        else { //pembelian dibawah 50 ribu
            discount = 0; //tidak ada diskon

        }
    }

    @Override
    public void totalHarga() {

        totalHarga = subTotal - discount;
    }

   






}
