package Database;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

public class program {

	static Connection con;

    public static void main(String[] args) throws Exception {

		Scanner inputan = new Scanner (System.in);
        String pilihanPengguna;
        boolean isLanjutkan = true;

        String url = "jdbc:mysql://localhost:3306/pj_penjualan";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Class Driver ditemukan");

            while (isLanjutkan){
                System.out.println("\n--------------------------");
                System.out.println("========================");
                System.out.println("---DATABASE PENJUALAN---");
                System.out.println("========================");
                System.out.println("1. Tambah Data ");
                System.out.println("2. Lihat Data ");
                System.out.println("3. Ubah Data  ");
                System.out.println("4. Cari Data  ");
                System.out.println("5. Hapus Data ");
                System.out.println("--------------------------");

                System.out.print("\nMasukkan Pilihan Anda = ");
                pilihanPengguna = inputan.next();

                switch (pilihanPengguna){
                    case "1":
                        tambahdata();
                        break;
					case "2":
                        lihatdata();
                        break;
					case "3":
                        ubahdata();
                        break;
					case "4":
                        caridata();
                        break;
					case "5":
                        hapusdata();
                        break;
                    default:
                        System.err.println("\nInputan Anda Tidak ditemukan!");
                        break;
                }

                System.out.print("\nApakah Anda Masih Ingin Lanjut[y/n]?");
                pilihanPengguna = inputan.next();
                isLanjutkan = pilihanPengguna.equalsIgnoreCase("y");
            }
            System.out.println("Terima Kasih!");
        }

        catch(ClassNotFoundException ex) {
            System.err.println("Driver Error");
            System.exit(0);
        }

        catch(SQLException e){
            System.out.println("Tidak berhasil koneksi");
        }

        //inputan.close();
    }

    private static void tambahdata() throws SQLException{

        String text1 = "\n===TAMBAH DATA TRANSAKSI ===";
        System.out.println(text1.toUpperCase());

        transaksi transaksi = new transaksi();

        try{
            
            transaksi.noFaktur();
            transaksi.namaBarang();
			transaksi.hargaBarang();
            transaksi.jumlah();
            

            String sql = "INSERT INTO penjualan (noFaktur,namaBarang,hargaBarang,jumlah) VALUES ('"+transaksi.noFaktur+"','"+transaksi.namaBarang+"','"+transaksi.hargaBarang+"','"+transaksi.jumlah+"')";

            Statement statement = con.createStatement();
            statement.execute(sql);
            System.out.println("Berhasil Menginputkan Data Pembelian");

        }

        catch (SQLException e){
            System.err.println("\nTerjadi kesalahan input data");
        }
        catch (InputMismatchException e) {
            System.err.println("\nInputlah dengan angka saja");
        }

    }
	private static void lihatdata() throws SQLException{
		String text5 = "\n===DATABASE TRANSAKSI===";
		System.out.println(text5.toUpperCase());
	
		String sql = "SELECT * FROM penjualan";
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(sql);
	
		while (result.next()){
			System.out.println("\nNomor Faktur\t");
			System.out.println(result.getString("noFaktur"));
			System.out.println("\nNama Barang\t");
			System.out.println(result.getString("namaBarang"));
			System.out.println("\nHarga Barang\t");
			System.out.println(result.getInt("hargabarang"));
			System.out.println("\nJumlah Barang\t");
			System.out.println(result.getInt("jumlah"));
			System.out.println("\n--------------------------------------");
		}
	}
	private static void ubahdata() throws SQLException{
		String text3 = "\n===UBAH DATA TRANSAKSI===";
		System.out.println(text3.toUpperCase());
		
		Scanner inputan = new Scanner (System.in);
		transaksi transaksi = new transaksi();
	
		try{
			lihatdata();
			System.out.print("Masukkan No Faktur Pembelian yang Akan diubah : ");
			transaksi.noFaktur = Integer.parseInt(inputan.nextLine());
	
			String sql = "SELECT * FROM penjualan WHERE noFaktur = " +transaksi.noFaktur;
	
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(sql);
	
			if(result.next()){
				
	
				System.out.print("No Faktur ["+result.getString("noFaktur")+"]\t: ");
				transaksi.noFaktur = inputan.nextInt();
	
				System.out.print("Nama Barang ["+result.getString("namaBarang")+"]\t: ");
				transaksi.namaBarang = inputan.next();

				System.out.print("Harga Barang ["+result.getInt("hargaBarang")+"]\t: ");
				transaksi.hargaBarang = inputan.nextInt();
	
				System.out.print("Jumlah Barang ["+result.getInt("jumlah")+"]\t: ");
				transaksi.jumlah = inputan.nextInt();
	
			
	
				System.out.println("\n");
	
				sql = "UPDATE penjualan SET  namaBarang='"+transaksi.namaBarang+"', jumlah='"+transaksi.jumlah+"' , hargaBarang='"+transaksi.hargaBarang+"' WHERE noFaktur='"+transaksi.noFaktur+"' ";
				//System.out.println(sql);
	
				if(statement.executeUpdate(sql) > 0){
					System.out.println("Data Berhasil diperbarui!");
				}
			}
			statement.close();
		}
	
		catch (SQLException e){
			System.err.println("Terjadi kesalahan Ubah data");
			System.err.println(e.getMessage());
			
		}
		
		
	}

	private static void caridata () throws SQLException {
		String text4 = "\n===CARI DATA TRANSAKSI===";
		System.out.println(text4.toUpperCase());
		
		Scanner inputan = new Scanner (System.in);
				
		System.out.print("Masukkan Nomor Faktur : ");
		
		String keyword = inputan.nextLine();
		Statement statement = con.createStatement();
		String sql = "SELECT * FROM penjualan WHERE noFaktur LIKE '%"+keyword+"%'";
		ResultSet result = statement.executeQuery(sql); 
				
		while(result.next()){
			System.out.println("\nNomor Faktur\t");
			System.out.println(result.getString("noFaktur"));
			System.out.println("\nNama Barang\t");
			System.out.println(result.getString("namaBarang"));
			System.out.println("\nHarga Barang\t");
			System.out.println(result.getInt("hargaBarang"));
			System.out.println("\nJumlah Barang\t");
			System.out.println(result.getInt("jumlah"));
			System.out.println("\n--------------------------------------");
		}
		
	}
	private static void hapusdata() {
		String text2 = "\n===HAPUS DATA TRANSAKSI===";
		System.out.println(text2.toUpperCase());
		
		Scanner inputan = new Scanner (System.in);
		transaksi transaksi = new transaksi();
		
		try{
			lihatdata();
			System.out.print("Ketik nomor faktur yang akan dihapus : ");
			transaksi.noFaktur = Integer.parseInt(inputan.nextLine());
			
			String sql = "DELETE FROM penjualan WHERE noFaktur = "+ transaksi.noFaktur;
			Statement statement = con.createStatement();
			//ResultSet result = statement.executeQuery(sql);
			
			if(statement.executeUpdate(sql) > 0){
				System.out.println("Berhasil menghapus data harga barang (Harga Barang "+transaksi.noFaktur+")");
			}
		}
		catch(SQLException e){
			 System.out.println("Terjadi kesalahan dalam menghapus data barang");
			 System.err.println(e.getMessage());
			 }
		//inputan.close(); 
	}
	


    }

